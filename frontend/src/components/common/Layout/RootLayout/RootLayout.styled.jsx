import { styled } from 'styled-components';
import { BACKGROUND } from '../../../../styles/color';

export const Background = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100dvh;
  background: ${BACKGROUND.gradient};
`;

export const NavigationContainer = styled.div`
  flex: 0.5;
  width: 225px;
  height: calc(100dvh - 20px);
  margin: 10px;
`;

export const PageContainer = styled.div`
  flex: 0.5;
  height: calc(100dvh - 20px);
  margin: 10px;
`;
