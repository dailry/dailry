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
  const [selectedTool, setSelectedTool] = useState(null);

  const {
    createNewDecorateComponent,
    newDecorateComponent,
    setNewDecorateComponent,
  } = useNewDecorateComponent(decorateComponents, pageRef);

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;
  const editable = () => selectedTool !== DECORATE_TYPE.MOVING;

  const handleClickPage = (e) => {
    if (selectedTool === null || selectedTool === DECORATE_TYPE.MOVING) return;

    createNewDecorateComponent(e, selectedTool);
  };

  const handleClickDecorate = (e, index) => {
    e.stopPropagation();
    setTarget(index + 1);
  };

  useEffect(() => {
    if (selectedTool === 'moving') {
      return;
    }
    if (newTypeContent) {
      setNewDecorateComponent((prev) => ({
        ...prev,
        typeContent: newTypeContent,
      }));
    }
  }, [newTypeContent]);

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper ref={pageRef} onMouseDown={handleClickPage}>
        {decorateComponents?.map((element, index) => {
          const { id } = element;

          return (
            <DecorateWrapper
              key={id}
              onMouseDown={(e) => handleClickDecorate(e, index)}
              setTarget={setTarget}
              index={index}
              canEdit={editable()}
              ref={(el) => {
                moveableRef[index + 1] = el;
              }}
              {...element}
            >
              <TypedDecorateComponent
                type={element.type}
                typeContent={element.typeContent}
                editable={editable()}
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
              editable={true}
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
                  if (newDecorateComponent?.typeContent) {
                    setDecorateComponents((prev) =>
                      prev.concat(newDecorateComponent),
                    );
                  }
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
