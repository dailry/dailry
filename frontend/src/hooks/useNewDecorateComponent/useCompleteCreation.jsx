import { useEffect, useState } from 'react';

const useCompleteCreation = (
  newDecorateComponent,
  setDecorateComponents,
  initializeNewDecorateComponent,
) => {
  const [isOtherActionTriggered, setIsOtherActionTriggered] = useState(false);
  const isCreationCompleted =
    newDecorateComponent?.typeContent &&
    Object.values(newDecorateComponent?.typeContent).every((v) => v !== null);

  useEffect(() => {
    if (isCreationCompleted) {
      setDecorateComponents((prev) => prev.concat(newDecorateComponent));
    }
    initializeNewDecorateComponent();
  }, [isOtherActionTriggered]);

  return { setIsOtherActionTriggered };
};

export default useCompleteCreation;
