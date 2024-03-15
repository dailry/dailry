import { useState } from 'react';

const useUpdatedDecorateComponents = () => {
  const [updatedDecorateComponents, setUpdatedDecorateComponents] = useState(
    [],
  );

  const addUpdatedDecorateComponent = (updatedDecorateComponent) => {
    setUpdatedDecorateComponents((prev) => [...prev, updatedDecorateComponent]);
  };

  const modifyUpdatedDecorateComponent = (updatedDecorateComponent) => {
    if (
      !updatedDecorateComponents.some(
        (id) => id === updatedDecorateComponent.id,
      )
    ) {
      addUpdatedDecorateComponent(updatedDecorateComponent);
      return;
    }
    setUpdatedDecorateComponents((prev) =>
      prev.map((decorateComponent) => {
        return decorateComponent.id === updatedDecorateComponent.id
          ? updatedDecorateComponent
          : decorateComponent;
      }),
    );
  };

  return {
    setUpdatedDecorateComponents,
    updatedDecorateComponents,
    addUpdatedDecorateComponent,
    modifyUpdatedDecorateComponent,
  };
};

export default useUpdatedDecorateComponents;
