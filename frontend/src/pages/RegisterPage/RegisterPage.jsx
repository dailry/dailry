// Register, RegisterDone
import * as S from './RegisterPage.styled';
import AuthButton from '../../components/common/AuthButton/AuthButton';
import Text from '../../components/common/Text/Text';
import Input from '../../components/common/Input/Input';
import { TEXT } from '../../styles/color';

const RegisterPage = () => {
  return (
    <S.RegisterWrapper>
      <Text as={'h1'} size={24}>
        회원가입
      </Text>
      <div>
        <Input placeholder={'아이디'}>
          <AuthButton size={'sm'} onClick={() => alert('중복확인 되었습니다!')}>
            중복확인
          </AuthButton>
        </Input>
        <Text size={12} color={TEXT.error}>
          아이디 중복확인을 해주세요
        </Text>
      </div>
      <div>
        <Input placeholder={'비밀번호'} />
        <Text size={12} color={TEXT.valid}>
          사용 가능한 비밀번호입니다
        </Text>
      </div>
      <div>
        <Input placeholder={'비밀번호 확인'} />
        <Text size={12} color={TEXT.error}>
          비밀번호가 비밀번호 확인과 다릅니다
        </Text>
      </div>
      <AuthButton onClick={() => alert('회원가입 완료!')}>회원가입</AuthButton>
    </S.RegisterWrapper>
  );
};

export default RegisterPage;
