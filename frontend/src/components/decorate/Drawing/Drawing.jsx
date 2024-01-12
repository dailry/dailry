import { useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { canvasToImage } from './canvas';
import useDrawing from './useDrawing';
import AuthButton from '../../common/AuthButton/AuthButton';

const Drawing = (props) => {
  const { id } = props;
  const canvas = useRef(null);

  const { saveCanvas, startDrawing, stopDrawing } = useDrawing(canvas);
  const [imgSrc, setImgSrc] = useState('');

  return (
    <>
      <canvas
        onMouseDown={(e) => startDrawing(e)}
        onMouseUp={(e) => stopDrawing(e)}
        ref={canvas}
      ></canvas>
      <image id="canvas-draw-image" alt="converted-image" src={imgSrc} />
      <AuthButton onClick={() => setImgSrc(canvasToImage(id))}>
        이미지로 변환하기
      </AuthButton>

      <AuthButton onClick={() => saveCanvas()}>저장</AuthButton>
    </>
  );
};

Drawing.propTypes = {
  id: PropTypes.string,
};

export default Drawing;
