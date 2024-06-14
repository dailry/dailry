import { useEffect, useState } from 'react';
import { useDailryContext } from './useDailryContext';
import { getPage } from '../apis/dailryApi';

const useDecorateComponents = () => {
  const [decorateComponents, setDecorateComponents] = useState([]);
  const { currentDailry } = useDailryContext();
  const { dailryId, pageIds, pageNumber } = currentDailry;

  useEffect(() => {
    (async () => {
      if (dailryId) {
        setDecorateComponents([]);
        const page = await getPage(pageIds[pageNumber - 1]);

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
          setDecorateComponents(datas);
        }
      }
    })();
  }, [pageIds, pageNumber]);

  const addNewDecorateComponent = (newDecorateComponent) => {
    setDecorateComponents((prev) => prev.concat(newDecorateComponent));
  };

  const modifyDecorateComponent = (modifiedDecorateComponent) => {
    setDecorateComponents((prev) =>
      prev.map((decorateComponent) => {
        return decorateComponent.id === modifiedDecorateComponent.id
          ? modifiedDecorateComponent
          : decorateComponent;
      }),
    );
  };

  return {
    decorateComponents,
    setDecorateComponents,
    addNewDecorateComponent,
    modifyDecorateComponent,
  };
};

export default useDecorateComponents;
