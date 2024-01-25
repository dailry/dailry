import { CursorIcon, DrawIcon, StickerIcon, TextIcon } from '../assets/svg';
import { DECORATE_TYPE } from './decorateComponent';

export const TOOLS = [
  {
    icon: (props) => <CursorIcon {...props} />,
    type: DECORATE_TYPE.MOVING,
  },
  { icon: (props) => <DrawIcon {...props} />, type: DECORATE_TYPE.DRAWING },
  { icon: (props) => <TextIcon {...props} />, type: DECORATE_TYPE.TEXT_BOX },
  {
    icon: (props) => <StickerIcon {...props} />,
    type: DECORATE_TYPE.STICKER,
  },
];
