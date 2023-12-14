import AuthButton from './AuthButton';

export default {
  title: 'Common/AuthButton',
  component: AuthButton,
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
};

export const Login = {
  args: {
    children: '로그인',
  },
};

export const DuplicationCheck = {
  args: {
    children: '중복확인',
    size: 'sm',
  },
};

export const CheckDone = {
  args: {
    children: '확인완료',
    size: 'sm',
    disabled: true,
  },
};
