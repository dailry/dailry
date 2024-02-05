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
  overflow: visible;

  height: 24px;
  width: fit-content;
`;

export const HamburgerMenu = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  row-gap: 8px;
  position: absolute;
  top: 30px;
  right: ${({ anchor }) => (anchor === 'left' ? '0px' : 'auto')};
  left: ${({ anchor }) => (anchor === 'right' ? '0px' : 'auto')};

  z-index: 2;

  width: auto;
  padding: 8px 16px 8px 8px;

  border-radius: 10px;
  background: #fff;

  box-shadow:
    4px -4px 10px 0 rgba(0, 0, 0, 0.25),
    -4px 4px 10px 0 rgba(0, 0, 0, 0.25);
  animation: ${fadeIn} 0.3s;
`;

export const HamburgerItem = styled.div`
  display: flex;
  align-items: center;
  column-gap: 8px;

  width: 100%;

  font-size: 11px;

  cursor: pointer;
  white-space: nowrap;

  &:hover {
    background-color: rgba(0, 0, 0, 0.1);
  }
`;

export const HamburgerMoreButton = styled(HamburgerMoreIcon)`
  cursor: pointer;

  &:hover {
    border-radius: 50%;
    background-color: rgba(0, 0, 0, 0.1);
  }
`;

export const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1;

  width: 100vw;
  height: 100vh;

  cursor: default;
`;
