import { useEffect, useState } from 'react';
import Draw from './Draw';
import { createCtx } from './canvas';
import useDomContentsLoaded from '../../../hooks/useDomContentsLoaded';

const useDrawInstance = (canvas, typeContent) => {
  const contentsLoaded = useDomContentsLoaded();
  const [drawInstance, setDrawInstance] = useState(undefined);

  useEffect(() => {
    if (canvas.current && typeContent?.base64) {
      const { ctx } = createCtx(canvas.current);

      const img = new Image();
      img.src = typeContent.base64;

      img.onload = () => {
        if (ctx && canvas.current) {
          ctx.globalCompositeOperation = 'source-over';
          ctx.drawImage(
            img,
            0,
            0,
            canvas.current?.width,
            canvas.current?.height,
          );
        }
      };
      setDrawInstance(new Draw(canvas.current, ctx));
    }
  }, [contentsLoaded, canvas, typeContent?.base64]);

  return { drawInstance };
};

export default useDrawInstance;
