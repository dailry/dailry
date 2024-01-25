import { useRef } from 'react';
import PropTypes from 'prop-types';
import useDrawUtils from './useDrawUtils';
import Button from '../../common/Button/Button';
import useDrawInstance from './useDrawInstance';

const Drawing = () => {
  const canvas = useRef(null);

  const { drawInstance } = useDrawInstance(canvas);
  const {
    saveCanvas,
    mouseEventHandlers,
    startMoving,
    stopMoving,
    mode,
    setMode,
  } = useDrawUtils(canvas, drawInstance);

  return (
    <>
      <canvas
        onMouseDown={(e) => startMoving(e, mouseEventHandlers[mode])}
        onMouseUp={() => stopMoving(mouseEventHandlers[mode])}
        ref={canvas}
      ></canvas>
      <Button onClick={() => saveCanvas()}>저장</Button>
      <Button onClick={() => setMode('drawMode')}>그리기</Button>

      <Button onClick={() => setMode('eraseMode')}>지우기</Button>
      <input
        type="color"
        onChange={(e) => drawInstance.setColor(e.target.value)}
      />
      <input
        type="range"
        onChange={(e) => drawInstance.setSize(e.target.value)}
      />
    </>
  );
};

Drawing.propTypes = {
  id: PropTypes.string,
};

export default Drawing;
