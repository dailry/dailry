import RootLayout from './RootLayout/RootLayout';
import { BrightColoredPageTemplate } from '../PageTemplate/PageTemplate.styled';
import DefaultNavigation from '../Navigation/DefaultNavigation';

const DefaultLayout = () => {
  return (
    <RootLayout
      navigation={DefaultNavigation}
      pageTemplate={BrightColoredPageTemplate}
    />
  );
};

export default DefaultLayout;
