const useCompleteCreation = (
  newDecorateComponent,
  addNewDecorateComponent,
  removeNewDecorateComponent,
  addUpdatedDecorateComponent,
) => {
  const isCreationCompleted =
    newDecorateComponent?.typeContent &&
    Object.values(newDecorateComponent?.typeContent).every((v) => v !== null);

  const completeCreateNewDecorateComponent = () => {
    if (isCreationCompleted) {
      addNewDecorateComponent(newDecorateComponent);
      addUpdatedDecorateComponent(newDecorateComponent);
    }
    removeNewDecorateComponent();
  };

  return { completeCreateNewDecorateComponent };
};

export default useCompleteCreation;
