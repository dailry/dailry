import PropTypes from 'prop-types';
import Mvb from 'react-moveable';

const Moveable = (props) => {
  const { moveableRef, target } = props;
  return (
    <Mvb
      ref={moveableRef}
      target={target}
      draggable={true}
      onDrag={({ transform }) => {
        target.style.transform = transform;
      }}
      throttleDrag={0}
      resizable={true}
      throttleResize={0}
      onResize={({ width, height }) => {
        target.style.width = `${width}px`;
        target.style.height = `${height}px`;
      }}
      rotatable={true}
      throttleRotate={0}
      onRotate={({ transform }) => {
        target.style.transform = transform;
      }}
    />
  );
};

Moveable.propTypes = {
  moveableRef: PropTypes.elementType,
  target: PropTypes.elementType,
};
export default Moveable;
