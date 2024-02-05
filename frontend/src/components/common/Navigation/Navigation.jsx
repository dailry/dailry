import PropTypes from 'prop-types';
import { useLocation } from 'react-router-dom';
import DailryNavigation from './DailryNavigation';
import CommunityNavigation from './CommunityNavigation';
import AdminNavigation from './AdminNavigation';

const Navigation = () => {
  const location = useLocation();

  return (
    <>
      {location.pathname.startsWith('/dailry') && <DailryNavigation />}
      {location.pathname.startsWith('/community') && <CommunityNavigation />}
      {location.pathname.startsWith('/admin') && <AdminNavigation />}
    </>
  );
};

Navigation.propTypes = {
  children: PropTypes.node,
};

export default Navigation;
