// Login, FindId, SentId, SentPasswordReset
import * as S from './LoginPage.styled';
import Text from '../../components/common/Text/Text';
import Input from '../../components/common/Input/Input';
import Button from '../../components/common/Button/Button';
import { TEXT } from '../../styles/color';
import { googleLogo, kakaoLogo } from '../../assets/png';

const LoginPage = () => {
  return (
    <S.LoginContainer>
      <Text as={'h1'} size={24}>
        로그인
      </Text>
      <S.FormWrapper>
        <Input placeholder={'아이디'} />
        <div>
          <Input placeholder={'비밀번호'} />
          <Text size={12} color={TEXT.error}>
            아이디 또는 비밀번호가 일치하지 않습니다
          </Text>
        </div>
        <Button onClick={() => alert('로그인 되었습니다!')}>로그인</Button>
        <Text size={12} color={TEXT.purple}>
          회원가입 | 아이디/비밀번호 찾기
        </Text>
      </S.FormWrapper>
      <S.LineWrapper>
        <S.Line />
        <Text size={12} color={TEXT.line}>
          or
        </Text>
        <S.Line />
      </S.LineWrapper>
      <S.SocialLoginWrapper>
        <S.GoogleLoginButton>
          <S.Logo src={googleLogo} />
          <Text css={S.GoogleLoginText}>Google</Text>
        </S.GoogleLoginButton>
        <S.KakaoLoginButton>
          <S.Logo src={kakaoLogo} />
          <Text css={S.KakaoLoginText}>로그인</Text>
        </S.KakaoLoginButton>
      </S.SocialLoginWrapper>
    </S.LoginContainer>
  );
};

export default LoginPage;
