import PropTypes from 'prop-types';
import styled from 'styled-components';
import { BACKGROUND } from '../../../styles/color';

const containerPropsStyles = (current) => ({
  background: current ? BACKGROUND.bright : 'none',
  boxShadow: current ? '0px 2px 4px 0px rgba(0, 0, 0, 0.25)' : 'none',
  '&:hover': {
    background: current ? BACKGROUND.bright : 'rgba(0, 0, 0, 0.1)',
  },
});

const BaseContainer = styled.a`
  width: 262px;
  height: 50px;
  border-radius: 20px;
  display: flex;
  align-items: center;
  column-gap: 14px;
  font-size: 16px;
  padding: 0px 16px;
  font-weight: 700;
  cursor: pointer;
`;

export const Container = styled(BaseContainer)((props) => {
  const { current } = props;
  return {
    ...containerPropsStyles(current),
  };
});

Container.propTypes = {
  current: PropTypes.bool,
};
