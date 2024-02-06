import { styled } from 'styled-components';
import { BACKGROUND } from '../../styles/color';
import { MEDIA_RATIO } from '../../constants/media';

export const FlexWrapper = styled.div`
  flex-grow: 1;
  display: flex;
  gap: 10px;

  @media (max-aspect-ratio: ${MEDIA_RATIO}) {
    flex-direction: column;
    height: 100%;
  }
`;

export const CanvasWrapper = styled.div`
  position: relative;
  aspect-ratio: 1.35/1;

  overflow: hidden;
  border-radius: 8px;
  background-color: ${BACKGROUND.paper};

  @media (min-aspect-ratio: ${MEDIA_RATIO}) {
    height: calc(100dvh - 20px);
  }

  @media (max-aspect-ratio: ${MEDIA_RATIO}) {
    width: 100%;
  }
`;

export const ElementStyle = ({ position, properties, order, size }) => {
  return {
    position: 'absolute',
    left: position.x,
    top: position.y,
    width: size.width,
    height: size.height,
    backgroundColor: properties.backgroundColor,
    zIndex: order,
  };
};

export const SideWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-grow: 1;

  @media (max-aspect-ratio: ${MEDIA_RATIO}) {
    flex-direction: row;
  }
`;

export const ToolWrapper = styled.div`
  display: inline-flex;
  flex-direction: column;
  gap: 8px;

  padding: 8px;
  border-radius: 8px;
  background-color: ${BACKGROUND.bright};

  @media (min-aspect-ratio: ${MEDIA_RATIO}) {
    width: 100%;
    max-width: 76px;
  }

  @media (max-aspect-ratio: ${MEDIA_RATIO}) {
    flex-direction: row;
    height: 100%;
    max-height: 76px;
  }
`;

export const ArrowWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 5px;

  @media (min-aspect-ratio: ${MEDIA_RATIO}) {
    width: 100%;
    max-width: 76px;
  }

  @media (max-aspect-ratio: ${MEDIA_RATIO}) {
    flex-direction: row;
    width: 76px;
    height: 76px;
  }
`;

export const ArrowButton = styled.button`
  display: flex;
  flex-grow: 1;
  height: 28px;

  justify-content: ${({ direction }) =>
    direction === 'left' ? 'start' : 'end'};
`;
