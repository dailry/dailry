import { useState } from 'react';

const useDrawUtils = (canvas, drawInstance) => {
  const [mode, setMode] = useState(undefined);

  const draw = (e) => drawInstance.move(e);
  const erase = (e) => drawInstance.erase(e);

  const startMoving = (event, callback) => {
    canvas.current.addEventListener('mousemove', callback);
    drawInstance.reposition(event);
  };

  const stopMoving = (callback) => {
    canvas.current.removeEventListener('mousemove', callback);
  };

  const saveCanvas = () => {
    localStorage.setItem('canvasImageUrl', drawInstance.getInfo());
  };

  const mouseEventHandlers = {
    drawMode: draw,
    eraseMode: erase,
  };

  return {
    saveCanvas,
    startMoving,
    stopMoving,
    mouseEventHandlers,
    mode,
    setMode,
  };
};

export default useDrawUtils;
