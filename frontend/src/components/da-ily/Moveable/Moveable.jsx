import Moveable from 'react-moveable';
import PropTypes from 'prop-types';
import MoveableHelper from 'moveable-helper';
import { useState } from 'react';

const MoveableComponent = (props) => {
  const { target, setCommonProperty } = props;
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
      onRotateStart={helper.onRotateStart}
      onRotate={helper.onRotate}
      onRotateEnd={(e) => {
        const transformString = e.target.style.transform;
        const deg = transformString.split('rotate(')[1].split('deg')[0];

        setCommonProperty({ rotation: deg });
      }}
      onResizeStart={helper.onResizeStart}
      onResize={helper.onResize}
      onResizeEnd={(e) =>
        setCommonProperty({
          size: {
            width: e.target.style.width,
            height: e.target.style.height,
          },
        })
      }
    />
  );
};

MoveableComponent.propTypes = {
  target: PropTypes.object,
  setCommonProperty: PropTypes.func,
};
export default MoveableComponent;
