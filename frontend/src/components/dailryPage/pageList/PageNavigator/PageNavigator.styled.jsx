import styled from 'styled-components';
import { MEDIA_RATIO } from '../../../../constants/media';

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

export const NumberButton = styled.button`
  flex-grow: 2;
  font-size: 24px;
  color: #ffffff;
`;
