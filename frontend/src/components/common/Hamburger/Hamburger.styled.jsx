import styled, { keyframes } from 'styled-components';
import { HamburgerMoreIcon } from '../../../assets/svg';

const fadeIn = keyframes`
    0%{
      opacity: 0;
    }
    100%{
      opacity: 1;
    }
`;

export const HamburgerContainer = styled.div`
  position: relative;
  width: fit-content;
`;

export const HamburgerMenu = styled.div`
  position: absolute;
  z-index: 2;
  width: auto;
  top: 30px;
  right: ${({ anchor }) => (anchor === 'left' ? '0px' : 'auto')};
  left: ${({ anchor }) => (anchor === 'right' ? '0px' : 'auto')};
  display: flex;
  flex-direction: column;
  align-items: center;
  row-gap: 8px;
  border-radius: 10px;
  background: #fff;
  box-shadow:
    4px -4px 10px 0px rgba(0, 0, 0, 0.25),
    -4px 4px 10px 0px rgba(0, 0, 0, 0.25);
  padding: 8px 16px 8px 8px;
  animation: ${fadeIn} 0.3s;
`;

export const HamburgerItem = styled.div`
  display: flex;
  column-gap: 8px;
  width: 100%;
  white-space: nowrap;
  align-items: center;

  font-size: 11px;
  cursor: pointer;
`;

export const HamburgerMoreButton = styled(HamburgerMoreIcon)`
  cursor: pointer;

  &:hover {
    border-radius: 50%;
    background-color: rgba(0, 0, 0, 0.1);
  }
`;

export const Overlay = styled.div`
  position: absolute;
  z-index: 1;
  top: 0px;
  left: 0px;
  width: 100vw;
  height: 100vh;
`;
