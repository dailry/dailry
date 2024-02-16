import { useState } from 'react';

const useUpdatedDecorateComponents = () => {
  const [updatedDecorateComponents, setUpdatedDecorateComponents] = useState(
    [],
  );

  const addUpdatedDecorateComponent = (updatedDecorateComponent) => {
    setUpdatedDecorateComponents((prev) => [...prev, updatedDecorateComponent]);
  };

  const modifyUpdatedDecorateComponent = (updatedDecorateComponent) => {
    setUpdatedDecorateComponents((prev) =>
      prev.map((decorateComponent) => {
        return decorateComponent.id === updatedDecorateComponent.id
          ? updatedDecorateComponent
          : decorateComponent;
      }),
    );
  };

  return {
    updatedDecorateComponents,
    addUpdatedDecorateComponent,
    modifyUpdatedDecorateComponent,
  };
};

export default useUpdatedDecorateComponents;
