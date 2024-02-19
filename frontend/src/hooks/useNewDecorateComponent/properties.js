export const commonDecorateComponentProperties = {
  id: '',
  order: null,
  size: {
    width: 'auto',
    height: 'auto',
  },
  position: {
    x: null,
    y: null,
  },
  rotation: '0deg',
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
