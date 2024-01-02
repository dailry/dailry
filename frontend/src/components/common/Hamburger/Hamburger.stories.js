import { node } from 'prop-types';
import Hamburger from './Hamburger';
import { HamburgerItem } from './Hamburger.styled';

export default {
  title: 'Common/Hamburger',
  component: Hamburger,
  argTypes: {
    color: {
      children: node,
      anchor: 'left' || 'right',
    },
  },
  parameters: {
    layout: 'centered',
  },
  tags: ['autodocs'],
};

export const Left = {
  args: {
    children: (
      <HamburgerItem>
        <div>테스트1</div>
        <div>테스트2</div>
      </HamburgerItem>
    ),
    anchor: 'left',
  },
};

export const Right = {
  args: {
    children: (
      <HamburgerItem>
        <div>테스트1</div>
        <div>테스트2</div>
      </HamburgerItem>
    ),
    anchor: 'right',
  },
};
