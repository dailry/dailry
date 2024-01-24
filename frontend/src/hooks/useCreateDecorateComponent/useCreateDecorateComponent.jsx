import { useEffect, useState } from 'react';
import { DECORATE_TYPE } from '../../constants/decorateComponent';
import {
  commonDecorateComponentProperties,
  typedDecorateComponentProperties,
} from './properties';

const useCreateDecorateComponent = (
  decorateComponents,
  addToArray,
  parentRef,
) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(undefined);
  const parentX = parentRef?.current?.getBoundingClientRect().left.toFixed();
  const parentY = parentRef?.current?.getBoundingClientRect().top.toFixed();

  const getCommonDecorateComponentProperties = (e) => {
    const { clientX, clientY } = e;

    return {
      ...commonDecorateComponentProperties,
      id: new Date().toISOString(),
      position: { x: clientX - parentX, y: clientY - parentY },
      order: decorateComponents.length,
    };
  };

  const createNewDecorateComponent = (e, type) => {
    if (type === null || type === DECORATE_TYPE.MOVING) return;
    setNewDecorateComponent(() => ({
      type,
      ...getCommonDecorateComponentProperties(e),
      ...typedDecorateComponentProperties[type],
    }));
  };

  useEffect(() => {
    if (Number.isSafeInteger(newDecorateComponent?.position?.x)) {
      addToArray((prev) => prev.concat(newDecorateComponent));
      setNewDecorateComponent(undefined);
    }
  }, [newDecorateComponent?.position?.x]);

  return { createNewDecorateComponent };
};

export default useCreateDecorateComponent;
