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

const useCreateDecorateComponent = (addToArray) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(undefined);

  const createNewDecorateComponent = (type) => {
    setNewDecorateComponent(() => ({ ...mockData, type }));
  };

  const setNewDecorateComponentPosition = (e) => {
    if (!newDecorateComponent) return;

    const { clientX, clientY } = e;
    setNewDecorateComponent((prev) => ({
      ...prev,
      position: { ...prev.position, x: clientX, y: clientY },
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
