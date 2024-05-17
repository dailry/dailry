import { styled } from 'styled-components';
import { MENU } from '../../styles/color';
import { MEDIA_RATIO } from '../../constants/media';

export const ToolWrapper = styled.button`
  aspect-ratio: 1;
  padding: 10px;

  border-radius: 4px;

  background-color: ${({ selected }) =>
    selected ? MENU.tool : MENU.boxCurrent};

  @media (min-aspect-ratio: ${MEDIA_RATIO}) {
    width: 100%;
  }

  @media (max-aspect-ratio: ${MEDIA_RATIO}) {
    height: 100%;
  }
`;

export const IconProps = (selected) => {
  return {
    fill: selected ? MENU.boxCurrent : MENU.tool,
    style: {
      width: '100%',
      height: '100%',
    },
  };
};
export const fillColor = (selected) => (selected ? MENU.boxCurrent : MENU.tool);
