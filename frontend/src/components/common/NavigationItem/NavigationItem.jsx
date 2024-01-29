import PropTypes from 'prop-types';
import * as S from './NavigationItem.styled';

const NavigationItem = (props) => {
  const { to, current, icon, children } = props;

  return (
    <S.Container to={to} current={current}>
      {icon}
      {children}
    </S.Container>
  );
};

NavigationItem.propTypes = {
  to: PropTypes.string.isRequired,
  current: PropTypes.bool.isRequired,
  icon: PropTypes.node.isRequired,
  children: PropTypes.string.isRequired,
};

export default NavigationItem;
