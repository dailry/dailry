import { styled } from 'styled-components';
import { MENU } from '../../../styles/color';

export const ToolWrapper = styled.button`
  aspect-ratio: 1;
  padding: 10px;

  border-radius: 4px;

  background-color: ${({ selected }) =>
    selected ? MENU.tool : MENU.boxCurrent};

  @media (min-aspect-ratio: 7/4) {
    width: 100%;
  }

  @media (max-aspect-ratio: 7/4) {
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
