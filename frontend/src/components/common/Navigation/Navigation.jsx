import PropTypes from 'prop-types';
import { useLocation } from 'react-router-dom';
import DailryNavigation from './DailryNavigation';
import CommunityNavigation from './CommunityNavigation';
import AdminNavigation from './AdminNavigation';
import { PATH_NAME } from '../../../constants/routes';

const Navigation = () => {
  const location = useLocation();

  return (
    <>
      {location.pathname.startsWith(PATH_NAME.Dailry) && <DailryNavigation />}
      {location.pathname.startsWith(PATH_NAME.Community) && (
        <CommunityNavigation />
      )}
      {location.pathname.startsWith(PATH_NAME.AdminMembers) && (
        <AdminNavigation />
      )}
    </>
  );
};

Navigation.propTypes = {
  children: PropTypes.node,
};

export default Navigation;
