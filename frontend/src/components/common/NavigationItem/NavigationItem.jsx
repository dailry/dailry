import PropTypes from 'prop-types';
import * as S from './NavigationItem.styled';

const NavigationItem = (props) => {
  const { current, icon, children } = props;

  return (
    <S.Container current={current}>
      {icon}
      {children}
    </S.Container>
  );
};

export default NavigationItem;

NavigationItem.propTypes = {
  current: PropTypes.bool,
  icon: PropTypes.node,
  children: PropTypes.string.isRequired,
};
