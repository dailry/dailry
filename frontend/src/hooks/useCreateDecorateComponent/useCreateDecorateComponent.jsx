import { useState } from 'react';
import { DECORATE_TYPE } from '../../constants/decorateComponent';
import {
  commonDecorateComponentProperties,
  typedDecorateComponentProperties,
} from './properties';

const useCreateNewDecorateComponent = ({
  decorateComponents,
  parentRef,
  setCanEditDecorateComponentId,
}) => {
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

    const newCommonDecorateComponentProperties =
      getCommonDecorateComponentProperties(e);

    setNewDecorateComponent(() => ({
      type,
      ...newCommonDecorateComponentProperties,
      ...typedDecorateComponentProperties[type],
    }));

    setCanEditDecorateComponentId(newCommonDecorateComponentProperties.id);
  };

  return {
    createNewDecorateComponent,
    newDecorateComponent,
    setNewDecorateComponent,
  };
};

export default useCreateNewDecorateComponent;
