// Register, RegisterDone
import { useState } from 'react';
import RegisterForm from './RegisterForm';
import RegisterDone from './RegisterDone';
import * as S from './RegisterPage.styled';
import Text from '../../components/common/Text/Text';

const RegisterPage = () => {
  const [registerCompletedMember, setRegisterCompletedMember] = useState(null);

  return (
    <S.RegisterWrapper>
      <Text as={'h1'} size={24}>
        회원가입
      </Text>
      {registerCompletedMember ? (
        <RegisterDone nickname={registerCompletedMember.nickname} />
      ) : (
        <RegisterForm setRegisterCompletedMember={setRegisterCompletedMember} />
      )}
    </S.RegisterWrapper>
  );
};

export default RegisterPage;
