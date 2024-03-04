import { useState } from 'react';

const useDecorateComponents = () => {
  const [decorateComponents, setDecorateComponents] = useState([]);

  const addNewDecorateComponent = (newDecorateComponent) => {
    setDecorateComponents((prev) => prev.concat(newDecorateComponent));
  };

  const modifyDecorateComponent = (modifiedDecorateComponent) => {
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
    modifyDecorateComponent,
  };
};

export default useDecorateComponents;
