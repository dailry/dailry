import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import * as S from './LoginPage.styled';
import Text from '../../components/common/Text/Text';
import Button from '../../components/common/Button/Button';
import { PATH_NAME } from '../../constants/routes';

const SentPassword = (props) => {
  const { email } = props;

  const navigate = useNavigate();

  const handleToLoginClick = () => {
    navigate(PATH_NAME.Login);
  };
  return (
    <S.LoginContainer>
      <Text size={24}>
        {email}
        으로 비밀번호 재설정 링크를 보냈습니다.
      </Text>
      <Button onClick={handleToLoginClick}>로그인 페이지로</Button>
    </S.LoginContainer>
  );
};

SentPassword.propTypes = {
  email: PropTypes.string.isRequired,
};

export default SentPassword;
