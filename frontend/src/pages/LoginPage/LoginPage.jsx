// Login, FindId, SentId, SentPasswordReset
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as S from './LoginPage.styled';
import Text from '../../components/common/Text/Text';
import Input from '../../components/common/Input/Input';
import Button from '../../components/common/Button/Button';
import { postLogin } from '../../apis/loginApi';
import { TEXT } from '../../styles/color';
import { googleLogo, kakaoLogo } from '../../assets/png';
import { PATH_NAME } from '../../constants/routes';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loginError, setLoginError] = useState(false);

  const navigate = useNavigate();

  const handleIdChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await postLogin({ username, password });
    const { status } = await response;
    if (status === 200) {
      return navigate(PATH_NAME.Home);
    }
    return setLoginError(true);
  };

  return (
    <S.LoginContainer>
      <Text as={'h1'} size={24}>
        로그인
      </Text>
      <S.FormWrapper onSubmit={handleSubmit}>
        <Input
          placeholder={'아이디'}
          value={username}
          onChange={handleIdChange}
        />
        <div>
          <Input
            type={'password'}
            placeholder={'비밀번호'}
            value={password}
            onChange={handlePasswordChange}
          />
          {loginError && (
            <Text size={12} color={TEXT.error}>
              아이디 또는 비밀번호가 일치하지 않습니다
            </Text>
          )}
        </div>
        <Button type={'submit'}>로그인</Button>
        <S.LinkContainer>
          <S.LinkWrapper to={PATH_NAME.Join}>회원가입</S.LinkWrapper>
          <Text size={12} color={TEXT.purple}>
            |
          </Text>
          <S.LinkWrapper to={PATH_NAME.FindId}>
            아이디/비밀번호 찾기
          </S.LinkWrapper>
        </S.LinkContainer>
      </S.FormWrapper>
      <S.LineWrapper>
        <S.Line />
        <Text size={12} color={TEXT.line}>
          or
        </Text>
        <S.Line />
      </S.LineWrapper>
      <S.SocialLoginWrapper>
        <a href="https://api.da-ily.site/oauth2/authorization/google">
          <S.GoogleLoginButton>
            <S.Logo src={googleLogo} />
            <Text css={S.GoogleLoginText}>Google</Text>
          </S.GoogleLoginButton>
        </a>
        <a href="https://api.da-ily.site/oauth2/authorization/kakao">
          <S.KakaoLoginButton>
            <S.Logo src={kakaoLogo} />
            <Text css={S.KakaoLoginText}>로그인</Text>
          </S.KakaoLoginButton>
        </a>
      </S.SocialLoginWrapper>
    </S.LoginContainer>
  );
};

export default LoginPage;
