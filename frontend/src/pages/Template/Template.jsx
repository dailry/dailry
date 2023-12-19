import PropTypes from 'prop-types';
import { Container } from './Template.styled';

const Template = ({ children }) => {
  return <Template>{children}</Template>;
};

Template.PropTypes = {
  children: PropTypes.elementType,
};

export default Template;
