import { useEffect, useState } from 'react';
import { DECORATE_TYPE } from '../constants/decorateComponent';

const useSetTypeContent = (
  selectedTool,
  newDecorateComponent,
  setNewDecorateComponentTypeContent,
  setCanEditDecorateComponentTypeContent,
) => {
  const [newTypeContent, setNewTypeContent] = useState(undefined);
  useEffect(() => {
    if (selectedTool === DECORATE_TYPE.MOVING) {
      return;
    }
    if (newDecorateComponent && newTypeContent) {
      setNewDecorateComponentTypeContent(newTypeContent);

      return;
    }

    setCanEditDecorateComponentTypeContent(newTypeContent);
  }, [newTypeContent]);

  return { setNewTypeContent };
};

export default useSetTypeContent;
