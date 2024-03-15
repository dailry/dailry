import { useEffect, useState, useRef } from 'react';
import * as S from './CommunityPage.styled';
import { getPosts } from '../../apis/postApi';
import Text from '../../components/common/Text/Text';
import { HeartIcon } from '../../assets/svg';

const CommunityPage = () => {
  // const observer = useRef(null);
  const endRef = useRef(null);
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [hasNextPage, setHasNextPage] = useState(true);

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
    const observer = new IntersectionObserver(onIntersect);
    observer.observe(endRef.current);
    return () => observer.disconnect();
  }, [endRef, page]);

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
          // writerId,
          writerNickname,
          hashtags,
          likeCount,
          createdTime,
        } = post;
        return (
          <S.PostWrapper key={postId}>
            <S.HeadWrapper>
              <S.RowFlex>
                <div>{writerNickname}</div>
                <div>{createdTime.split('T').join(' ')}</div>
              </S.RowFlex>
              <S.RowFlex>
                <button>수정</button>
                <button>삭제</button>
                <button>
                  <S.LikeWrapper>
                    <div>하트</div>
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
