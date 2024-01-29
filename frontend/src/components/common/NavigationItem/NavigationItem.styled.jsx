import PropTypes from 'prop-types';
import { styled, css } from 'styled-components';
import { MENU } from '../../../styles/color';

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
  column-gap: 14px;
  overflow: hidden;

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

Container.propTypes = {
  current: PropTypes.bool,
};

export const CreateForm = styled.form`
  background-color: ${MENU.boxMouseOver};
  ${BaseContainer}
`;
