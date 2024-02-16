import { useState } from 'react';
import { typedDecorateComponentProperties } from './properties';
import { getCommonDecorateComponentProperties } from './createNewDecorateComponent';

const useNewDecorateComponent = (decorateComponents, pageRef) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(null);

  const removeNewDecorateComponent = () => {
    setNewDecorateComponent(null);
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
    removeNewDecorateComponent,
    setNewDecorateComponentTypeContent,
  };
};

export default useNewDecorateComponent;
