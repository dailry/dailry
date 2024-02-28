import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import * as S from './LoginPage.styled';
import Button from '../../components/common/Button/Button';
import Text from '../../components/common/Text/Text';
import { PATH_NAME } from '../../constants/routes';

const SentId = (props) => {
  const { email, setUserInfo } = props;

  const navigate = useNavigate();

  const handleToLoginClick = () => {
    navigate(PATH_NAME.Login);
  };

  const handleFindPasswordClick = () => {
    setUserInfo({});
  };

  return (
    <S.LoginContainer>
      <Text size={24}>
        {email}
        으로 아이디를 보냈습니다.
      </Text>
      <S.FormWrapper>
        <Button onClick={handleToLoginClick}>로그인 페이지로</Button>
        <Button onClick={handleFindPasswordClick}>비밀번호 찾기</Button>
      </S.FormWrapper>
    </S.LoginContainer>
  );
};

SentId.propTypes = {
  email: PropTypes.string.isRequired,
  setUserInfo: PropTypes.func,
};

export default SentId;
