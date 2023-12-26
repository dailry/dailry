import PropTypes from 'prop-types';

import { Outlet } from 'react-router-dom';
import {
  Background,
  NavigationContainer,
  PageContainer,
} from './RootLayout.styled';

const RootLayout = (props) => {
  const { navigation: Navigation, pageTemplate: PageTemplate } = props;
  return (
    <Background>
      <NavigationContainer>
        <Navigation />
      </NavigationContainer>
      <PageContainer>
        <PageTemplate>
          <Outlet />
        </PageTemplate>
      </PageContainer>
    </Background>
  );
};
export default RootLayout;

RootLayout.propTypes = {
  navigation: PropTypes.elementType,
  pageTemplate: PropTypes.elementType,
};
