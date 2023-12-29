import styled from 'styled-components';
import { BACKGROUND } from '../../../styles/color';

export const DefaultPageTemplate = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const BrightColoredPageTemplate = styled(DefaultPageTemplate)`
  border-radius: 8px;
  background-color: ${BACKGROUND.bright};
  box-shadow: 0 4px 4px #00000040;
`;
