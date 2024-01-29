import { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import * as S from './TextBox.styled';

const TextBox = (props) => {
  const { text, setText } = props;
  const textRef = useRef(null);
  const [height, setHeight] = useState(text?.current?.scrollHeight);

  useEffect(() => {
    setHeight(textRef?.current.scrollHeight);
  }, [textRef?.current?.scrollHeight]);

  return (
    <S.TextArea
      ref={textRef}
      value={text}
      height={height}
      onChange={(e) => setText(e.target.value)}
    />
  );
};

TextBox.propTypes = {
  text: PropTypes.string,
  setText: PropTypes.func,
};

export default TextBox;
