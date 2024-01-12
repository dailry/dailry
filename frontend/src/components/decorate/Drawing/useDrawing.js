import { useEffect, useState } from 'react';
import { createCanvasElement } from './canvas';
import Draw from './Draw';

const useDrawing = (id) => {
  const [contentsLoaded, setContentsLoaded] = useState(false);
  const [drawInstance, setDrawInstance] = useState(undefined);
  const el = document.getElementById(id);

  document.addEventListener('DOMContentLoaded', () => {
    setContentsLoaded(true);
  });

  const move = (e) => drawInstance.move(e);

  const startDrawing = (event) => {
    el.addEventListener('mousemove', move);
    drawInstance.reposition(event);
  };

  const stopDrawing = () => {
    el.removeEventListener('mousemove', move);
  };

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

  return { saveDrawData, loadCanvas, startDrawing, stopDrawing };
};

export default useDrawing;
