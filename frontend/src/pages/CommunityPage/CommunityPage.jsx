import { useEffect, useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import * as S from './CommunityPage.styled';
import { deletePosts, getPosts } from '../../apis/postApi';
import Text from '../../components/common/Text/Text';
import { getMember } from '../../apis/memberApi';
import { PATH_NAME } from '../../constants/routes';

const CommunityPage = () => {
  const endRef = useRef(null);
  const [memberId, setMemberId] = useState('');
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [hasNextPage, setHasNextPage] = useState(true);
  const navigate = useNavigate();

  const getSetPost = async () => {
    const response = await getPosts({ page, size: 5 });
    setHasNextPage(await response.data.hasNext);
    setPosts([...posts, ...(await response.data.posts)]);
    setPage((await response.data.presentPage) + 1);
  };

  const onIntersect = (entries) => {
    entries.forEach(async (entry) => {
      if (entry.isIntersecting && hasNextPage) {
        await getSetPost();
      }
    });
  };

  useEffect(() => {
    (async () => {
      const response = await getMember();
      setMemberId(response.data.memberId);
    })();
  }, []);

  useEffect(() => {
    const observer = new IntersectionObserver(onIntersect);
    observer.observe(endRef.current);
    return () => observer.disconnect();
  }, [endRef, page]);

  // const handleEditClick = async (postId) => {};

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

  return (
    <S.CommunityWrapper>
      <S.HeaderWrapper>
        <Text size={24} weight={1000}>
          커뮤니티
        </Text>
        <S.SortWrapper>
          <button>최신순</button>
          <button>인기순</button>
        </S.SortWrapper>
      </S.HeaderWrapper>
      {posts.map((post) => {
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
                  <S.LikeWrapper>
                    좋아요
                    <Text>{likeCount}</Text>
                  </S.LikeWrapper>
                </button>
              </S.RowFlex>
            </S.HeadWrapper>
            <S.DailryWrapper src={pageImage} />
            <S.ContentWrapper>{content}</S.ContentWrapper>
            <S.TagWrapper>
              {hashtags.map((hashtag) => (
                <Text key={Math.random()}>#{hashtag}</Text>
              ))}
            </S.TagWrapper>
          </S.PostWrapper>
        );
      })}
      <div ref={endRef}></div>
    </S.CommunityWrapper>
  );
};

export default CommunityPage;
