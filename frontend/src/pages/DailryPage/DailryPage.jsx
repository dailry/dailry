// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';

import * as S from './DailryPage.styled';
import dailryData from './dailry.json';

const DailryPage = () => {
  const { elements } = dailryData;
  const [target, setTarget] = useState(null);
  const moveableRef = useRef([]);

  const handleMouseDown = (e, index) => {
    setTarget(index);
  };
  return (
    <S.FlexWrapper>
      <S.CanvasWrapper>
        {elements.map((element, index) => {
          const { id, type, position, properties } = element;
          return (
            <div
              key={id}
              onMouseDown={(e) => handleMouseDown(e, index)}
              style={S.ElementStyle({ position, properties })}
              ref={(el) => {
                moveableRef[index] = el;
              }}
            >
              {type}
              <div>child element</div>
            </div>
          );
        })}
        {target && <Moveable target={moveableRef[target]} />}
      </S.CanvasWrapper>
      <S.ToolWrapper>
        <div>tool1크기</div>
        <div>tool2</div>
      </S.ToolWrapper>
    </S.FlexWrapper>
  );
};
export default DailryPage;
