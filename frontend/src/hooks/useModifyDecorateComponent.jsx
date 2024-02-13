import { useState } from 'react';

const useModifyDecorateComponent = (setDecorateComponents) => {
  const [canEditDecorateComponent, setCanEditDecorateComponent] =
    useState(null);
  const modifyDecorateComponentTypeContent = (newTypeContent) => {
    setDecorateComponents((prev) =>
      prev.map((decorateComponent) => {
        return decorateComponent.id === canEditDecorateComponent.id
          ? { ...decorateComponent, typeContent: newTypeContent }
          : decorateComponent;
      }),
    );
  };

  return {
    canEditDecorateComponent,
    setCanEditDecorateComponent,
    modifyDecorateComponentTypeContent,
  };
};

export default useModifyDecorateComponent;
