import * as S from './Navigation.styled';
import NameArea from './NameArea';
// import Text from '../Text/Text';
// import NavigationItem from '../NavigationItem/NavigationItem';
// import { NavigationItemIcon } from '../../../assets/svg';

const CommunityNavigation = () => {
  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      {/* <Text css={S.ItemName}>Daily Hot</Text> */}
      {/* <NavigationItem icon={<NavigationItemIcon />}>#수능</NavigationItem> */}
    </S.NavigationWrapper>
  );
};

export default CommunityNavigation;
