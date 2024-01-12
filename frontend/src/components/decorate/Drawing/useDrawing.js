import { useCallback, useEffect, useState } from 'react';
import { createCanvasElement } from './canvas';
import Draw from './Draw';

const useDrawing = (id) => {
  const [contentsLoaded, setContentsLoaded] = useState(false);
  const [drawInstance, setDrawInstance] = useState(undefined);

  document.addEventListener('DOMContentLoaded', () => {
    setContentsLoaded(true);
  });

  const attachDrawEvent = useCallback(() => {
    const drawMove = (e) => drawInstance.move(e);

    const start = (event) => {
      document.addEventListener('mousemove', drawMove);

      drawInstance.reposition(event);
    };

    const stop = () => {
      document.removeEventListener('mousemove', drawMove);
    };

    document.addEventListener('mousedown', start);
    document.addEventListener('mouseup', stop);
  }, [drawInstance]);

  const saveDrawData = () => {
    drawInstance.getInfo();
  };

  const loadCanvas = () => {
    const { canvas, ctx } = createCanvasElement('loaded-canvas');
    const uintArray = new Uint8ClampedArray(
      JSON.parse(drawInstance.getInfo().data),
    );
    const newImageData = new ImageData(uintArray, 300, 150);
    console.log('canvas 데이터 로드', newImageData);
    ctx.putImageData(newImageData, 0, 0);
    const loadedDrawInstance = new Draw(canvas, ctx);

    setDrawInstance(loadedDrawInstance);
  };

  useEffect(() => {
    const { canvas, ctx } = createCanvasElement(id);
    const draw = new Draw(canvas, ctx);

    setDrawInstance(draw);
  }, [id, contentsLoaded]);

  useEffect(() => {
    if (drawInstance) attachDrawEvent();
  }, [drawInstance, attachDrawEvent]);

  return { saveDrawData, loadCanvas };
};

export default useDrawing;
