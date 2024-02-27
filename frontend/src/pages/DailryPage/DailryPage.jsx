// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import html2canvas from 'html2canvas';
import saveAs from 'file-saver';
import { toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { DECORATE_TOOLS, PAGE_TOOLS } from '../../constants/toolbar';
import { LeftArrowIcon, RightArrowIcon } from '../../assets/svg';
import { useDailryContext } from '../../hooks/useDailryContext';
import { postPage, getPages } from '../../apis/dailryApi';
import { DECORATE_TYPE, EDIT_MODE } from '../../constants/decorateComponent';
import useNewDecorateComponent from '../../hooks/useNewDecorateComponent/useNewDecorateComponent';
import DecorateWrapper from '../../components/decorate/DecorateWrapper';
import TypedDecorateComponent from '../../components/decorate/TypedDecorateComponent';
import PageListModal from '../../components/da-ily/PageListModal/PageListModal';
import useCompleteCreation from '../../hooks/useNewDecorateComponent/useCompleteCreation';
import useEditDecorateComponent from '../../hooks/useEditDecorateComponent';
import useDecorateComponents from '../../hooks/useDecorateComponents';
import useUpdatedDecorateComponents from '../../hooks/useUpdatedDecorateComponents';
import MoveableComponent from '../../components/da-ily/Moveable/Moveable';

const DailryPage = () => {
  const pageRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);

  const [selectedTool, setSelectedTool] = useState(null);
  const [showPageModal, setShowPageModal] = useState(false);
  const [pageList, setPageList] = useState(null);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  const { addUpdatedDecorateComponent, modifyUpdatedDecorateComponent } =
    useUpdatedDecorateComponents();

  const {
    decorateComponents,
    addNewDecorateComponent,
    modifyDecorateComponent,
  } = useDecorateComponents();

  const {
    newDecorateComponent,
    createNewDecorateComponent,
    removeNewDecorateComponent,
    setNewDecorateComponentTypeContent,
  } = useNewDecorateComponent(decorateComponents, pageRef);

  const { completeCreateNewDecorateComponent } = useCompleteCreation(
    newDecorateComponent,
    addNewDecorateComponent,
    removeNewDecorateComponent,
    addUpdatedDecorateComponent,
  );

  const {
    editMode,
    setEditMode,
    canEditDecorateComponent,
    setCanEditDecorateComponent,
    setCanEditDecorateComponentTypeContent,
    setCanEditDecorateComponentCommonProperty,
    completeModifyDecorateComponent,
  } = useEditDecorateComponent(
    modifyDecorateComponent,
    modifyUpdatedDecorateComponent,
  );

  const isMoveable = () => target && editMode === EDIT_MODE.COMMON_PROPERTY;

  const { dailryId, pageId } = currentDailry;

  useEffect(() => {
    getPages(dailryId).then((response) => setPageList(response.data.pages));
  }, [dailryId, pageId, showPageModal]);

  const toastify = (message) => {
    toast(message, {
      position: 'bottom-right',
      autoClose: 300,
      hideProgressBar: true,
      closeOnClick: true,
      transition: Zoom,
    });
  };

  const handleLeftArrowClick = () => {
    if (pageId === 1) {
      toastify('첫 번째 페이지입니다');
      return;
    }
    setCurrentDailry({ dailryId, pageId: pageId - 1 });
  };

  const handleRightArrowClick = () => {
    if (pageList.length === pageId) {
      toastify('마지막 페이지입니다');
      return;
    }
    setCurrentDailry({ dailryId, pageId: pageId + 1 });
  };

  const handleDownloadClick = async () => {
    try {
      const pageImg = await html2canvas(pageRef.current);
      pageImg.toBlob((blob) => {
        if (blob !== null) {
          saveAs(blob, `${dailryId}_${pageId}.png`);
        }
      });
    } catch (e) {
      console.error('이미지 변환 오류', e);
    }
  };

  const handleClickPage = (e) => {
    if (selectedTool === null || editMode === EDIT_MODE.COMMON_PROPERTY) {
      return;
    }

    if (newDecorateComponent) {
      completeCreateNewDecorateComponent();
      return;
    }

    createNewDecorateComponent(e, selectedTool);
  };

  const handleClickDecorate = (e, index, element) => {
    e.stopPropagation();
    setTarget(index + 1);

    if (newDecorateComponent) {
      completeCreateNewDecorateComponent();

      return;
    }

    if (
      canEditDecorateComponent &&
      canEditDecorateComponent?.id !== element.id
    ) {
      console.log(
        'this is canEdit',
        canEditDecorateComponent,
        'this is current element',
        element,
        moveableRef[target],
      );
      completeModifyDecorateComponent();
      moveableRef[target].style.transform = 'translate(0px, 0px)';
    }

    setCanEditDecorateComponent(element);
  };

  return (
    <S.FlexWrapper>
      {showPageModal && (
        <PageListModal
          pageList={pageList}
          onClose={() => setShowPageModal(false)}
        />
      )}
      <S.CanvasWrapper ref={pageRef} onMouseDown={handleClickPage}>
        {decorateComponents?.map((element, index) => {
          const canEdit =
            editMode === EDIT_MODE.TYPE_CONTENT &&
            element.type === selectedTool &&
            canEditDecorateComponent?.id === element.id;
          return (
            <DecorateWrapper
              key={element.id}
              onMouseDown={(e) => handleClickDecorate(e, index, element)}
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
                setTypeContent={setCanEditDecorateComponentTypeContent}
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
              setTypeContent={setNewDecorateComponentTypeContent}
            />
          </DecorateWrapper>
        )}
        {isMoveable() && (
          <MoveableComponent
            target={moveableRef[target]}
            setCommonProperty={setCanEditDecorateComponentCommonProperty}
          />
        )}
      </S.CanvasWrapper>
      <S.SideWrapper>
        <S.ToolWrapper>
          {DECORATE_TOOLS.map(({ icon, type }, index) => {
            const onSelect = (t) => {
              if (newDecorateComponent) {
                completeCreateNewDecorateComponent();
              }
              if (canEditDecorateComponent) {
                completeModifyDecorateComponent();
                setCanEditDecorateComponent(null);
                moveableRef[target].style.transform = 'translate(0px, 0px)';
              }
              setSelectedTool(selectedTool === t ? null : t);
              if (t === DECORATE_TYPE.MOVING) {
                setEditMode(EDIT_MODE.COMMON_PROPERTY);
              } else {
                setEditMode(EDIT_MODE.TYPE_CONTENT);
              }
              setTarget(null);
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
          {PAGE_TOOLS.map(({ icon, type }, index) => {
            const onSelect = (t) => {
              if (newDecorateComponent) {
                completeCreateNewDecorateComponent();
              }
              if (canEditDecorateComponent) {
                completeModifyDecorateComponent();
                setCanEditDecorateComponent(null);
                moveableRef[target].style.transform = 'translate(0px, 0px)';
              }
              setSelectedTool(selectedTool === t ? null : t);
              setTimeout(() => {
                setSelectedTool(null);
              }, 150);
              if (t === 'add') {
                postPage(dailryId).then((response) =>
                  setCurrentDailry({ dailryId, pageId: response.data.pageId }),
                );
              }
              if (t === 'download') {
                handleDownloadClick();
              }
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
          <S.NumberButton onClick={() => setShowPageModal(true)}>
            {pageId}
          </S.NumberButton>
          <S.ArrowButton direction={'right'} onClick={handleRightArrowClick}>
            <RightArrowIcon />
          </S.ArrowButton>
        </S.ArrowWrapper>
      </S.SideWrapper>
    </S.FlexWrapper>
  );
};

export default DailryPage;
