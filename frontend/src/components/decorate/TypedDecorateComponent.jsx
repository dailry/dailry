import PropTypes from 'prop-types';

import { DECORATE_COMPONENT } from '../../constants/decorateComponent';

const TypedDecorateComponent = (props) => {
  const { type, canEdit, typeContent, setTypeContent } = props;
  const Com = DECORATE_COMPONENT[type];

  return (
    <Com
      canEdit={canEdit}
      typeContent={typeContent}
      setTypeContent={setTypeContent}
    />
  );
};

export default TypedDecorateComponent;

TypedDecorateComponent.propTypes = {
  type: PropTypes.string,
  canEdit: PropTypes.bool,
  typeContent: PropTypes.object,
  setTypeContent: PropTypes.func,
};
