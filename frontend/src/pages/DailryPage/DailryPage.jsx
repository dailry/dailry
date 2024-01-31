// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { TOOLS } from '../../constants/toolbar';
import { DECORATE_TYPE } from '../../constants/decorateComponent';
import useCreateNewDecorateComponent from '../../hooks/useCreateDecorateComponent/useCreateDecorateComponent';
import Decorate from '../../components/decorate/Decorate';

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

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;

  const isNewDecorateComponentEdited =
    newDecorateComponent &&
    Object.values(newDecorateComponent).every((v) => v !== null);

  const initializeCreateNewDecorateComponentState = () => {
    setNewDecorateComponent(undefined);
    setCanEditDecorateComponentId(null);
  };

  const handleClickPage = (e) => {
    if (canEditDecorateComponentId !== null && isNewDecorateComponentEdited) {
      initializeCreateNewDecorateComponentState();
      return;
    }

    createNewDecorateComponent(e, selectedTool);
  };

  const handleClickDecorate = (e, index) => {
    e.stopPropagation();
    setTarget(index + 1);
  };

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper ref={parentRef} onMouseDown={handleClickPage}>
        {decorateComponents.length}
        {decorateComponents.map((element, index) => {
          const { id } = element;
          const canEdit = canEditDecorateComponentId === id;
          return (
            <Decorate
              key={id}
              onMouseDown={(e) => handleClickDecorate(e, index)}
              setTarget={setTarget}
              index={index}
              canEdit={canEdit}
              ref={(el) => {
                moveableRef[index + 1] = el;
              }}
              {...element}
            />
          );
        })}

        {newDecorateComponent && (
          <Decorate
            onMouseDown={(e) => {
              e.stopPropagation();
            }}
            canEdit
            {...newDecorateComponent}
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
