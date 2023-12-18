import { styled } from 'styled-components';
import { BACKGROUND } from '../../styles/color';

export const Container = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100dvh;
  background: ${BACKGROUND.gradient};
`;
