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
    const response = await postLogout();
    if (response.status === 200) {
      setLoggedIn(false);
    }
  };

  return (
    <S.BackGround>
      <S.HomeWrapper>
        <S.RingWrapper top={'14dvh'} />
        <S.RingWrapper top={'23dvh'} />
        <S.RingWrapper top={'32dvh'} />
        <S.RingWrapper top={'57dvh'} />
        <S.RingWrapper top={'66dvh'} />
        <S.RingWrapper top={'75dvh'} />
        <S.FrontCover>
          <Text as={'h1'} color={TEXT.white} size={60} weight={1000}>
            Dailry
          </Text>
        </S.FrontCover>
        <S.BackCover />
        {loggedIn ? (
          <S.BookMark as="button" onClick={handleLogoutClick} css={S.BookMark1}>
            로그아웃
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
        {loggedIn && (
          <S.BookMark to={PATH_NAME.MyPage} css={S.BookMark5}>
            마이 페이지
          </S.BookMark>
        )}
        <S.BookMark
          as="a"
          href={PATH_NAME.TeamDailry}
          target={'_blank'}
          css={S.BookMark4}
        >
          팀 다일리
        </S.BookMark>
      </S.HomeWrapper>
    </S.BackGround>
  );
};

export default HomePage;
