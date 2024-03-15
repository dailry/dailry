import PropTypes from 'prop-types';
import { forwardRef } from 'react';
import * as S from '../../pages/DailryPage/DailryPage.styled';

const DecorateWrapper = forwardRef((props, ref) => {
  const {
    id,
    children,
    order,
    initialStyle,
    canEdit,
    typeContent,
    onMouseDown,
    onMouseUp,
  } = props;
  const { position, size, rotation } = initialStyle;

  return (
    <div
      id={id}
      onMouseDown={onMouseDown}
      onMouseUp={onMouseUp}
      ref={ref}
      style={{
        top: `${position.y}px`,
        left: `${position.x}px`,
        width: `${size?.width}px`,
        height: `${size?.height}px`,
        rotate: `${rotation}deg`,
        ...S.ElementStyle({
          typeContent,
          canEdit,
          order,
        }),
      }}
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
  initialStyle: PropTypes.object,
  position: PropTypes.object,
  typeContent: PropTypes.object,
  order: PropTypes.number,
  rotation: PropTypes.string,
  size: PropTypes.object,
  canEdit: PropTypes.bool,
  onMouseDown: PropTypes.func,
  onMouseUp: PropTypes.func,
};
