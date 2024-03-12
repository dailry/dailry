export const commonDecorateComponentProperties = {
  id: '',
  order: null,
  size: {
    width: 200,
    height: 150,
  },
  initialStyle: {
    position: { x: 0, y: 0 },
    size: {
      width: 200,
      height: 150,
    },
    rotation: '0',
  },
  position: {
    x: 0,
    y: 0,
  },
  rotation: '0',
};

export const typedDecorateComponentProperties = {
  textBox: {
    typeContent: {
      font: '굴림',
      fontSize: 12,
      text: null,
      fontWeight: 'bold',
      backgroundColor: '#ffcc00',
      color: '#333333',
    },
  },

  drawing: {
    typeContent: {
      base64: null,
    },
  },

  sticker: {
    typeContent: {
      imageUrl: null,
    },
  },
};
