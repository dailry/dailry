import Text from './Text';
import { TEXT } from '../../../styles/color';

export default {
  title: 'Common/Text',
  component: Text,
  argTypes: {
    color: {
      control: { type: 'color' },
    },
  },
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
};

export const Regular = {
  args: {
    size: 16,
    children: '일반글씨',
  },
};

export const ErrorMessage = {
  args: {
    size: 12,
    color: TEXT.error,
    children: '아이디 중복확인을 해주세요',
  },
};

export const ValidMessage = {
  args: {
    size: 12,
    color: TEXT.valid,
    children: '사용 가능한 아이디입니다',
  },
};
