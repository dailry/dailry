import { useEffect, useState, useRef } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as S from './CommunityPage.styled';
import {
  deletePosts,
  getPosts,
  postLikes,
  deleteLikes,
  getLikes,
  getHotPosts,
  getPostsByHashtags,
} from '../../apis/postApi';
import Text from '../../components/common/Text/Text';
import { getMember } from '../../apis/memberApi';
import { PATH_NAME } from '../../constants/routes';

const CommunityPage = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const endRef = useRef(null);
  const [memberId, setMemberId] = useState('');
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [hasNextPage, setHasNextPage] = useState(true);
  const [liked, setLiked] = useState({});
  const navigate = useNavigate();

  const toastify = (message) => {
    toast(message, {
      position: 'bottom-right',
      autoClose: 300,
      hideProgressBar: true,
      closeOnClick: true,
      transition: Zoom,
    });
  };

  const conditions = [
    {
      parameter: 'hashtag',
      value: (val) => val !== null,
      api: () =>
        getPostsByHashtags({
          hashtags: searchParams.get('hashtag'),
          page,
          size: 5,
        }),
      post: 'posts',
    },
    {
      parameter: 'orderBy',
      value: (val) => val === 'latest',
      api: () => getPosts({ page, size: 5 }),
      post: 'posts',
    },
    {
      parameter: 'orderBy',
      value: (val) => val === 'hotPosts',
      api: () => getHotPosts({ page, size: 5 }),
      post: 'hotPosts',
    },
  ];

  const setPostState = (hasNext = true, newPosts = [], newPage = 0) => {
    setHasNextPage(hasNext);
    setPosts(newPosts);
    setPage(newPage);
  };

  const getSetPost = async () => {
    const condition =
      conditions.find((c) => {
        return c.value(searchParams.get(c.parameter));
      }) || conditions[1];
    const response = await condition.api();
    if (response.status !== 200) {
      toastify('알 수 없는 오류가 발생했습니다');
      return;
    }
    const { hasNext, [condition.post]: newPost, presentPage } = response.data;
    setPostState(hasNext, [...posts, ...newPost], presentPage + 1);

    if (!memberId) {
      return;
    }
    const res = await getLikes([
      response.data[condition.post].map((p) => p.postId),
    ]);
    setLiked({
      ...liked,
      ...(await res.data),
    });
  };

  const onIntersect = (entries) => {
    entries.forEach(async (entry) => {
      if (entry.isIntersecting && hasNextPage) {
        await getSetPost();
      }
    });
  };

  useEffect(() => {
    setPostState();
    setLiked({});
  }, [searchParams]);

  useEffect(() => {
    (async () => {
      const response = await getMember();
      if (response.status === 200) {
        setMemberId(response.data.memberId);
      }
    })();
  }, []);

  useEffect(() => {
    const observer = new IntersectionObserver(onIntersect);
    observer.observe(endRef.current);
    return () => observer.disconnect();
  }, [endRef, page]);

  const handleLatestClick = () => {
    setSearchParams({ orderBy: 'latest' });
  };

  const handleHotClick = () => {
    setSearchParams({ orderBy: 'hotPosts' });
  };

  const handleToDailryClick = () => {
    if (!memberId) {
      navigate(PATH_NAME.Login);
      return;
    }
    navigate(PATH_NAME.Dailry);
  };

  const handleDeleteClick = async (postId) => {
    const response = await deletePosts(postId);
    if (response.status === 200) {
      window.location.reload();
    }
  };

  const handleEditClick = async (postId, pageImage) => {
    navigate(
      `${PATH_NAME.CommunityWrite}?type=edit&pageImage=${pageImage}&postId=${postId}`,
    );
  };

  const handleLikeClick = async (postId) => {
    if (!memberId) {
      toastify('로그인 후 이용해주세요');
      return;
    }
    if (liked[postId] === false) {
      const response = await postLikes(postId);
      if (response.status === 200) {
        setLiked({ ...liked, [postId]: true });
        setPosts(
          posts.map((post) => {
            return post.postId === postId
              ? { ...post, likeCount: post.likeCount + 1 }
              : post;
          }),
        );
        toastify('좋아요 처리되었습니다');
        return;
      }
      if (response.status === 409) {
        toastify('이미 좋아요 처리된 게시글입니다');
        return;
      }
      toastify('알 수 없는 에러가 발생했습니다.');
      return;
    }
    if (liked[postId] === true) {
      const response = await deleteLikes(postId);
      if (response.status === 200) {
        setLiked({ ...liked, [postId]: false });
        setPosts(
          posts.map((post) => {
            return post.postId === postId
              ? { ...post, likeCount: post.likeCount - 1 }
              : post;
          }),
        );
        toastify('좋아요가 취소되었습니다');
      }
    }
  };

  return (
    <S.CommunityWrapper>
      <S.HeaderWrapper>
        <Text size={24} weight={1000}>
          커뮤니티
        </Text>
        <S.SortWrapper>
          <button onClick={handleLatestClick}>전체게시글</button>
          <button onClick={handleHotClick}>인기게시글</button>
        </S.SortWrapper>
      </S.HeaderWrapper>
      {posts.length === 0 ? (
        <S.PostWrapper>
          <div>게시글이 없습니다</div>
          <button onClick={handleToDailryClick}>다일리 꾸미러 가기</button>
        </S.PostWrapper>
      ) : (
        posts.map((post) => {
          const {
            postId,
            content,
            pageImage,
            writerId,
            writerNickname,
            hashtags,
            likeCount,
            createdTime,
          } = post;
          const myPost = memberId === writerId;
          return (
            <S.PostWrapper key={postId}>
              <S.HeadWrapper>
                <S.RowFlex>
                  <div>{writerNickname}</div>
                  <div>{createdTime.split('T').join(' ')}</div>
                </S.RowFlex>
                <S.RowFlex>
                  {myPost && (
                    <button onClick={() => handleEditClick(postId, pageImage)}>
                      수정
                    </button>
                  )}
                  {myPost && (
                    <button onClick={() => handleDeleteClick(postId)}>
                      삭제
                    </button>
                  )}
                  <button>
                    <S.LikeWrapper onClick={() => handleLikeClick(postId)}>
                      <S.LikeIcon liked={liked[postId]} />
                      <Text>좋아요 {likeCount}</Text>
                    </S.LikeWrapper>
                  </button>
                </S.RowFlex>
              </S.HeadWrapper>
              <S.DailryWrapper src={pageImage} />
              <S.ContentWrapper>{content}</S.ContentWrapper>
              <S.TagsWrapper>
                {hashtags.map((hashtag) => (
                  <Text key={Math.random()}>#{hashtag}</Text>
                ))}
              </S.TagsWrapper>
            </S.PostWrapper>
          );
        })
      )}
      <div ref={endRef}></div>
    </S.CommunityWrapper>
  );
};

export default CommunityPage;
