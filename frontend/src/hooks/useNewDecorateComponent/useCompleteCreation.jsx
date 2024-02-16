import { useEffect, useState } from 'react';

const useCompleteCreation = (
  newDecorateComponent,
  addNewDecorateComponent,
  removeNewDecorateComponent,
  addUpdatedDecorateComponent,
) => {
  const [isOtherActionTriggered, setIsOtherActionTriggered] = useState(false);
  const isCreationCompleted =
    newDecorateComponent?.typeContent &&
    Object.values(newDecorateComponent?.typeContent).every((v) => v !== null);

  useEffect(() => {
    if (isCreationCompleted) {
      addNewDecorateComponent(newDecorateComponent);
      addUpdatedDecorateComponent(newDecorateComponent);
    }
    removeNewDecorateComponent();
  }, [isOtherActionTriggered]);

  return { setIsOtherActionTriggered };
};

export default useCompleteCreation;
