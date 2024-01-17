// 일반/disabled/강조, 아래 경고, 버튼여부
import PropTypes from 'prop-types';
import * as S from './Input.styled';

// eslint-disable-next-line react/display-name
const Input = (props) => {
  const { children, disabled = false, ...rest } = props;
  return (
    <S.InputContainer disabled={disabled}>
      <S.InputArea autoComplete="off" disabled={disabled} {...rest} />
      {children}
    </S.InputContainer>
  );
};

Input.propTypes = {
  children: PropTypes.node,
  disabled: PropTypes.bool,
  ...PropTypes.any,
};

export default Input;
