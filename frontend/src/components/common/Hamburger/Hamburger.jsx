import { useState } from 'react';
import PropTypes from 'prop-types';
import * as S from './Hamburger.styled';

const Hamburger = (props) => {
  const { anchor = 'right', children } = props;
  const [isOpen, setIsOpen] = useState(false);

  return (
    <S.HamburgerContainer>
      <S.HamburgerMoreButton
        onClick={() => {
          setIsOpen((prev) => !prev);
        }}
      />
      {isOpen && <S.Overlay onClick={() => setIsOpen(false)} />}
      {isOpen && <S.HamburgerMenu anchor={anchor}>{children}</S.HamburgerMenu>}
    </S.HamburgerContainer>
  );
};

Hamburger.propTypes = {
  anchor: 'right' || 'left',
  children: PropTypes.node,
};

export default Hamburger;
