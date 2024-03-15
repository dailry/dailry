import PropTypes, { oneOf } from 'prop-types';
import * as S from './Text.styled';
import { TEXT } from '../../../styles/color';

const Text = (props) => {
  const {
    as = 'p',
    size,
    weight = 400,
    color = TEXT.black,
    css,
    children,
  } = props;
  return (
    <S.TextWrapper
      as={as}
      size={`${size}px`}
      weight={weight}
      color={color}
      css={css}
    >
      {children}
    </S.TextWrapper>
  );
};

const rHexColor = /^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/;
const rRgbaColor =
  /^rgba\(\s?\d+\s?,\s?\d+\s?,\s?\d+\s?,\s?(1|0(\.\d+)?)\s?\)$/;
const colorPropType = (props, propName, componentName) => {
  const value = props[propName];
  if (
    typeof value !== 'string' ||
    (!rHexColor.test(value) && !rRgbaColor.test(value))
  ) {
    return new Error(
      `Invalid prop ${propName} supplied to ${componentName}. Validation failed.`,
    );
  }
  return null;
};

Text.propTypes = {
  as: oneOf([
    'h1',
    'h2',
    'h3',
    'h4',
    'h5',
    'h6',
    'p',
    'span',
    'strong',
    'small',
  ]),
  size: PropTypes.number,
  weight: PropTypes.number,
  color: colorPropType,
  css: PropTypes.object,
  children: PropTypes.string,
};

export default Text;
