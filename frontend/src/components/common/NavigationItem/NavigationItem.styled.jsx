import PropTypes from 'prop-types';
import { styled, css } from 'styled-components';
import { MENU, TEXT } from '../../../styles/color';

const containerPropsStyles = (current) => ({
  background: current ? MENU.boxCurrent : 'none',
  boxShadow: current ? '0px 2px 4px 0px rgba(0, 0, 0, 0.25)' : 'none',
  '&:hover': {
    background: current ? MENU.boxCurrent : MENU.boxMouseOver,
  },
});

const BaseContainer = css`
  display: flex;
  align-items: center;
  justify-content: center;
  column-gap: 14px;
  white-space: nowrap;
  text-overflow: ellipsis;

  width: 100%;
  height: 50px;
  padding: 0 16px;

  border-radius: 20px;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
`;

export const Container = styled.button`
  ${BaseContainer}
  ${(props) => {
    const { current } = props;
    return {
      ...containerPropsStyles(current),
    };
  }}
`;

export const CreateForm = styled.form`
  ${BaseContainer};

  z-index: 987654321;

  box-sizing: border-box;
  padding: 12px;
  cursor: default;
  border: 2px dashed ${MENU.line};

  background-color: ${MENU.boxCurrent};
`;

export const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  z-index: 987654320;

  width: 100vw;
  height: 100vh;

  cursor: default;
`;

export const IconWrapper = styled.div`
  width: 20px;
  height: 20px;
`;

export const TextWrapper = styled.div`
  flex-grow: 1;
  text-align: start;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const InputArea = styled.input`
  flex-grow: 1;
  width: 150px;

  border: none;
  font-size: 16px;
  font-weight: 700;

    &:focus {
        outline: none;
    }

    ::placeholder {
        color: ${TEXT.placeHolder};
`;

Container.propTypes = {
  current: PropTypes.bool,
};
