import { useState, useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { toastify } from '../../utils/toastify';
import * as S from './CommunityPage.styled';
import Button from '../../components/common/Button/Button';
import Text from '../../components/common/Text/Text';
import { getPost, postPosts, postEditedPosts } from '../../apis/postApi';
import { PATH_NAME } from '../../constants/routes';
import { getRequest } from '../../apis/dailryApi';

const CommunityWritePage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const type = searchParams.get('type');
  const pageImage = searchParams.get('pageImage');
  const postId = searchParams.get('postId');

  const [content, setContent] = useState('');
  const [hashtags, setHashtags] = useState([]);
  const [writingTag, setWritingTag] = useState('');

  useEffect(() => {
    (async () => {
      if (type === 'edit') {
        const { data } = await getPost(postId);
        setContent(data.content);
        setHashtags(data.hashtags);
      }
    })();
  }, []);

  const handleContentChange = (e) => {
    setContent(e.target.value);
  };

  const handleShareClick = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    const request = new Blob([JSON.stringify({ content, hashtags })], {
      type: 'application/json',
    });
    formData.append('request', request);

    const pageRequest = await getRequest(pageImage);
    const pageImageBlob = new Blob([pageRequest.data], { type: 'image/png' });
    formData.append('pageImage', await pageImageBlob);
    if (type === 'post') {
      await postPosts(await formData);
    }
    if (type === 'edit') {
      await postEditedPosts(postId, await formData);
    }
    navigate(PATH_NAME.CommunityList);
  };

  const handleTagDelClick = (delTag) => {
    setHashtags([...hashtags].filter((tag) => tag !== delTag));
  };

  const handleWritingTagChange = (e) => {
    const tmpVal = e.target.value.replace(/^#/, '');
    return setWritingTag(tmpVal);
  };

  const handleWritingKeyDown = (e) => {
    if (e.key === 'Enter') {
      if (hashtags.length === 5) {
        toastify('태그는 5개까지만 등록 가능합니다');
        return;
      }
      if (hashtags.includes(writingTag)) {
        toastify('중복 태그입니다');
        return;
      }
      if (writingTag !== '') {
        setHashtags([...hashtags, writingTag]);
        setWritingTag('');
      }
      return;
    }
    if (e.key === ' ') {
      e.preventDefault();
      toastify('태그에는 띄어쓰기를 할 수 없습니다');
    }
  };

  return (
    <S.PostWrapper>
      <S.DailryWrapper src={pageImage} />
      <S.WriteContentArea
        placeholder={'한줄설명'}
        value={content}
        onChange={handleContentChange}
      />
      <S.TagsWrapper>
        <Text>태그</Text>
        {[...hashtags].map((hashtag) => (
          <S.TagWrapper key={Math.random()}>
            <Text>#{hashtag}</Text>
            <S.TagDeleteButton onClick={() => handleTagDelClick(hashtag)}>
              x
            </S.TagDeleteButton>
          </S.TagWrapper>
        ))}
        <S.WriteTagArea
          value={`#${writingTag}`}
          onChange={handleWritingTagChange}
          onKeyDown={handleWritingKeyDown}
        />
      </S.TagsWrapper>
      <Button onClick={handleShareClick}>공유하기</Button>
    </S.PostWrapper>
  );
};

export default CommunityWritePage;
