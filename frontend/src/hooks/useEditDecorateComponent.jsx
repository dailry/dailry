import { useEffect, useState } from 'react';

const useEditDecorateComponent = (newDecorateComponent) => {
  const [canEditDecorateComponentId, setCanEditDecorateComponentId] =
    useState(null);
  useEffect(() => {
    setCanEditDecorateComponentId(
      newDecorateComponent ? newDecorateComponent.id : null,
    );
  }, [newDecorateComponent]);

  return { canEditDecorateComponentId };
};

export default useEditDecorateComponent;
