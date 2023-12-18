// 일반/disabled/강조, 아래 경고, 버튼여부
import PropTypes from 'prop-types';
import { useState, forwardRef } from 'react';
import * as S from './Input.styled';

// eslint-disable-next-line react/display-name
const Input = forwardRef((props, ref) => {
  const { children, disabled = false, ...rest } = props;

  // container가 알아야하는거 : focus or disabled, button
  // inputarea가 알아야하는거 : input에 대한 기본 속성들 - value, placeholder 등 ...rest로 넘겨주기
  return (
    <S.InputContainer disabled={disabled}>
      <S.InputArea ref={ref} disabled={disabled} {...rest} />
      {children}
    </S.InputContainer>
  );
});

Input.propTypes = {
  children: PropTypes.elementType,
  disabled: PropTypes.bool,
  ...PropTypes.any,
};

export default Input;
