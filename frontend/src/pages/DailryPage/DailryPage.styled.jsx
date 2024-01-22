import { styled } from 'styled-components';
import { BACKGROUND } from '../../styles/color';

export const FlexWrapper = styled.div`
  flex-grow: 1;
  display: flex;
  gap: 10px;
`;

export const CanvasWrapper = styled.div`
  position: relative;
  height: calc(100dvh - 20px);
  aspect-ratio: 1.35/1;

  border-radius: 8px;
  background-color: ${BACKGROUND.paper};
`;

export const ElementStyle = ({ position, properties }) => {
  return {
    position: 'absolute',
    left: position.x,
    top: position.y,
    width: properties.width,
    height: properties.height,
    backgroundColor: properties.backgroundColor,
  };
};

export const SideWrapper = styled.div`
  flex-grow: 1;
`;

export const ToolWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;

  min-width: 40px;
  max-width: 76px;

  padding: 8px;
  border-radius: 8px;
  background-color: ${BACKGROUND.bright};
`;
