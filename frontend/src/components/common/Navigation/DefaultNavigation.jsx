import PropTypes from 'prop-types';
import NavigationItem from '../NavigationItem/NavigationItem';
import { NavigationItemIcon } from '../../../assets/svg';

const DefaultNavigation = () => {
  return (
    <>
      <NavigationItem icon={<NavigationItemIcon />}>daily</NavigationItem>
      <NavigationItem icon={<NavigationItemIcon />} current>
        daily
      </NavigationItem>
    </>
  );
};

export default DefaultNavigation;

DefaultNavigation.propTypes = {
  children: PropTypes.node,
};
