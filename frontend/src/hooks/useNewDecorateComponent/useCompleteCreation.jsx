import { useEffect, useState } from 'react';

const useCompleteCreation = (
  newDecorateComponent,
  addNewDecorateComponent,
  initializeNewDecorateComponent,
) => {
  const [isOtherActionTriggered, setIsOtherActionTriggered] = useState(false);
  const isCreationCompleted =
    newDecorateComponent?.typeContent &&
    Object.values(newDecorateComponent?.typeContent).every((v) => v !== null);

  useEffect(() => {
    if (isCreationCompleted) {
      addNewDecorateComponent(newDecorateComponent);
    }
    initializeNewDecorateComponent();
  }, [isOtherActionTriggered]);

  return { setIsOtherActionTriggered };
};

export default useCompleteCreation;
