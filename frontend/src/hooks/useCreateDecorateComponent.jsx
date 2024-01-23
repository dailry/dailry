import { useEffect, useState } from 'react';

const mockData = {
  key: new Date().toISOString(),
  type: null,
  order: 1,
  position: {
    x: null,
    y: null,
  },
  rotation: '90deg',
  properties: {
    font: '굴림',
    fontSize: 12,
    text: '아 어지럽다.',
    fontWeight: 'bold',
    backgroundColor: '#ffcc00',
    width: 'auto',
    height: 'auto',
    color: '#333333',
  },
};

const useCreateDecorateComponent = (addToArray, parentRef) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(undefined);
  const parentX = parentRef?.current?.getBoundingClientRect().left.toFixed();
  const parentY = parentRef?.current?.getBoundingClientRect().top.toFixed();

  const createNewDecorateComponent = (type) => {
    setNewDecorateComponent(() => ({ ...mockData, type }));
  };

  const getNewDecorateComponentPosition = (e) => {
    const { clientX, clientY } = e;
    return { x: clientX - parentX, y: clientY - parentY };
  };

  const setNewDecorateComponentPosition = (e) => {
    if (!newDecorateComponent) return;

    setNewDecorateComponent((prev) => ({
      ...prev,
      position: {
        ...prev.position,
        ...getNewDecorateComponentPosition(e),
      },
    }));
  };

  useEffect(() => {
    if (Number.isSafeInteger(newDecorateComponent?.position?.x)) {
      addToArray((prev) => prev.concat(newDecorateComponent));
      setNewDecorateComponent(undefined);
    }
  }, [newDecorateComponent?.position?.x]);

  return { createNewDecorateComponent, setNewDecorateComponentPosition };
};

export default useCreateDecorateComponent;
