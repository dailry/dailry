import PropTypes, { elementType } from 'prop-types';
import { Container } from './Template.styled';

const Template = ({ children }) => {
  return <Container>{children}</Container>;
};

Template.propTypes = {
  children: elementType,
};

export default Template;
