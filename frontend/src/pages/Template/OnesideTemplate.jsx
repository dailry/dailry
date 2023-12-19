import PropTypes from 'prop-types';
import { Background, Container } from './OnesideTemplate.styled';

const OnesideTemplate = ({ children }) => {
  return (
    <Background>
      <Container>{children}</Container>
    </Background>
  );
};

OnesideTemplate.propTypes = {
  children: PropTypes.elementType,
};

export default OnesideTemplate;
