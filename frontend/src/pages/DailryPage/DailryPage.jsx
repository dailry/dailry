// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import useCreateDecorateComponent from '../../hooks/useCreateDecorateComponent/useCreateDecorateComponent';
import { TOOLS } from '../../constants/toolbar';
import {
  DECORATE_COMPONENT,
  DECORATE_TYPE,
} from '../../constants/decorateComponent';
import useDrawInstance from '../../components/decorate/Drawing/useDrawInstance';
import useDrawUtils from '../../components/decorate/Drawing/useDrawUtils';
import Button from '../../components/common/Button/Button';

const DailryPage = () => {
  const parentRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);
  const [decorateComponents, setDecorateComponents] = useState([]);
  const [selectedTool, setSelectedTool] = useState(null);

  const { drawInstance } = useDrawInstance(parentRef);
  const {
    saveCanvas,
    mouseEventHandlers,
    startMoving,
    stopMoving,
    mode,
    setMode,
  } = useDrawUtils(parentRef, drawInstance);

  const { createNewDecorateComponent } = useCreateDecorateComponent(
    decorateComponents,
    setDecorateComponents,
    parentRef,
  );

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper
        ref={parentRef}
        onClick={(e) => createNewDecorateComponent(e, selectedTool)}
        onMouseDown={(e) => {
          if (selectedTool === DECORATE_TYPE.DRAWING)
            startMoving(e, mouseEventHandlers[mode]);
        }}
        onMouseUp={() => {
          if (selectedTool === DECORATE_TYPE.DRAWING)
            stopMoving(mouseEventHandlers[mode]);
        }}
      />

      {decorateComponents.map((element, index) => {
        const { id, type, position, properties, order, size } = element;
        return (
          <div
            key={id}
            onMouseDown={() => setTarget(index + 1)}
            style={S.ElementStyle({ position, properties, order, size })}
            ref={(el) => {
              moveableRef[index + 1] = el;
            }}
          >
            {DECORATE_COMPONENT[type]}
          </div>
        );
      })}

      {isMoveable() && <Moveable target={moveableRef[target]} />}

      <S.SideWrapper>
        <S.ToolWrapper>
          <Button onClick={() => saveCanvas()}>저장</Button>
          <Button onClick={() => setMode('drawMode')}>그리기</Button>

          <Button onClick={() => setMode('eraseMode')}>지우기</Button>
          {TOOLS.map(({ icon, type }, index) => {
            return (
              <ToolButton
                key={index}
                icon={icon}
                selected={selectedTool === type}
                onSelect={() =>
                  setSelectedTool(selectedTool === type ? null : type)
                }
              />
            );
          })}
        </S.ToolWrapper>
      </S.SideWrapper>
    </S.FlexWrapper>
  );
};

export default DailryPage;
