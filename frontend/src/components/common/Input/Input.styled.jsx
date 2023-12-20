import { styled, css } from 'styled-components';
import { FORM, TEXT } from '../../../styles/color';

const disabledStyle = css`
  color: ${TEXT.disabled};
  border: 1px solid ${FORM.inputDisabled};
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  width: 250px;
  height: 50px;
  padding: 12px;

  border: 1px solid ${FORM.input};
  border-radius: 8px;

  color: ${TEXT.black};
  font-size: 16px;

  &:focus-within {
    border-color: ${FORM.inputFocused};
  }

  ${({ disabled }) => (disabled ? disabledStyle : undefined)}
`;

export const InputArea = styled.input`
  flex: 1;
  border: none;

  &:focus {
    outline: none;
  }

  ::placeholder {
    color: ${TEXT.placeHolder};
  }
`;
