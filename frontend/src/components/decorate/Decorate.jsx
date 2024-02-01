import PropTypes from 'prop-types';
import { forwardRef } from 'react';
import * as S from '../../pages/DailryPage/DailryPage.styled';
import { DECORATE_COMPONENT } from '../../constants/decorateComponent';

const Decorate = forwardRef((props, ref) => {
  const { id, type, order, position, size, typeContent, canEdit, onMouseDown } =
    props;

  return (
    <div
      id={id}
      onMouseDown={onMouseDown}
      ref={ref}
      style={S.ElementStyle({
        position,
        typeContent,
        order,
        size,
        canEdit,
      })}
    >
      {DECORATE_COMPONENT[type]}
    </div>
  );
});

Decorate.displayName = 'decorate';

export default Decorate;

Decorate.propTypes = {
  id: PropTypes.string,
  setTarget: PropTypes.func,
  index: PropTypes.number,
  type: PropTypes.string,
  position: PropTypes.object,
  typeContent: PropTypes.object,
  order: PropTypes.number,
  size: PropTypes.object,
  canEdit: PropTypes.bool,
  onMouseDown: PropTypes.func,
};
