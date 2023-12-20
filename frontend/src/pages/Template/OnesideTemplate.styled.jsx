import { styled } from 'styled-components';
import { BACKGROUND } from '../../styles/color';

export const Background = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;

  width: 100%;
  height: 100dvh;
  background: ${BACKGROUND.gradient};
`;

export const Container = styled.div`
  flex-basis: 70%;
  display: flex;
  justify-content: center;
  align-items: center;

  height: calc(100dvh - 20px);
  margin: 10px;

  border-radius: 8px;
  background-color: ${BACKGROUND.bright};
  box-shadow: 0 4px 4px #00000040;
`;
