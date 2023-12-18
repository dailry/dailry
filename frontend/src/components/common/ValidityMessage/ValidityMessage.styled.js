import { css } from 'styled-components';
import { TEXT } from '../../../styles/color';

export const ValidityMessage = css`
  color: ${({ valid }) => (valid ? TEXT.valid : TEXT.error)};
  font-size: 12px;
`;
