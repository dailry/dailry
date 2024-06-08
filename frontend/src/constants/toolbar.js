import {
  CursorIcon,
  DrawIcon,
  // StickerIcon,
  TextIcon,
  AddPageIcon,
  DownloadIcon,
  SaveDailryPageIcon,
  CommunityShareIcon,
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

const PAGE_TOOL_TYPE = {
  ADD: 'add',
  DOWNLOAD: 'download',
  SAVE: 'save',
  SHARE: 'share',
};

export const DECORATE_TOOLS_TOOLTIP = {
  [DECORATE_TYPE.MOVING]: '이동',
  [DECORATE_TYPE.DRAWING]: '그리기',
  [DECORATE_TYPE.TEXT_BOX]: '텍스트',
};

export const PAGE_TOOLS_TOOLTIP = {
  [PAGE_TOOL_TYPE.ADD]: '페이지 추가',
  [PAGE_TOOL_TYPE.DOWNLOAD]: '페이지 내보내기',
  [PAGE_TOOL_TYPE.SAVE]: '페이지 저장',
  [PAGE_TOOL_TYPE.SHARE]: '공유하기',
};

export const PAGE_TOOLS = [
  {
    icon: (props) => <AddPageIcon {...props} />,
    type: PAGE_TOOL_TYPE.ADD,
  },
  {
    icon: (props) => <SaveDailryPageIcon {...props} />,
    type: PAGE_TOOL_TYPE.SAVE,
  },
  {
    icon: (props) => <CommunityShareIcon {...props} />,
    type: PAGE_TOOL_TYPE.SHARE,
  },
  {
    icon: (props) => <DownloadIcon {...props} />,
    type: PAGE_TOOL_TYPE.DOWNLOAD,
  },
];
