import { styled } from 'styled-components';
import { BACKGROUND } from '../../styles/color';

export const FlexWrapper = styled.div`
  flex: 0.5;
  display: flex;
  gap: 10px;
`;

export const CanvasWrapper = styled.div`
  position: relative;
  height: calc(100dvh - 20px);
  aspect-ratio: 1.35/1;

  overflow: hidden;
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

export const ToolWrapper = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 10px;

  padding: 5px;
  border-radius: 15px;
  background-color: ${BACKGROUND.bright};
`;
