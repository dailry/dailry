import PropTypes from 'prop-types';
import * as S from './NavigationItem.styled';
import { PATH_NAME } from '../../../constants/routes';

const NavigationItem = (props) => {
  const { to, current, icon, children } = props;

  return (
    <S.Container to={to} current={current}>
      {icon}
      {children}
    </S.Container>
  );
};

const isDailryUrl = (props, propName, componentName) => {
  const path = props[propName];

  if (!path.startsWith(PATH_NAME.Dailry)) {
    return new Error(
      `Invalid prop ${propName} supplied to ${componentName}. It should start with '/dailry'.`,
    );
  }
  return null;
};

NavigationItem.propTypes = {
  to: isDailryUrl,
  current: PropTypes.bool,
  icon: PropTypes.node,
  children: PropTypes.string.isRequired,
};

export default NavigationItem;
