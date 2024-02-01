// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { TOOLS } from '../../constants/toolbar';
import {
  DECORATE_COMPONENT,
  DECORATE_TYPE,
} from '../../constants/decorateComponent';
import useNewDecorateComponent from '../../hooks/useNewDecorateComponent/useNewDecorateComponent';
import DecorateWrapper from '../../components/decorate/DecorateWrapper';

const DailryPage = () => {
  const pageRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);
  const [decorateComponents] = useState([
    {
      id: '123avxsdf',
      type: 'drawing',
      order: 3,
      position: {
        x: 100,
        y: 50,
      },
      size: {
        width: 250,
        height: 150,
      },
      rotation: '60deg',
      typeContent: {
        base64: 'YXNjc2FzYXZmbnJ0bnJ0bnN0',
      },
    },
  ]);
  const [selectedTool, setSelectedTool] = useState(null);

  const {
    createNewDecorateComponent,
    newDecorateComponent,
    setNewDecorateComponent,
    isEdited,
  } = useNewDecorateComponent(decorateComponents, pageRef);

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;

  const [editableDecorateComponent, setEditableDecorateComponent] = useState(
    {},
  );

  const handleClickPage = (e) => {
    if (selectedTool === null || selectedTool === DECORATE_TYPE.MOVING) return;

    if (editableDecorateComponent !== null && isEdited) {
      setNewDecorateComponent(undefined);
      return;
    }

    createNewDecorateComponent(e, selectedTool);
  };

  const handleClickDecorate = (e, index) => {
    e.stopPropagation();
    setTarget(index + 1);
  };

  useEffect(() => {
    if (newDecorateComponent) {
      setEditableDecorateComponent(newDecorateComponent);
    }
  }, [newDecorateComponent]);

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper ref={pageRef} onMouseDown={handleClickPage}>
        {decorateComponents?.map((element, index) => {
          const { id } = element;
          const canEdit = editableDecorateComponent.id === id;
          return (
            <DecorateWrapper
              key={id}
              onMouseDown={(e) => handleClickDecorate(e, index)}
              setTarget={setTarget}
              index={index}
              canEdit={canEdit}
              ref={(el) => {
                moveableRef[index + 1] = el;
              }}
              {...element}
            >
              {DECORATE_TYPE[element.type]}
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
            {DECORATE_COMPONENT[newDecorateComponent.type]}
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
                  setNewDecorateComponent(undefined);
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
