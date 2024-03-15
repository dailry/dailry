import Input from './Input';

export default {
  title: 'Common/Input',
  component: Input,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
};

export const Password = {
  args: {
    placeholder: '비밀번호',
  },
};

export const Disabled = {
  args: {
    value: 'puranium235',
    disabled: true,
  },
};
