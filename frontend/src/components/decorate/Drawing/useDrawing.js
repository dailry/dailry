import { useCallback, useEffect, useState } from 'react';
import { createCtx } from './canvas';
import Draw from './Draw';

const useDrawing = (canvas) => {
  const [contentsLoaded, setContentsLoaded] = useState(false);
  const [drawInstance, setDrawInstance] = useState(undefined);

  document.addEventListener('DOMContentLoaded', () => {
    setContentsLoaded(true);
  });

  const move = (e) => drawInstance.move(e);

  const startDrawing = (event) => {
    canvas.current.addEventListener('mousemove', move);
    drawInstance.reposition(event);
  };

  const stopDrawing = () => {
    canvas.current.removeEventListener('mousemove', move);
  };

  const saveCanvas = () => {
    localStorage.setItem('drawData', JSON.stringify(drawInstance.getInfo()));
  };

  const convertDataToCanvasImageData = (data) => {
    const uintArray = new Uint8ClampedArray(JSON.parse(JSON.parse(data).data));
    return new ImageData(uintArray, 300, 150);
  };

  const createDrawing = useCallback(() => {
    const { ctx } = createCtx(canvas.current);
    const draw = new Draw(canvas.current, ctx);
    setDrawInstance(draw);

    return ctx;
  }, [canvas]);

  useEffect(() => {
    const ctx = createDrawing();
    if (localStorage.getItem('drawData')) {
      const imageData = convertDataToCanvasImageData(
        localStorage.getItem('drawData'),
      );
      ctx.putImageData(imageData, 0, 0);
    }
  }, [canvas, contentsLoaded, createDrawing]);

  return { saveCanvas, createDrawing, startDrawing, stopDrawing };
};

export default useDrawing;
