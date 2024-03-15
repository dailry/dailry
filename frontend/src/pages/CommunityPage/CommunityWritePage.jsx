import { useState } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import * as S from './CommunityPage.styled';
import Button from '../../components/common/Button/Button';
import Text from '../../components/common/Text/Text';
import { postPosts } from '../../apis/postApi';
import { PATH_NAME } from '../../constants/routes';
import { getRequest } from '../../apis/dailryApi';

const CommunityWritePage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const pageImage = searchParams.get('pageImage');

  const [content, setContent] = useState('');
  const [hashtags, setHashtags] = useState([]);
  const [writingTag, setWritingTag] = useState('');

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

    const pageImageBlob = await getRequest(pageImage);
    formData.append('pageImage', await pageImageBlob);
    await postPosts(await formData);
    navigate(PATH_NAME.CommunityList);
  };

  const handleWritingTagChange = (e) => {
    const tmpVal = e.target.value.replace(/^#/, '');
    if (tmpVal.includes(' ')) {
      setHashtags([...hashtags, tmpVal.trim()]);
      return setWritingTag('');
    }
    return setWritingTag(tmpVal);
  };

  return (
    <S.CommunityWrapper>
      <S.DailryWrapper src={pageImage} />
      <S.WriteContentArea
        placeholder={'한줄설명'}
        value={content}
        onChange={handleContentChange}
      />
      <S.TagWrapper>
        <Text>태그</Text>
        {hashtags.map((hashtag) => (
          <Text key={new Date()}>#{hashtag}</Text>
        ))}
        <S.WriteTagArea
          value={`#${writingTag}`}
          onChange={handleWritingTagChange}
        />
      </S.TagWrapper>
      <Button onClick={handleShareClick}>공유하기</Button>
    </S.CommunityWrapper>
  );
};

export default CommunityWritePage;
