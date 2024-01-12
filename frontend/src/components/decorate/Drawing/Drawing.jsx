import { useState } from 'react';
import PropTypes from 'prop-types';
import { canvasToImage } from './canvas';
import useDrawing from './useDrawing';
import AuthButton from '../../common/AuthButton/AuthButton';

const Drawing = (props) => {
  const { id } = props;
  const { saveDrawData, loadCanvas } = useDrawing(id);
  const [imgSrc, setImgSrc] = useState('');

  return (
    <>
      <canvas id={id}></canvas>
      <image id="canvas-draw-image" alt="converted-image" src={imgSrc} />
      <AuthButton onClick={() => setImgSrc(canvasToImage(id))}>
        이미지로 변환하기
      </AuthButton>

      <AuthButton onClick={() => saveDrawData()}>저장</AuthButton>
      <AuthButton onClick={() => loadCanvas()}>불러오기</AuthButton>

      <canvas id="loaded-canvas"></canvas>
    </>
  );
};

Drawing.propTypes = {
  id: PropTypes.string,
};

export default Drawing;
