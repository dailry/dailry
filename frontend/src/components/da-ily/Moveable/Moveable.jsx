import Moveable from 'react-moveable';
import PropTypes from 'prop-types';

const MoveableComponent = (props) => {
  const { target, setCommonProperty } = props;

  return (
    <Moveable
      target={target}
      draggable={true}
      resizable={true}
      throttleDrag={0}
      throttleResize={0}
      throttleRotate={0}
      onRender={(e) => {
        e.target.style.cssText += e.cssText;
      }}
      onDragEnd={(e) => {
        if (e.isDrag) {
          const movedX = parseInt(e.lastEvent.translate[0], 10);
          const movedY = parseInt(e.lastEvent.translate[1], 10);

          setCommonProperty({
            position: {
              x: movedX,
              y: movedY,
            },
          });
        }
      }}
      onRotateEnd={(e) => {
        const transformString = e.target.style.transform;
        const deg = transformString.split('rotate(')[1].split('deg')[0];

        setCommonProperty({ rotation: deg });
      }}
      onResizeEnd={(e) => {
        setCommonProperty({
          size: {
            width: e.lastEvent.width,
            height: e.lastEvent.height,
          },
        });
      }}
    />
  );
};

MoveableComponent.propTypes = {
  target: PropTypes.object,
  setCommonProperty: PropTypes.func,
};
export default MoveableComponent;
