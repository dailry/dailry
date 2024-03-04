import { useEffect, useState } from 'react';
import { EDIT_MODE } from '../constants/decorateComponent';

const useEditDecorateComponent = (
  modifyDecorateComponent,
  modifyUpdatedDecorateComponent,
) => {
  const [canEditDecorateComponent, setCanEditDecorateComponent] =
    useState(null);

  const [editMode, setEditMode] = useState('');

  const isTypeContentEdited =
    editMode === EDIT_MODE.TYPE_CONTENT &&
    canEditDecorateComponent?.typeContent &&
    Object.values(canEditDecorateComponent?.typeContent).every(
      (v) => v !== null,
    );

  const setCanEditDecorateComponentTypeContent = (newTypeContent) => {
    setCanEditDecorateComponent((prev) => ({
      ...prev,
      typeContent: newTypeContent,
    }));
  };

  const setCanEditDecorateComponentCommonProperty = (commonProperty) => {
    const { position, rotation, size } = commonProperty;

    setCanEditDecorateComponent((prev) => ({
      ...prev,
      position: position
        ? {
            x: position.x + prev.position.x,
            y: position.y + prev.position.y,
          }
        : prev.position,
      rotation: rotation ?? prev.rotation,
      size: size ?? prev.size,
    }));
  };

  const completeModifyDecorateComponent = () => {
    modifyDecorateComponent(canEditDecorateComponent);
    modifyUpdatedDecorateComponent(canEditDecorateComponent);
  };

  useEffect(() => {
    if (!canEditDecorateComponent) {
      return;
    }

    if (isTypeContentEdited) {
      completeModifyDecorateComponent();
    }
  }, [canEditDecorateComponent]);

  return {
    editMode,
    setEditMode,
    canEditDecorateComponent,
    setCanEditDecorateComponent,
    setCanEditDecorateComponentTypeContent,
    setCanEditDecorateComponentCommonProperty,
    completeModifyDecorateComponent,
  };
};

export default useEditDecorateComponent;
