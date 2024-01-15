import { useCallback, useEffect, useState } from 'react';
import Draw from './Draw';
import { createCtx } from './canvas';
import { convertDataToCanvasImageData } from '../../../utils/dataFormatting';
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
    if (localStorage.getItem('drawData')) {
      const imageData = convertDataToCanvasImageData(
        localStorage.getItem('drawData'),
      );
      ctx.putImageData(imageData, 0, 0);
    }
  }, [contentsLoaded, initialize]);

  return { drawInstance };
};

export default useDrawInstance;
