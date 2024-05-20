// Home-LoggedIn, Home-NotLoggedIn (책모양 페이지)
import { useState, useEffect } from 'react';
import * as S from './HomePage.styled';
import Text from '../../components/common/Text/Text';
import { TEXT } from '../../styles/color';
import { PATH_NAME } from '../../constants/routes';
import { getMember } from '../../apis/memberApi';
import { postLogout } from '../../apis/loginApi';

const HomePage = () => {
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    (async () => {
      const { status } = await getMember();
      if (status === 200) {
        setLoggedIn(true);
      }
    })();
  }, []);

  const handleLogoutClick = async () => {
    await postLogout();
  };

  return (
    <S.BackGround>
      <S.HomeWrapper>
        <S.RingWrapper top={'111px'} />
        <S.RingWrapper top={'181px'} />
        <S.RingWrapper top={'251px'} />
        <S.RingWrapper top={'503px'} />
        <S.RingWrapper top={'573px'} />
        <S.RingWrapper top={'643px'} />
        <S.FrontCover>
          <Text as={'h1'} color={TEXT.white} size={60} weight={1000}>
            Dailry
          </Text>
        </S.FrontCover>
        <S.BackCover />
        {loggedIn ? (
          <S.BookMark to={PATH_NAME.MyPage} css={S.BookMark5}>
            마이페이지
          </S.BookMark>
        ) : (
          <S.BookMark to={PATH_NAME.Login} css={S.BookMark1}>
            로그인
          </S.BookMark>
        )}
        <S.BookMark to={PATH_NAME.CommunityList} css={S.BookMark2}>
          커뮤니티
        </S.BookMark>
        {loggedIn && (
          <S.BookMark to={PATH_NAME.Dailry} css={S.BookMark3}>
            다일리 만들기
          </S.BookMark>
        )}
        <S.BookMark to={PATH_NAME.Home} css={S.BookMark4}>
          팀 다일리
        </S.BookMark>
        {loggedIn && <S.LogOut onClick={handleLogoutClick}>로그아웃</S.LogOut>}
      </S.HomeWrapper>
    </S.BackGround>
  );
};

export default HomePage;
