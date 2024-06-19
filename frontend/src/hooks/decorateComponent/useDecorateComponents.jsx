import { useEffect, useReducer } from 'react';
import { useDailryContext } from '../useDailryContext';
import { getPage } from '../../apis/dailryApi';

const decorateComponentReducer = (decorateComponents, action) => {
  switch (action.type) {
    case 'setAll': {
      return [...decorateComponents].concat(action.decorateComponents);
    }
    case 'addNew': {
      return [...decorateComponents, action.newDecorateComponent];
    }
    case 'modify': {
      const toModifyDecorateComponent = decorateComponents.find(
        (d) => d.id === action.toModifyDecorateComponent.id,
      );
      return [
        ...decorateComponents,
        toModifyDecorateComponent ?? action.toModifyDecorateComponent,
      ];
    }
    case 'delete': {
      return [...decorateComponents].filter((d) => d.id !== action.id);
    }
    default: {
      throw Error(`Unknown action:${action.type}`);
    }
  }
};

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
  };
};

export default useDecorateComponents;
