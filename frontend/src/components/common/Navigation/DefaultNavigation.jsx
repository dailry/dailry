import PropTypes from 'prop-types';
import NavigationItem from '../NavigationItem/NavigationItem';
import { NavigationItemIcon } from '../../../assets/svg';

const DefaultNavigation = () => {
  return (
    <>
      <NavigationItem icon={<NavigationItemIcon />}>dailry</NavigationItem>
      <NavigationItem icon={<NavigationItemIcon />} current>
        dailry
      </NavigationItem>
    </>
  );
};

export default DefaultNavigation;

DefaultNavigation.propTypes = {
  children: PropTypes.node,
};
