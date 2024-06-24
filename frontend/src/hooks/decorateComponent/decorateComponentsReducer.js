export const decorateComponentReducer = (decorateComponents, action) => {
  switch (action.type) {
    case 'setAll': {
      return [...decorateComponents].concat(action.decorateComponents);
    }
    case 'addNew': {
      const { newDecorateComponent, isUpdated } = action;
      return [
        ...decorateComponents,
        {
          ...newDecorateComponent,
          isUpdated,
        },
      ];
    }
    case 'modify': {
      const toModifyDecorateComponent = decorateComponents.find(
        (d) => d.id === action.toModifyDecorateComponent.id,
      );
      return [
        ...decorateComponents,
        toModifyDecorateComponent ?? action.toModifyDecorateComponent,
      ];
    }
    case 'delete': {
      return [...decorateComponents].filter((d) => d.id !== action.id);
    }
    case 'setUpdateIsDone': {
      return [...decorateComponents].map((component) => {
        if (component.isUpdated) {
          return { ...component, isUpdated: false };
        }
        return { ...component };
      });
    }
    default: {
      throw Error(`Unknown action:${action.type}`);
    }
  }
};
