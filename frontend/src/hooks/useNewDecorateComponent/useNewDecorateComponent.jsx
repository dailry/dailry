import { useState } from 'react';
import { typedDecorateComponentProperties } from './properties';
import { getCommonDecorateComponentProperties } from './createNewDecorateComponent';

const useNewDecorateComponent = (
  decorateComponents,
  setDecorateComponents,
  pageRef,
) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(undefined);

  const initializeNewDecorateComponent = () => {
    setNewDecorateComponent(undefined);
  };

  const createNewDecorateComponent = (e, type) => {
    setNewDecorateComponent({
      type,
      order: decorateComponents.length,
      ...getCommonDecorateComponentProperties(e, pageRef),
      ...typedDecorateComponentProperties[type],
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
