import { useEffect, useState } from 'react';

const useModifyDecorateComponent = (modifyDecorateComponentTypeContent) => {
  const [canEditDecorateComponent, setCanEditDecorateComponent] =
    useState(null);

  const setCanEditDecorateComponentTypeContent = (newTypeContent) => {
    setCanEditDecorateComponent((prev) => ({
      ...prev,
      typeContent: newTypeContent,
    }));
  };

  useEffect(() => {
    if (
      canEditDecorateComponent?.typeContent &&
      Object.values(canEditDecorateComponent?.typeContent).every(
        (v) => v !== null,
      )
    ) {
      modifyDecorateComponentTypeContent(canEditDecorateComponent);
    }
  }, [canEditDecorateComponent?.typeContent]);

  return {
    canEditDecorateComponent,
    setCanEditDecorateComponent,
    setCanEditDecorateComponentTypeContent,
  };
};

export default useModifyDecorateComponent;
