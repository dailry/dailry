// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef } from 'react';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { TOOLS } from '../../constants/toolbar';
import { LeftArrowIcon, RightArrowIcon } from '../../assets/svg';
import Text from '../../components/common/Text/Text';
import { useDailryContext } from '../../hooks/useDailryContext';
import { postPage, getPages } from '../../apis/dailryApi';
import { DECORATE_TYPE } from '../../constants/decorateComponent';
import useNewDecorateComponent from '../../hooks/useNewDecorateComponent/useNewDecorateComponent';
import DecorateWrapper from '../../components/decorate/DecorateWrapper';
import TypedDecorateComponent from '../../components/decorate/TypedDecorateComponent';
import useCompleteCreation from '../../hooks/useNewDecorateComponent/useCompleteCreation';
import useModifyDecorateComponent from '../../hooks/useModifyDecorateComponent';
import useSetTypeContent from '../../hooks/useSetTypeContent';

const DailryPage = () => {
  const pageRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);
  const [decorateComponents, setDecorateComponents] = useState([]);

  const [selectedTool, setSelectedTool] = useState(null);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  const {
    createNewDecorateComponent,
    newDecorateComponent,
    initializeNewDecorateComponent,
    setNewDecorateComponentTypeContent,
  } = useNewDecorateComponent(
    decorateComponents,
    setDecorateComponents,
    pageRef,
  );

  const {
    canEditDecorateComponent,
    setCanEditDecorateComponent,
    modifyDecorateComponentTypeContent,
  } = useModifyDecorateComponent(setDecorateComponents);

  const { setNewTypeContent } = useSetTypeContent(
    selectedTool,
    newDecorateComponent,
    setNewDecorateComponentTypeContent,
    modifyDecorateComponentTypeContent,
  );

  const { setIsOtherActionTriggered } = useCompleteCreation(
    newDecorateComponent,
    setDecorateComponents,
    initializeNewDecorateComponent,
  );

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;

  const { dailryId, pageId } = currentDailry;

  const handleLeftArrowClick = () => {
    if (pageId === 1) {
      console.log('첫 번째 페이지입니다');
      return;
    }
    setCurrentDailry({ dailryId, pageId: pageId - 1 });
  };

  const handleRightArrowClick = async () => {
    getPages(dailryId).then((response) => {
      if (response.data.pages.length === pageId) {
        console.log('마지막 페이지입니다');
        return;
      }
      setCurrentDailry({ dailryId, pageId: pageId + 1 });
    });
  };

  const handleClickPage = (e) => {
    if (selectedTool === null || selectedTool === DECORATE_TYPE.MOVING) {
      return;
    }

    if (canEditDecorateComponent) {
      setCanEditDecorateComponent(null);
    }

    if (newDecorateComponent) {
      setIsOtherActionTriggered((prev) => !prev);
      return;
    }

    createNewDecorateComponent(e, selectedTool);
  };

  const handleClickDecorate = (e, index) => {
    e.stopPropagation();
    setTarget(index + 1);

    if (newDecorateComponent) {
      setIsOtherActionTriggered((prev) => !prev);
    }
  };

  return (
    <S.FlexWrapper>
      <S.CanvasWrapper ref={pageRef} onMouseDown={handleClickPage}>
        {decorateComponents?.map((element, index) => {
          const canEdit =
            selectedTool !== DECORATE_TYPE.MOVING &&
            element.type === selectedTool &&
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
            const onSelect = (t) => {
              if (t === 'page') {
                postPage(dailryId).then((response) =>
                  setCurrentDailry({ dailryId, pageId: response.data.pageId }),
                );
                setTimeout(() => setSelectedTool(null), 150);
              }
              if (newDecorateComponent) {
                setIsOtherActionTriggered((prev) => !prev);
              }
              setSelectedTool(selectedTool === t ? null : t);
              setCanEditDecorateComponent(null);
            };
            return (
              <ToolButton
                key={index}
                icon={icon}
                selected={selectedTool === type}
                onSelect={() => onSelect(type)}
              />
            );
          })}
        </S.ToolWrapper>
        <S.ArrowWrapper>
          <S.ArrowButton direction={'left'} onClick={handleLeftArrowClick}>
            <LeftArrowIcon />
          </S.ArrowButton>
          <Text bold color={'#ffffff'} size={24}>
            {pageId}
          </Text>
          <S.ArrowButton direction={'right'} onClick={handleRightArrowClick}>
            <RightArrowIcon />
          </S.ArrowButton>
        </S.ArrowWrapper>
      </S.SideWrapper>
    </S.FlexWrapper>
  );
};

export default DailryPage;
