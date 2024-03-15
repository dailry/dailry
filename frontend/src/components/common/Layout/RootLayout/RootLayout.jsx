import { Outlet } from 'react-router-dom';
import * as S from './RootLayout.styled';
import Navigation from '../../Navigation/Navigation';

const RootLayout = (props) => {
  const { templateSize } = props;

  return (
    <S.Background>
      {Navigation && (
        <S.NavigationContainer>
          <Navigation />
        </S.NavigationContainer>
      )}
      <S.PageContainer>
        {templateSize ? (
          <S.BrightColoredPageTemplate templateSize={templateSize}>
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
  templateSize: 'full' || 'half',
};

export default RootLayout;
