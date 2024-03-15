import { useEffect, useState, useRef, useCallback } from 'react';
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

  const getSetPost = useCallback(async () => {
    const response = await getPosts({ page, size: 5 });
    setHasNextPage(response.data.hasNext);
    setPosts(() => [...posts, ...response.data.posts]);
    setPage(() => response.data.presentPage + 1);
  }, []);

  useEffect(() => {
    (async () => {
      await getSetPost();
    })();
  }, []);

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
