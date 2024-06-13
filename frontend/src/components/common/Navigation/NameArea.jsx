import { useState, useEffect } from 'react';
import { PATH_NAME } from '../../../constants/routes';
import { TEXT, MENU } from '../../../styles/color';
import Text from '../Text/Text';
import { getMember } from '../../../apis/memberApi';
import * as S from './Navigation.styled';

const NameArea = () => {
  const [userInfo, setUserInfo] = useState(null);

  useEffect(() => {
    (async () => {
      const response = await getMember();
      if (response.status === 200) {
        setUserInfo(response.data);
      }
    })();
  }, []);

  if (userInfo) {
    return (
      <S.NameWrapper to={PATH_NAME.MyPage}>
        <Text size={24} color={TEXT.white} weight={700}>
          {userInfo.nickname}
        </Text>
        <Text size={16} color={MENU.text} weight={700}>
          {userInfo.username}
        </Text>
      </S.NameWrapper>
    );
  }

  return (
    <S.NameWrapper to={PATH_NAME.Login}>
      <Text size={24} color={TEXT.white} weight={700}>
        로그인
      </Text>
    </S.NameWrapper>
  );
};

export default NameArea;
