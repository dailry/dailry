import { useState } from 'react';
import PropTypes from 'prop-types';
import * as S from './Hamburger.styled';

const Hamburger = (props) => {
  const { children } = props;
  const [isOpen, setIsOpen] = useState(false);

  return (
    <S.HamburgerContainer>
      <S.HamburgerMoreButton
        onClick={() => {
          setIsOpen((prev) => !prev);
        }}
      />
      {isOpen && <S.Overlay onClick={() => setIsOpen(false)} />}
      {isOpen && children}
    </S.HamburgerContainer>
  );
};

Hamburger.propTypes = {
  children: PropTypes.node,
};

export default Hamburger;
