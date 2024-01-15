import { useState } from 'react';
import useDrawInstance from './useDrawInstance';

const useDrawing = (canvas) => {
  const { drawInstance, draw, erase } = useDrawInstance(canvas);

  const [mode, setMode] = useState(undefined);

  const startMoving = (event, callback) => {
    canvas.current.addEventListener('mousemove', callback);
    drawInstance.reposition(event);
  };

  const stopMoving = (callback) => {
    canvas.current.removeEventListener('mousemove', callback);
  };

  const saveCanvas = () => {
    localStorage.setItem('drawData', JSON.stringify(drawInstance.getInfo()));
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

export default useDrawing;
