import Drawing from '../components/decorate/Drawing/Drawing';

export const DECORATE_TYPE = {
  MOVING: 'moving',
  DRAWING: 'drawing',
  TEXT_BOX: 'textBox',
  STICKER: 'sticker',
};

export const DECORATE_COMPONENT = {
  [DECORATE_TYPE.DRAWING]: Drawing,
};
