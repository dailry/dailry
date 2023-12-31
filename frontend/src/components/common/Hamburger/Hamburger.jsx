import { useState } from 'react';
import PropTypes from 'prop-types';
import {
  HamburgerContainer,
  HamburgerMenu,
  HamburgerMoreButton,
  Overlay,
} from './Hamburger.styled';

const Hamburger = (props) => {
  const { anchor = 'right', children } = props;
  const [isOpen, setIsOpen] = useState(false);

  return (
    <HamburgerContainer>
      <HamburgerMoreButton
        onClick={() => {
          setIsOpen((prev) => !prev);
        }}
      />
      {isOpen && <Overlay onClick={() => setIsOpen(false)} />}
      {isOpen && <HamburgerMenu anchor={anchor}>{children}</HamburgerMenu>}
    </HamburgerContainer>
  );
};

Hamburger.propTypes = {
  anchor: 'right' || 'left',
  children: PropTypes.node,
};

export default Hamburger;
