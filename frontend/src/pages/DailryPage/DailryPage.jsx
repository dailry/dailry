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

const DailryPage = () => {
  const parentRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);
  const [decorateComponents, setDecorateComponents] = useState([]);
  const [selectedTool, setSelectedTool] = useState(null);

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
      >
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
      </S.CanvasWrapper>
      <S.SideWrapper>
        <S.ToolWrapper>
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
