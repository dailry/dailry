import PropTypes from 'prop-types';
import * as S from './LoginPage.styled';
import Button from '../../components/common/Button/Button';
import Text from '../../components/common/Text/Text';

const SentId = (props) => {
  const { email } = props;
  return (
    <S.LoginContainer>
      <Text size={24}>
        {email}
        으로 아이디를 보냈습니다.
      </Text>
      <S.FormWrapper>
        <Button onClick={() => alert('로그인 페이지로 갈거에요')}>
          로그인 페이지로
        </Button>
        <Button onClick={() => alert('비밀번호도 찾을거에요')}>
          비밀번호 찾기
        </Button>
      </S.FormWrapper>
    </S.LoginContainer>
  );
};

SentId.propTypes = {
  email: PropTypes.string.isRequired,
};

export default SentId;
