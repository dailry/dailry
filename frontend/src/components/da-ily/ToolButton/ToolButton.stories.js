import ToolButton from './ToolButton';
import { CursorIcon } from '../../../assets/svg';

export default {
  title: 'Dailry/ToolButton',
  component: ToolButton,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
};

export const Cursor = {
  args: {
    tool: { icon: (props) => <CursorIcon {...props} /> },
    selected: false,
  },
};
