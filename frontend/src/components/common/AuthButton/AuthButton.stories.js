export default {};
import AuthButton from './AuthButton';

export default {
  title: 'Common/AuthButton',
  component: AuthButton,
  argTypes: {
    size: { control: '' },
    disabled: { control: ''},
    children: { control: 'text'},
    onClick: { control: ''},
  }
};

export const Primary =
