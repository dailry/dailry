// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import Drawing from '../../components/decorate/Drawing/Drawing';
import * as S from './DailryPage.styled';
import dailryData from './dailry.json';
import Button from '../../components/common/Button/Button';
import useCreateDecorateComponent from '../../hooks/useCreateDecorateComponent';

const DecorateComponents = {
  drawing: {
    type: 'drawing',
    component: <Drawing />,
  },
};

const DailryPage = () => {
  const { elements } = dailryData;
  const [target, setTarget] = useState(null);
  const moveableRef = useRef(null);
  const [decorateComponents, setDecorateComponents] = useState([]);
  const { setNewDecorateComponentPosition, createNewDecorateComponent } =
    useCreateDecorateComponent(setDecorateComponents);

  useEffect(() => {
    // if (elements) setDecorateComponents(elements);
  }, [elements]);

  const handleMouseDown = (e) => {
    const { nativeEvent } = e;
    setTarget(nativeEvent.target);
    if (moveableRef.current) {
      moveableRef.current.dragStart(nativeEvent);
    }
  };
  return (
    <S.FlexWrapper>
      <S.CanvasWrapper onClick={(e) => setNewDecorateComponentPosition(e)}>
        <Drawing id="first" />
        {decorateComponents.map((element) => {
          const { id, type, position, properties } = element;
          return (
            <div
              key={id}
              onMouseDown={handleMouseDown}
              style={S.ElementStyle({ position, properties })}
            >
              {DecorateComponents[type].component}
            </div>
          );
        })}
        {target && <Moveable target={target} moveableRef={moveableRef} />}
      </S.CanvasWrapper>
      <S.ToolWrapper>
        <Button onClick={() => createNewDecorateComponent('drawing')}>
          드로잉 컴포넌트 생성
        </Button>
        <div>tool1크기</div>
        <div>tool2</div>
      </S.ToolWrapper>
    </S.FlexWrapper>
  );
};
export default DailryPage;
