import Drawing from '../../components/decorate/Drawing/Drawing';

// Dailry 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState } from 'react';
import { Rnd } from 'react-rnd';
import * as S from './DailryPage.styled';
import Element from '../../components/dailry/Wrapper/Element';
import Wrapper from '../../components/dailry/Wrapper/Wrapper';
import dailryData from './dailry.json';

const DailryPage = () => {
  const { background, elements } = dailryData;
  const [activeElementId, setActiveElementId] = useState(null);
  const [draggable, setDraggable] = useState(false);
  return (
    <S.FlexWrapper>
      <S.CanvasWrapper>
        <button onClick={() => setDraggable(!draggable)}>
          draggable: {draggable}
        </button>
        <Drawing id="first" />
        {elements.map((element) => {
          return <Element enableResizing={draggable} {...element}/>;
        })}
      </S.CanvasWrapper>
      <S.ToolWrapper>
        <div>tool1크기</div>
        <div>tool2</div>
      </S.ToolWrapper>
    </S.FlexWrapper>
  );
};
export default DailryPage;
