// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { TOOLS } from '../../constants/toolbar';
import {
  DECORATE_COMPONENT,
  DECORATE_TYPE,
} from '../../constants/decorateComponent';
import NewTemporaryComponent from '../../components/decorate/NewTemporaryComponent/NewTemporaryComponent';
import useCreateNewDecorateComponent from '../../hooks/useCreateDecorateComponent/useCreateDecorateComponent';

const DailryPage = () => {
  const parentRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);
  const [decorateComponents] = useState([]);
  const [selectedTool, setSelectedTool] = useState(null);
  const [canEditDecorateComponentId, setCanEditDecorateComponentId] =
    useState(null);

  const {
    createNewDecorateComponent,
    newDecorateComponent,
    setNewDecorateComponent,
  } = useCreateNewDecorateComponent({
    decorateComponents,
    parentRef,
    setCanEditDecorateComponentId,
  });

  const isMoveable = () =>
    target &&
    selectedTool === DECORATE_TYPE.MOVING &&
    canEditDecorateComponentId === null;

  const isNewDecorateComponentEdited =
    newDecorateComponent &&
    Object.values(newDecorateComponent).every((v) => v !== null);

  const initializeCreateNewDecorateComponentState = () => {
    setNewDecorateComponent(undefined);
    setCanEditDecorateComponentId(null);
  };

  const handleMouseDown = (e) => {
    if (canEditDecorateComponentId !== null && isNewDecorateComponentEdited) {
      initializeCreateNewDecorateComponentState();
      return;
    }

    createNewDecorateComponent(e, selectedTool);
  };

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper ref={parentRef} onMouseDown={handleMouseDown}>
        {decorateComponents.map((element, index) => {
          const { id, type, position, properties, order, size } = element;
          const canEdit = canEditDecorateComponentId === id;
          return (
            <div
              key={id}
              onMouseDown={(e) => {
                e.stopPropagation();
                setTarget(index + 1);
              }}
              style={S.ElementStyle({
                position,
                properties,
                order,
                size,
                canEdit,
              })}
              ref={(el) => {
                moveableRef[index + 1] = el;
              }}
            >
              {DECORATE_COMPONENT[type]}
            </div>
          );
        })}

        {newDecorateComponent && (
          <NewTemporaryComponent
            newDecorateComponent={newDecorateComponent}
            canEditDecorateComponentId={canEditDecorateComponentId}
          />
        )}
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
                onSelect={() => {
                  initializeCreateNewDecorateComponentState();
                  setSelectedTool(selectedTool === type ? null : type);
                }}
              />
            );
          })}
        </S.ToolWrapper>
      </S.SideWrapper>
    </S.FlexWrapper>
  );
};

export default DailryPage;
