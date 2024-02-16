import { useState } from 'react';
import { typedDecorateComponentProperties } from './properties';
import { getCommonDecorateComponentProperties } from './createNewDecorateComponent';

const useNewDecorateComponent = (decorateComponents, pageRef) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(undefined);

  const initializeNewDecorateComponent = () => {
    setNewDecorateComponent(undefined);
  };

  const createNewDecorateComponent = (e, type) => {
    setNewDecorateComponent({
      ...getCommonDecorateComponentProperties(e, pageRef),
      ...typedDecorateComponentProperties[type],
      type,
      order: decorateComponents.length,
    });
  };

  const setNewDecorateComponentTypeContent = (newTypeContent) => {
    setNewDecorateComponent((prev) => ({
      ...prev,
      typeContent: newTypeContent,
    }));
  };

  return {
    createNewDecorateComponent,
    newDecorateComponent,
    initializeNewDecorateComponent,
    setNewDecorateComponentTypeContent,
  };
};

export default useNewDecorateComponent;
