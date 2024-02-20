import Moveable from 'react-moveable';
import PropTypes from 'prop-types';
import MoveableHelper from 'moveable-helper';
import { useState } from 'react';

const MoveableComponent = (props) => {
  const { target } = props;
  const [helper] = useState(() => {
    return new MoveableHelper();
  });

  return (
    <Moveable
      target={target}
      draggable={true}
      resizable={true}
      rotatable={true}
      throttleDrag={0}
      throttleResize={0}
      throttleRotate={0}
      onDragStart={helper.onDragStart}
      onDrag={helper.onDrag}
      onRotateStart={helper.onRotateStart}
      onRotate={helper.onRotate}
      onResizeStart={helper.onResizeStart}
      onResize={helper.onResize}
    />
  );
};

MoveableComponent.propTypes = {
  target: PropTypes.object,
};
export default MoveableComponent;
