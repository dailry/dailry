import { styled } from 'styled-components';
import { BACKGROUND } from '../../../../styles/color';

export const Background = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  overflow: hidden;

  width: 100%;
  height: 100dvh;
  background: ${BACKGROUND.gradient};
`;

export const NavigationContainer = styled.div`
  flex-grow: 1;
  overflow-x: visible;
  overflow-y: auto;
  width: 180px;
  max-width: 270px;
  height: calc(100dvh - 20px);
  margin: 10px;
`;

export const PageContainer = styled.div`
  flex-grow: 1;
  height: calc(100dvh - 20px);
  margin: 10px 10px 10px 0;
`;

const templateWidth = {
  half: '80%',
  full: '100%',
};

export const DefaultPageTemplate = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 100%;
  width: ${({ templateSize }) => templateWidth[templateSize]};
`;

export const BrightColoredPageTemplate = styled(DefaultPageTemplate)`
  border-radius: 8px;
  background-color: ${BACKGROUND.bright};
  box-shadow: 0 4px 4px #00000040;
`;
