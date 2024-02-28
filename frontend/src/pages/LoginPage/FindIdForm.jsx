import PropTypes from 'prop-types';
import { useState } from 'react';
import * as S from './LoginPage.styled';
import Text from '../../components/common/Text/Text';
import Input from '../../components/common/Input/Input';
import Button from '../../components/common/Button/Button';
import { TEXT } from '../../styles/color';
import { postRecoverUsername, postRecoverPassword } from '../../apis/memberApi';
import { PATH_NAME } from '../../constants/routes';

const FindIdForm = (props) => {
  const { setUserInfo } = props;
  const [findIdEmail, setFindIdEmail] = useState('');
  const [findPwEmail, setFindPwEmail] = useState('');
  const [findPwUsername, setFindPwUsername] = useState('');
  const [findIdError, setFindIdError] = useState(false);
  const [findPwError, setFindPwError] = useState(false);

  const handleFindIdEmailChange = (e) => {
    setFindIdEmail(e.target.value);
  };

  const handleFindPwEmailChange = (e) => {
    setFindPwEmail(e.target.value);
  };

  const handleFindPwUsernameChange = (e) => {
    setFindPwUsername(e.target.value);
  };

  const handleFindIdSubmit = async (e) => {
    e.preventDefault();
    const response = await postRecoverUsername(findIdEmail);
    const { status } = await response;
    if (status === 200) {
      return setUserInfo({ email: findIdEmail });
    }
    return setFindIdError(true);
  };

  const handleFindPwSubmit = async (e) => {
    e.preventDefault();
    const response = await postRecoverPassword({
      username: findPwUsername,
      email: findIdEmail,
    });
    const { status } = await response;
    if (status === 200) {
      return setUserInfo({ username: findPwUsername, email: findPwEmail });
    }
    return setFindPwError(true);
  };

  return (
    <S.LoginContainer>
      <S.FormWrapper onSubmit={handleFindIdSubmit}>
        <Text as={'h1'} size={24}>
          아이디 찾기
        </Text>
        <div>
          <Input
            placeholder={'이메일'}
            value={findIdEmail}
            onChange={handleFindIdEmailChange}
          />
          {findIdError && (
            <Text size={12} color={TEXT.error}>
              이메일이 일치하지 않습니다
            </Text>
          )}
        </div>
        <Button type={'submit'}>아이디 찾기</Button>
      </S.FormWrapper>
      <S.LinkContainer>
        <S.LinkWrapper to={PATH_NAME.Join}>회원가입</S.LinkWrapper>
        <Text size={12} color={TEXT.purple}>
          |
        </Text>
        <S.LinkWrapper to={PATH_NAME.Login}>로그인</S.LinkWrapper>
      </S.LinkContainer>
      <S.LineWrapper>
        <S.Line />
        <Text size={12} color={TEXT.line}>
          or
        </Text>
        <S.Line />
      </S.LineWrapper>
      <S.FormWrapper onSubmit={handleFindPwSubmit}>
        <Text as={'h1'} size={24}>
          비밀번호 찾기
        </Text>
        <Input
          placeholder={'아이디'}
          value={findPwUsername}
          onChange={handleFindPwUsernameChange}
        />
        <div>
          <Input
            placeholder={'이메일'}
            value={findPwEmail}
            onChange={handleFindPwEmailChange}
          />
          {findPwError && (
            <Text size={12} color={TEXT.error}>
              아이디 또는 이메일이 일치하지 않습니다
            </Text>
          )}
        </div>
        <Button type={'submit'}>비밀번호 찾기</Button>
      </S.FormWrapper>
    </S.LoginContainer>
  );
};

FindIdForm.propTypes = {
  setUserInfo: PropTypes.func,
};

export default FindIdForm;
