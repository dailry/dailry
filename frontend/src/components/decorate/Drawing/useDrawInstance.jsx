import { useCallback, useEffect, useState } from 'react';
import Draw from './Draw';
import { createCtx } from './canvas';

const useDrawInstance = (canvas) => {
  const [contentsLoaded, setContentsLoaded] = useState(false);
  document.addEventListener('DOMContentLoaded', () => {
    setContentsLoaded(true);
  });

  const [drawInstance, setDrawInstance] = useState(undefined);

  const createDrawing = useCallback(() => {
    const { ctx } = createCtx(canvas.current);
    setDrawInstance(new Draw(canvas.current, ctx));

    return ctx;
  }, [canvas]);

  const convertDataToCanvasImageData = (data) => {
    const uintArray = new Uint8ClampedArray(JSON.parse(JSON.parse(data).data));
    return new ImageData(uintArray, 300, 150);
  };

  useEffect(() => {
    const ctx = createDrawing();
    if (localStorage.getItem('drawData')) {
      const imageData = convertDataToCanvasImageData(
        localStorage.getItem('drawData'),
      );
      ctx.putImageData(imageData, 0, 0);
    }
  }, [canvas, contentsLoaded, createDrawing]);

  const draw = (e) => drawInstance.move(e);
  const erase = (e) => drawInstance.erase(e);

  return { drawInstance, draw, erase };
};

export default useDrawInstance;
