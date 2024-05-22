import styled, { keyframes } from 'styled-components';

const fadeIn = keyframes`
    0%{
      opacity: 0;
    }
    100%{
      opacity: 1;
    }
`;

export const TooltipContainer = styled.div`
  position: relative;
`;

export const Tooltip = styled.div`
  position: absolute;
  z-index: 2;

  width: max-content;

  padding: 8px 8px;

  border-radius: 10px;
  background: #fff;

  box-shadow:
    4px -4px 10px 0 rgba(0, 0, 0, 0.25),
    -4px 4px 10px 0 rgba(0, 0, 0, 0.25);
  animation: ${fadeIn} 0.3s;
  cursor: default;
`;
