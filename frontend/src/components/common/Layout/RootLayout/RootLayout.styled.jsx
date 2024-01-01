import { styled } from 'styled-components';
import { BACKGROUND } from '../../../../styles/color';

export const Background = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;

  width: 100%;
  height: 100dvh;
  background: ${BACKGROUND.gradient};
`;

export const NavigationContainer = styled.div`
  flex-basis: 30%;
  height: calc(100dvh - 20px);
  margin: 10px;
`;

export const PageContainer = styled.div`
  flex-basis: 70%;
  height: calc(100dvh - 20px);
  margin: 10px;
`;
