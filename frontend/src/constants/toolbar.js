import {
  CursorIcon,
  DrawIcon,
  // StickerIcon,
  TextIcon,
  AddPageIcon,
  DownloadIcon,
} from '../assets/svg';
import { DECORATE_TYPE } from './decorateComponent';

export const DECORATE_TOOLS = [
  {
    icon: (props) => <CursorIcon {...props} />,
    type: DECORATE_TYPE.MOVING,
  },
  { icon: (props) => <DrawIcon {...props} />, type: DECORATE_TYPE.DRAWING },
  { icon: (props) => <TextIcon {...props} />, type: DECORATE_TYPE.TEXT_BOX },
  // {
  //   icon: (props) => <StickerIcon {...props} />,
  //   type: DECORATE_TYPE.STICKER,
  // },
];

export const PAGE_TOOLS = [
  {
    icon: (props) => <AddPageIcon {...props} />,
    type: 'add',
  },
  {
    icon: (props) => <DownloadIcon {...props} />,
    type: 'download',
  },
  {
    icon: (props) => <DownloadIcon {...props} />,
    type: 'save',
  },
];
