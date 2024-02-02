import { useCallback, useEffect, useState } from 'react';
import Draw from './Draw';
import { createCtx } from './canvas';
import useDomContentsLoaded from '../../../hooks/useDomContentsLoaded';

const useDrawInstance = (canvas, typeContent) => {
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

    if (typeContent) {
      img.src = typeContent.base64;
      console.log(img.src);

      img.onload = () => {
        if (ctx) {
          ctx.globalCompositeOperation = 'source-over';
          ctx.drawImage(img, 0, 0, canvas.current.width, canvas.current.height);
        }
      };
    }
  }, [contentsLoaded, initialize, typeContent]);

  return { drawInstance };
};

export default useDrawInstance;
