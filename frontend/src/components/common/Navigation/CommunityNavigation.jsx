import { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import * as S from './Navigation.styled';
import NameArea from './NameArea';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import { NavigationItemIcon } from '../../../assets/svg';
import { getHotHashtags } from '../../../apis/postApi';
import { PATH_NAME } from '../../../constants/routes';

const CommunityNavigation = () => {
  const [hotHashtags, setHotHashtags] = useState([]);
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    (async () => {
      const response = await getHotHashtags();
      setHotHashtags(response.data.hashtags.map((hashtag) => hashtag.tagName));
      setHotHashtags(['일반', '테스트', '태그']);
    })();
  }, []);

  const handleTagClick = (hashtag) => {
    navigate(`${PATH_NAME.CommunityList}?hashtag=${hashtag}`);
  };

  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>Daily Hot</Text>
      {hotHashtags.map((hashtag) => (
        <NavigationItem
          current={hashtag === searchParams.get('hashtag')}
          onClick={() => handleTagClick(hashtag)}
          icon={<NavigationItemIcon />}
          key={hashtag}
        >
          #{hashtag}
        </NavigationItem>
      ))}
    </S.NavigationWrapper>
  );
};

export default CommunityNavigation;
