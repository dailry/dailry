import { styled, css } from 'styled-components';
import { TEXT, FORM } from '../../../styles/color';

const sizes = {
  sm: css`
    width: 70px;
    height: 26px;
    border-radius: 4px;
    font-size: 12px;
  `,
  lg: css`
    width: 250px;
    height: 40px;
    border-radius: 8px;
    font-size: 16px;
  `,
};
export const Container = styled.button`
  color: ${TEXT.white};
  text-align: center;
  background-color: ${({ disabled = false }) =>
    disabled ? FORM.buttonDisabled : FORM.button};
  ${({ size = 'lg' }) => sizes[size]};
`;
