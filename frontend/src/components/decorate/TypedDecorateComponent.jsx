import PropTypes from 'prop-types';

import { DECORATE_COMPONENT } from '../../constants/decorateComponent';

const TypedDecorateComponent = (props) => {
  const { type, editable, typeContent, setTypeContent } = props;
  const Com = DECORATE_COMPONENT[type];

  return (
    <Com
      editable={editable}
      typeContent={typeContent}
      setTypeContent={setTypeContent}
    />
  );
};

export default TypedDecorateComponent;

TypedDecorateComponent.propTypes = {
  type: PropTypes.string,
  editable: PropTypes.bool,
  typeContent: PropTypes.object,
  setTypeContent: PropTypes.func,
};
