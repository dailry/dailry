import PropTypes from 'prop-types';
import { forwardRef } from 'react';
import * as S from '../../pages/DailryPage/DailryPage.styled';

const DecorateWrapper = forwardRef((props, ref) => {
  const {
    id,
    children,
    order,
    position,
    size,
    typeContent,
    canEdit,
    onMouseDown,
    onMouseUp,
  } = props;

  return (
    <div
      id={id}
      onMouseDown={onMouseDown}
      onMouseUp={onMouseUp}
      ref={ref}
      style={S.ElementStyle({
        position,
        typeContent,
        order,
        size,
        canEdit,
      })}
    >
      {children}
    </div>
  );
});

DecorateWrapper.displayName = 'decorate';

export default DecorateWrapper;

DecorateWrapper.propTypes = {
  children: PropTypes.node,
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
  onMouseUp: PropTypes.func,
};
