import { Outlet } from 'react-router-dom';
import {
  Background,
  NavigationContainer,
  PageContainer,
} from './RootLayout.styled';

const RootLayout = () => {
  return (
    <Background>
      <NavigationContainer></NavigationContainer>
      <PageContainer>
        <Outlet />
      </PageContainer>
    </Background>
  );
};
export default RootLayout;
