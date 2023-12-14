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
const themes = {
  normal: css`
    background-color: ${FORM.button};
  `,
  disabled: css`
    background-color: ${FORM.buttonDisabled};
  `,
};
export const Container = styled.button`
  color: ${TEXT.white};
  text-align: center;
  ${({ size = 'lg' }) => {
    return sizes[size];
  }}
  ${({ theme = 'normal' }) => {
    return themes[theme];
  }}
`;
