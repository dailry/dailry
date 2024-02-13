import { styled } from 'styled-components';
import { MODAL } from '../../../styles/color';

export const ModalBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10000;

  background-color: ${MODAL.background};

  width: 100vw;
  height: 100dvh;
`;

export const ModalWrapper = styled.div`
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: ${MODAL.box};

  height: 80%;
  aspect-ratio: 1.5/1;

  border-radius: 16px;
`;

export const TopArea = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  padding: 10px;
  border-bottom: 1px solid #000000;
`;

export const ItemArea = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  padding: 10px;
`;

export const ItemWrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 4px;
  width: 25%;
  border-radius: 4px;
  cursor: pointer;
  box-sizing: border-box;

  border: 2px solid
    ${({ selected }) => (selected ? MODAL.selectedItem : MODAL.item)};
`;

export const PageNumberArea = styled.div`
  text-align: center;
`;

export const ThumbnailArea = styled.div``;
