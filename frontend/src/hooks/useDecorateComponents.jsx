import { useState } from 'react';

const useDecorateComponents = () => {
  const [decorateComponents, setDecorateComponents] = useState([]);

  const addNewDecorateComponent = (newDecorateComponent) => {
    setDecorateComponents((prev) => prev.concat(newDecorateComponent));
  };

  const modifyDecorateComponentTypeContent = (modifiedDecorateComponent) => {
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
    addNewDecorateComponent,
    modifyDecorateComponentTypeContent,
  };
};

export default useDecorateComponents;
