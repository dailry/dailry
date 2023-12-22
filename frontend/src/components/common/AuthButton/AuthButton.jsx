// 로그인과 회원가입에 들어갈 버튼
// 2가지 크기, text, onClick, disabled
import PropTypes, { oneOf } from 'prop-types';
import * as S from './AuthButton.styled';

const AuthButton = (props) => {
  const { size, disabled, children, onClick } = props;
  return (
    <S.Container size={size} disabled={disabled} onClick={onClick}>
      {children}
    </S.Container>
  );
};

AuthButton.propTypes = {
  size: oneOf(['sm', 'lg']),
  disabled: PropTypes.bool,
  children: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default AuthButton;
