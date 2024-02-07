import { useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import * as S from './TextBox.styled';

const TextBox = (props) => {
  const { typeContent, setTypeContent } = props;
  const textRef = useRef(null);
  const [height, setHeight] = useState(typeContent?.current?.scrollHeight);

  useEffect(() => {
    setHeight(textRef?.current.scrollHeight);
  }, [textRef?.current?.scrollHeight]);

  return (
    <S.TextArea
      ref={textRef}
      value={typeContent}
      height={height}
      onChange={(e) => setTypeContent(e.target.value)}
    />
  );
};

TextBox.propTypes = {
  typeContent: PropTypes.string,
  setTypeContent: PropTypes.func,
};

export default TextBox;
