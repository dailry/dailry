import { useEffect, useState } from 'react';
import * as S from './Navigation.styled';
import NameArea from './NameArea';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import { NavigationItemIcon } from '../../../assets/svg';
import { getHotHashtags } from '../../../apis/postApi';

const CommunityNavigation = () => {
  const [hotHashtags, setHotHashtags] = useState([]);

  useEffect(() => {
    (async () => {
      const response = await getHotHashtags();
      setHotHashtags(response.data.hashtags.map((hashtag) => hashtag.tagName));
    })();
  }, []);

  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>Daily Hot</Text>
      {hotHashtags.map((hashtag) => (
        <NavigationItem icon={<NavigationItemIcon />} key={hashtag}>
          #{hashtag}
        </NavigationItem>
      ))}
    </S.NavigationWrapper>
  );
};

export default CommunityNavigation;
