import { styled, css } from 'styled-components';
import { Link } from 'react-router-dom';
import { MENU } from '../../../styles/color';

export const NavigationWrapper = styled.div`
  display: flex;
  flex-direction: column;
  overflow: visible;

  width: 100%;
`;

export const NameWrapper = styled(Link)`
  display: flex;
  flex-direction: column;
  align-items: start;

  margin: 0 0 0 60px;

  &:hover {
    background-color: ${MENU.boxMouseOver};
  }
`;

export const Line = styled.hr`
  display: inline;
  margin: 10px;
  width: calc(100% - 20px);
  height: 1px;
  border: 1px solid ${MENU.line};
`;

export const ItemName = css`
  padding: 0 0 8px 15px;

  color: ${MENU.title};
  font-size: 16px;
  font-weight: 700;
`;

export const AddDailry = styled.button`
  padding: 10px;

  text-align: start;
  font-weight: 700;
  font-size: 16px;
  color: ${MENU.text};
  letter-spacing: -1px;

  &:hover {
    color: ${MENU.textMouseOver};
  }
`;
