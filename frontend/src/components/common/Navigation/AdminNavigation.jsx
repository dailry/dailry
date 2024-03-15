import NameArea from './NameArea';
import * as S from './Navigation.styled';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import { NavigationItemIcon } from '../../../assets/svg';

const AdminNavigation = () => {
  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>Admin</Text>
      <NavigationItem icon={<NavigationItemIcon />}>회원 목록</NavigationItem>
    </S.NavigationWrapper>
  );
};

export default AdminNavigation;
