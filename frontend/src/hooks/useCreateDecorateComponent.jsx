import { useEffect, useState } from 'react';
import { DECORATE_TYPE } from '../constants/decorateComponent';

const commonDecorateComponentProperties = {
  size: {
    width: 300,
    height: 150,
  },
  position: {
    x: null,
    y: null,
  },
  rotation: null,
};

const initialDecorateComponentProperties = {
  textBox: {
    properties: {
      font: '굴림',
      fontSize: 12,
      text: '',
      fontWeight: 'bold',
      backgroundColor: '#ffcc00',
      color: '#333333',
    },
  },

  drawing: {
    properties: {
      base64: '[255, 255, 255, 255, 255]',
    },
  },

  sticker: {
    properties: {
      imageUrl:
        'https://trboard.game.onstove.com/Data/TR/20180111/13/636512725318200105.jpg',
    },
  },
};

const useCreateDecorateComponent = (
  decorateComponents,
  addToArray,
  parentRef,
) => {
  const [newDecorateComponent, setNewDecorateComponent] = useState(undefined);
  const parentX = parentRef?.current?.getBoundingClientRect().left.toFixed();
  const parentY = parentRef?.current?.getBoundingClientRect().top.toFixed();

  const getNewDecorateComponentProperties = (e, type) => {
    const { clientX, clientY } = e;

    return {
      ...initialDecorateComponentProperties[type],
      ...{
        id: new Date().toISOString(),
        type,
        order: decorateComponents.length,
        position: { x: clientX - parentX, y: clientY - parentY },
      },
    };
  };

  const createNewDecorateComponent = (e, type) => {
    if (type === null || type === DECORATE_TYPE.MOVING) return;
    setNewDecorateComponent(() => ({
      ...commonDecorateComponentProperties,
      ...getNewDecorateComponentProperties(e, type),
    }));
  };

  useEffect(() => {
    if (Number.isSafeInteger(newDecorateComponent?.position?.x)) {
      addToArray((prev) => prev.concat(newDecorateComponent));
      setNewDecorateComponent(undefined);
    }
  }, [newDecorateComponent?.position?.x]);

  return { createNewDecorateComponent };
};

export default useCreateDecorateComponent;
