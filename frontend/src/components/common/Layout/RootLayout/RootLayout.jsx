import PropTypes from 'prop-types';

import { Outlet } from 'react-router-dom';
import * as S from './RootLayout.styled';

const RootLayout = (props) => {
  const { navigation: Navigation } = props;
  return (
    <S.Background>
      {Navigation && (
        <S.NavigationContainer>
          <Navigation />
        </S.NavigationContainer>
      )}
      <S.PageContainer>
        <Outlet />
      </S.PageContainer>
    </S.Background>
  );
};
export default RootLayout;

RootLayout.propTypes = {
  navigation: PropTypes.elementType,
  pageTemplate: PropTypes.elementType,
};
