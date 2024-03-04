import Drawing from '../components/decorate/Drawing/Drawing';
import TextBox from '../components/decorate/TextBox/TextBox';

export const DECORATE_TYPE = {
  MOVING: 'moving',
  DRAWING: 'drawing',
  TEXT_BOX: 'textBox',
  STICKER: 'sticker',
};

export const DECORATE_COMPONENT = {
  [DECORATE_TYPE.DRAWING]: Drawing,
  [DECORATE_TYPE.TEXT_BOX]: TextBox,
};

export const EDIT_MODE = {
  TYPE_CONTENT: 'typeContent',
  COMMON_PROPERTY: 'commonProperty',
};
