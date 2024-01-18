// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from 'react-moveable';
import Drawing from '../../components/decorate/Drawing/Drawing';
import * as S from './DailyPage.styled';
import dailryData from './dailry.json';

const DailyPage = () => {
  const { elements } = dailryData;
  const [target, setTarget] = useState(null);
  const moveableRef = useRef(null);

  const handleMouseDown = (e) => {
    const { nativeEvent } = e;
    setTarget(nativeEvent.target);
    if (moveableRef.current) {
      moveableRef.current.dragStart(nativeEvent);
    }
  };
  return (
    <S.FlexWrapper>
      <S.CanvasWrapper>
        <Drawing id="first" />
        {elements.map((element) => {
          const { id, type, position, properties } = element;
          return (
            <div
              key={id}
              onMouseDown={handleMouseDown}
              style={{
                position: 'absolute',
                left: position.x,
                top: position.y,
                width: properties.width,
                height: properties.height,
                backgroundColor: properties.backgroundColor,
              }}
            >
              {type}
            </div>
          );
        })}
        <Moveable
          ref={moveableRef}
          target={target}
          draggable={true}
          onDrag={({ transform }) => {
            target.style.transform = transform;
          }}
          throttleDrag={0}
          resizable={true}
          throttleResize={0}
          onResize={({ width, height }) => {
            target.style.width = `${width}px`;
            target.style.height = `${height}px`;
          }}
          rotatable={true}
          throttleRotate={0}
          onRotate={({ transform }) => {
            target.style.transform = transform;
          }}
        />
      </S.CanvasWrapper>
      <S.ToolWrapper>
        <div>tool1크기</div>
        <div>tool2</div>
      </S.ToolWrapper>
    </S.FlexWrapper>
  );
};
export default DailyPage;
