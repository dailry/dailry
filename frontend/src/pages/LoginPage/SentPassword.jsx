import PropTypes from 'prop-types';
import * as S from './LoginPage.styled';
import Text from '../../components/common/Text/Text';
import Button from '../../components/common/Button/Button';

const SentPassword = (props) => {
  const { email } = props;
  return (
    <S.LoginContainer>
      <Text size={24}>
        {email}
        으로 비밀번호 재설정 링크를 보냈습니다.
      </Text>
      <Button onClick={() => alert('로그인 페이지로...')}>
        로그인 페이지로
      </Button>
    </S.LoginContainer>
  );
};

SentPassword.propTypes = {
  email: PropTypes.string.isRequired,
};

export default SentPassword;
