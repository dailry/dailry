// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
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
              style={S.ElementStyle({ position, properties })}
            >
              {type}
            </div>
          );
        })}
        <Moveable target={target} moveableRef={moveableRef} />
      </S.CanvasWrapper>
      <S.ToolWrapper>
        <div>tool1크기</div>
        <div>tool2</div>
      </S.ToolWrapper>
    </S.FlexWrapper>
  );
};
export default DailyPage;
