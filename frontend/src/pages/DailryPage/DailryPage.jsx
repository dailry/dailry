// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { TOOLS } from '../../constants/toolbar';
import { DECORATE_TYPE } from '../../constants/decorateComponent';
import useNewDecorateComponent from '../../hooks/useNewDecorateComponent/useNewDecorateComponent';
import DecorateWrapper from '../../components/decorate/DecorateWrapper';
import TypedDecorateComponent from '../../components/decorate/TypedDecorateComponent';

const DailryPage = () => {
  const pageRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);
  const [newTypeContent, setNewTypeContent] = useState(undefined);
  const [decorateComponents, setDecorateComponents] = useState([]);
  const [canEditDecorateComponent, setCanEditDecorateComponent] =
    useState(undefined);
  const [selectedTool, setSelectedTool] = useState(null);

  const {
    createNewDecorateComponent,
    newDecorateComponent,
    setNewDecorateComponent,
  } = useNewDecorateComponent(decorateComponents, pageRef);

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;

  const isNewDecorateComponentCompleted =
    newDecorateComponent?.typeContent &&
    Object.values(newDecorateComponent?.typeContent).every((v) => v !== null);

  const addNewDecorateComponent = () => {
    if (isNewDecorateComponentCompleted) {
      setDecorateComponents((prev) => prev.concat(newDecorateComponent));
    }
    setNewDecorateComponent(undefined);
  };

  const setNewDecorateComponentTypeContent = () => {
    setNewDecorateComponent((prev) => ({
      ...prev,
      typeContent: newTypeContent,
    }));
  };

  const modifyDecorateComponentTypeContent = () => {
    setDecorateComponents((prev) =>
      prev.map((c) => {
        return c.id === canEditDecorateComponent.id
          ? { ...c, typeContent: newTypeContent }
          : c;
      }),
    );
  };

  const handleClickPage = (e) => {
    if (selectedTool === null || selectedTool === DECORATE_TYPE.MOVING) return;

    if (canEditDecorateComponent) setCanEditDecorateComponent(undefined);

    if (newDecorateComponent) {
      addNewDecorateComponent();
      return;
    }

    createNewDecorateComponent(e, selectedTool);
  };

  const handleClickDecorate = (e, index) => {
    e.stopPropagation();
    setTarget(index + 1);

    if (newDecorateComponent) {
      addNewDecorateComponent();
    }
  };

  useEffect(() => {
    if (selectedTool === DECORATE_TYPE.MOVING) {
      return;
    }
    if (newDecorateComponent && newTypeContent) {
      setNewDecorateComponentTypeContent();

      return;
    }

    modifyDecorateComponentTypeContent();
  }, [newTypeContent]);

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper ref={pageRef} onMouseDown={handleClickPage}>
        {decorateComponents?.map((element, index) => {
          const canEdit =
            selectedTool !== DECORATE_TYPE.MOVING &&
            canEditDecorateComponent?.id === element.id;
          return (
            <DecorateWrapper
              key={element.id}
              onMouseDown={(e) => handleClickDecorate(e, index, element)}
              onMouseUp={() => {
                if (selectedTool !== DECORATE_TYPE.MOVING)
                  setCanEditDecorateComponent(element);
              }}
              setTarget={setTarget}
              index={index}
              canEdit={canEdit}
              ref={(el) => {
                moveableRef[index + 1] = el;
              }}
              {...element}
            >
              <TypedDecorateComponent
                type={element.type}
                typeContent={element.typeContent}
                canEdit={canEdit}
                setTypeContent={setNewTypeContent}
              />
            </DecorateWrapper>
          );
        })}

        {newDecorateComponent && (
          <DecorateWrapper
            onMouseDown={(e) => {
              e.stopPropagation();
            }}
            canEdit
            {...newDecorateComponent}
          >
            <TypedDecorateComponent
              type={newDecorateComponent.type}
              canEdit
              setTypeContent={setNewTypeContent}
            />
          </DecorateWrapper>
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
                  if (newDecorateComponent) {
                    addNewDecorateComponent();
                  }
                  setSelectedTool(selectedTool === type ? null : type);
                  setCanEditDecorateComponent(undefined);
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
