import { useState } from 'react';
import PropTypes from 'prop-types';
import * as S from './Hamburger.styled';

const Hamburger = (props) => {
  const { children } = props;
  const [isOpen, setIsOpen] = useState(false);

  const handleHamburgerClick = (e) => {
    e.stopPropagation();
    setIsOpen((prev) => !prev);
  };

  const handleOverlayClick = (e) => {
    e.stopPropagation();
    setIsOpen(false);
  };

  return (
    <S.HamburgerContainer>
      <S.HamburgerMoreButton onClick={handleHamburgerClick} />
      {isOpen && <S.Overlay onClick={handleOverlayClick} />}
      {isOpen && children}
    </S.HamburgerContainer>
  );
};

Hamburger.propTypes = {
  children: PropTypes.node,
};

export default Hamburger;
