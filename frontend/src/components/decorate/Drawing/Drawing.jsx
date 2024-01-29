import { useRef } from 'react';
import PropTypes from 'prop-types';
import useDrawUtils from './useDrawUtils';
import useDrawInstance from './useDrawInstance';

const Drawing = () => {
  const canvas = useRef(null);

  const { drawInstance } = useDrawInstance(canvas);
  const { mouseEventHandlers, startMoving, stopMoving, mode } = useDrawUtils(
    canvas,
    drawInstance,
  );

  return (
    <>
      <canvas
        onMouseDown={(e) => startMoving(e, mouseEventHandlers[mode])}
        onMouseUp={() => stopMoving(mouseEventHandlers[mode])}
        ref={canvas}
      ></canvas>

      {/** 툴바 에서 사용 가능한 코드들 */}
      {/*
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
      */}
    </>
  );
};

Drawing.propTypes = {
  id: PropTypes.string,
};

export default Drawing;
