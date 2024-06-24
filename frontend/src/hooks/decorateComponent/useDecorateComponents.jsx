import { useEffect, useReducer } from 'react';
import { useDailryContext } from '../useDailryContext';
import { getPage } from '../../apis/dailryApi';
import { decorateComponentReducer } from './decorateComponentsReducer';

const useDecorateComponents = () => {
  const { currentDailry, currentDailryPage } = useDailryContext();
  const { id } = currentDailry;
  const [decorateComponents, dispatchDecorateComponents] = useReducer(
    decorateComponentReducer,
    [],
  );

  useEffect(() => {
    console.log(decorateComponents);
  }, [decorateComponents]);

  const getUpdatedDecorateComponents = () => {
    const filteredDecorateComponents = decorateComponents.filter(
      (component) => component.isUpdated === true,
    );
    return filteredDecorateComponents;
  };

  useEffect(() => {
    (async () => {
      if (id) {
        dispatchDecorateComponents({
          type: 'setAll',
          decorateComponents: [],
        });
        const page = await getPage(currentDailryPage.pageId);

        if (page.data?.elements.length > 0) {
          const datas = page.data?.elements.map((i) => ({
            ...i,
            initialStyle: {
              ...i.initialStyle,
              position: i?.position,
              size: i?.size,
              rotation: i?.rotation,
            },
          }));

          dispatchDecorateComponents({
            type: 'setAll',
            decorateComponents: datas,
          });
        }
      }
    })();
  }, [currentDailryPage]);

  return {
    decorateComponents,
    dispatchDecorateComponents,
    getUpdatedDecorateComponents,
  };
};

export default useDecorateComponents;
