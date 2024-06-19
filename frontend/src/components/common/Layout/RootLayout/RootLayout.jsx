import { Outlet, useNavigate } from 'react-router-dom';
import * as S from './RootLayout.styled';
import Navigation from '../../Navigation/Navigation';
import { PATH_NAME } from '../../../../constants/routes';
import { BackIcon, DailryLogoIcon } from '../../../../assets/svg';
import useSetCurrentDailryWithPath from '../../../../hooks/useSetCurrentDailryWithPath';
import useSetCurrentDailryPageWithPath from '../../../../hooks/useSetCurrentDailryPageWithPath';

const RootLayout = (props) => {
  const { templateSize } = props;
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate(-1);
  };

  useSetCurrentDailryWithPath();
  useSetCurrentDailryPageWithPath();
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
      <S.BackIconWrapper onClick={handleBackClick}>
        <BackIcon />
      </S.BackIconWrapper>
      <S.LogoWrapper to={PATH_NAME.Home}>
        <DailryLogoIcon />
      </S.LogoWrapper>
    </S.Background>
  );
};

RootLayout.propTypes = {
  templateSize: 'full' || 'half',
};

export default RootLayout;
