import { useState } from 'react';
import { typedDecorateComponentProperties } from './properties';
import { getCommonDecorateComponentProperties } from './createNewDecorateComponent';

const useNewDecorateComponent = (decorateComponents, pageRef) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(undefined);

  const isEdited =
    newDecorateComponent &&
    Object.values(newDecorateComponent).every((v) => v !== null);

  const createNewDecorateComponent = (e, type) => {
    setNewDecorateComponent({
      type,
      order: decorateComponents.length,
      ...getCommonDecorateComponentProperties(e, pageRef),
      ...typedDecorateComponentProperties[type],
    });
  };
  return {
    isEdited,
    createNewDecorateComponent,
    newDecorateComponent,
    setNewDecorateComponent,
  };
};

export default useNewDecorateComponent;
