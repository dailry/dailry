import { useCallback, useEffect, useState } from 'react';
import Draw from './Draw';
import { createCtx } from './canvas';
import useDomContentsLoaded from '../../../hooks/useDomContentsLoaded';

const useDrawInstance = (canvas) => {
  const contentsLoaded = useDomContentsLoaded();
  const [drawInstance, setDrawInstance] = useState(undefined);

  const initialize = useCallback(() => {
    const { ctx } = createCtx(canvas.current);
    setDrawInstance(new Draw(canvas.current, ctx));

    return ctx;
  }, [canvas]);

  useEffect(() => {
    const ctx = initialize();
    const img = new Image();
    if (localStorage.getItem('canvasImageUrl')) {
      img.src = localStorage.getItem('canvasImageUrl');

      img.onload = () => {
        if (ctx) {
          ctx.globalCompositeOperation = 'source-over';
          ctx.drawImage(img, 0, 0, canvas.current.width, canvas.current.height);
        }
      };
    }
  }, [contentsLoaded, initialize]);

  return { drawInstance };
};

export default useDrawInstance;
