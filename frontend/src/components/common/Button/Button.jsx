// 로그인과 회원가입에 들어갈 버튼
// 2가지 크기, text, onClick, disabled
import PropTypes, { oneOf } from 'prop-types';
import * as S from './Button.styled';

const Button = (props) => {
  const { size, disabled, children, onClick, type = 'button' } = props;
  return (
    <S.Container size={size} disabled={disabled} onClick={onClick} type={type}>
      {children}
    </S.Container>
  );
};

Button.propTypes = {
  size: oneOf(['sm', 'lg']),
  disabled: PropTypes.bool,
  children: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  type: oneOf(['submit', 'button']),
};

export default Button;
