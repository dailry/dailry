// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState } from 'react';
import { Rnd } from 'react-rnd';
import * as S from './DailyPage.styled';
import Element from '../../components/da-ily/Wrapper/Element';
import Wrapper from '../../components/da-ily/Wrapper/Wrapper';
import dailryData from './dailry.json';

const DailyPage = () => {
  const { background, elements } = dailryData;
  const [activeElementId, setActiveElementId] = useState(null);
  const [draggable, setDraggable] = useState(false);

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper>
        <button onClick={() => setDraggable(!draggable)}>
          draggable: {draggable}
        </button>
        {elements.map((element) => {
          return <Element enableResizing={draggable} {...element} />;
        })}
      </S.CanvasWrapper>
      <S.ToolWrapper>
        <div>tool1크기</div>
        <div>tool2</div>
      </S.ToolWrapper>
    </S.FlexWrapper>
  );
};
export default DailyPage;
