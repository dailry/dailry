import PropTypes from 'prop-types';
import * as S from './NavigationItem.styled';
import Hamburger from '../Hamburger/Hamburger';

const NavigationItem = (props) => {
  const { current, icon, children, onClick, hamburger } = props;

  return (
    <S.Container current={current} onClick={onClick}>
      <S.IconWrapper>{icon}</S.IconWrapper>
      <S.TextWrapper>{children}</S.TextWrapper>
      <Hamburger>{hamburger}</Hamburger>
    </S.Container>
  );
};

NavigationItem.propTypes = {
  current: PropTypes.bool.isRequired,
  icon: PropTypes.node.isRequired,
  children: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  hamburger: PropTypes.node,
};

export default NavigationItem;
