import * as S from './Navigation.styled';
import NameArea from './NameArea';
import Text from '../Text/Text';
import NavigationItem from '../NavigationItem/NavigationItem';
import { NavigationItemIcon } from '../../../assets/svg';

const DailryNavigation = () => {
  return (
    <S.NavigationWrapper>
      <NameArea />
      <S.Line />
      <Text css={S.ItemName}>My Dailry</Text>
      <NavigationItem icon={<NavigationItemIcon />}>dailry</NavigationItem>
      <NavigationItem icon={<NavigationItemIcon />} current>
        dailry
      </NavigationItem>
    </S.NavigationWrapper>
  );
};

export default DailryNavigation;
