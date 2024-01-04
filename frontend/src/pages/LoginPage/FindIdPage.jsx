import * as S from './LoginPage.styled';
import Text from '../../components/common/Text/Text';
import Input from '../../components/common/Input/Input';
import AuthButton from '../../components/common/AuthButton/AuthButton';
import { TEXT } from '../../styles/color';

const FindIdPage = () => {
  return (
    <S.LoginContainer>
      <S.FormWrapper>
        <Text as={'h1'} size={24}>
          아이디 찾기
        </Text>
        <Input placeholder={'이메일'} />
        <AuthButton onClick={() => alert('아이디를 이메일로 보냈습니다!')}>
          아이디 찾기
        </AuthButton>
      </S.FormWrapper>
      <Text size={12} color={TEXT.purple}>
        회원가입 | 로그인
      </Text>
      <S.LineWrapper>
        <S.Line />
        <Text size={12} color={TEXT.line}>
          or
        </Text>
        <S.Line />
      </S.LineWrapper>
      <S.FormWrapper>
        <Text as={'h1'} size={24}>
          비밀번호 찾기
        </Text>
        <Input placeholder={'아이디'} />
        <Input placeholder={'이메일'} />
        <AuthButton onClick={() => alert('비밀번호를 이메일로 보냈습니다!')}>
          비밀번호 찾기
        </AuthButton>
      </S.FormWrapper>
    </S.LoginContainer>
  );
};

export default FindIdPage;
