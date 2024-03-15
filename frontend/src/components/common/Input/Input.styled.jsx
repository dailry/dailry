import { styled, css } from 'styled-components';
import { INPUT, TEXT } from '../../../styles/color';

const disabledStyle = css`
  color: ${TEXT.disabled};
  border: 1px solid ${INPUT.disabled};
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  width: 250px;
  height: 50px;
  padding: 12px;

  border: 1px solid ${INPUT.default};
  border-radius: 8px;

  color: ${TEXT.black};
  font-size: 16px;

  &:focus-within {
    border-color: ${INPUT.focused};
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
