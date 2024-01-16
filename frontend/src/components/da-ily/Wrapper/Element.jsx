import { Rnd } from 'react-rnd';
import { useState } from 'react';

// !TODO 내부 요소 렌더링 고민
// !TODO 캔버스 안에서만 움직일수있게
// !TODO 이동한 크기와 위치, 각도 저장
// !TODO 이동모드와 아닐때 설정
// !TODO 회전

const Element = (props) => {
  const { children, key, type, order, position, rotation, properties } = props;
  const { x, y } = position;
  const {
    font,
    fontSize,
    text,
    fontweight,
    backgroundColor,
    width,
    height,
    color,
  } = properties;

  const [pos, setPos] = useState({ x, y });
  const [size, setSize] = useState({ width, height });
  const [deg, setDeg] = useState(rotation);

  const handleResize = (e, direction, ref, delta, newPos) => {
    setSize({ width: ref.offsetWidth, height: ref.offsetHeight });
    setPos({ ...newPos });
  };

  const handleDragStop = (e, d) => {
    setPos({ x: d.x, y: d.y });
  };

  return (
    <Rnd
      position={pos}
      size={size}
      onResize={handleResize}
      onDragStop={handleDragStop}
      style={{ backgroundColor }}
    >
      {children}
    </Rnd>
  );
};

export default Element;
