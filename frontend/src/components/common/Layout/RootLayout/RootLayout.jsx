import PropTypes from 'prop-types';

import { Outlet } from 'react-router-dom';
import * as S from './RootLayout.styled';
import Navigation from '../../Navigation/Navigation';

const RootLayout = (props) => {
  const { hasTemplate } = props;

  return (
    <S.Background>
      {Navigation && (
        <S.NavigationContainer>
          <Navigation />
        </S.NavigationContainer>
      )}
      <S.PageContainer>
        {hasTemplate ? (
          <S.BrightColoredPageTemplate>
            <Outlet />
          </S.BrightColoredPageTemplate>
        ) : (
          <Outlet />
        )}
      </S.PageContainer>
    </S.Background>
  );
};

RootLayout.propTypes = {
  hasTemplate: PropTypes.bool,
};

export default RootLayout;
