import { makeMoveable, Rotatable, Draggable, Resizable } from 'react-moveable';
import PropTypes from 'prop-types';
import MoveableHelper from 'moveable-helper';
import { useState } from 'react';

const MoveableInstance = makeMoveable([Draggable, Rotatable, Resizable]);

const Moveable = (props) => {
  const { target } = props;
  const [helper] = useState(() => {
    return new MoveableHelper();
  });

  return (
    <MoveableInstance
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

Moveable.propTypes = {
  target: PropTypes.object,
};
export default Moveable;
