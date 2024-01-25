export const commonDecorateComponentProperties = {
  id: '',
  order: null,
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

export const typedDecorateComponentProperties = {
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
