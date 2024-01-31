import PropTypes from 'prop-types';
import * as S from './NavigationItem.styled';

const NavigationItem = (props) => {
  const { current, icon, children, onClick } = props;

  return (
    <S.Container current={current} onClick={onClick}>
      <S.IconWrapper>{icon}</S.IconWrapper>
      <S.TextWrapper>{children}</S.TextWrapper>
    </S.Container>
  );
};

NavigationItem.propTypes = {
  current: PropTypes.bool.isRequired,
  icon: PropTypes.node.isRequired,
  children: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default NavigationItem;
