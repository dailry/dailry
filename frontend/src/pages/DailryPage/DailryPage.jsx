// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import html2canvas from 'html2canvas';
import saveAs from 'file-saver';
import { toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Moveable from '../../components/da-ily/Moveable/Moveable';
import * as S from './DailryPage.styled';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { DECORATE_TOOLS, PAGE_TOOLS } from '../../constants/toolbar';
import { LeftArrowIcon, RightArrowIcon } from '../../assets/svg';
import { useDailryContext } from '../../hooks/useDailryContext';
import { postPage, getPages } from '../../apis/dailryApi';
import { DECORATE_TYPE } from '../../constants/decorateComponent';
import useNewDecorateComponent from '../../hooks/useNewDecorateComponent/useNewDecorateComponent';
import DecorateWrapper from '../../components/decorate/DecorateWrapper';
import TypedDecorateComponent from '../../components/decorate/TypedDecorateComponent';
import PageListModal from '../../components/da-ily/PageListModal/PageListModal';
import useCompleteCreation from '../../hooks/useNewDecorateComponent/useCompleteCreation';
import useModifyDecorateComponent from '../../hooks/useModifyDecorateComponent';
import useSetTypeContent from '../../hooks/useSetTypeContent';
import useDecorateComponents from '../../hooks/useDecorateComponents';
import useUpdatedDecorateComponents from '../../hooks/useUpdatedDecorateComponents';

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
    modifyDecorateComponentTypeContent,
  } = useDecorateComponents();

  const {
    newDecorateComponent,
    createNewDecorateComponent,
    initializeNewDecorateComponent,
    setNewDecorateComponentTypeContent,
  } = useNewDecorateComponent(decorateComponents, pageRef);

  const { setIsOtherActionTriggered } = useCompleteCreation(
    newDecorateComponent,
    addNewDecorateComponent,
    initializeNewDecorateComponent,
    addUpdatedDecorateComponent,
  );

  const {
    canEditDecorateComponent,
    setCanEditDecorateComponent,
    setCanEditDecorateComponentTypeContent,
  } = useModifyDecorateComponent(
    modifyDecorateComponentTypeContent,
    modifyUpdatedDecorateComponent,
  );

  const { setNewTypeContent } = useSetTypeContent(
    selectedTool,
    newDecorateComponent,
    setNewDecorateComponentTypeContent,
    setCanEditDecorateComponentTypeContent,
  );

  const isMoveable = () => target && selectedTool === DECORATE_TYPE.MOVING;

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
      {showPageModal && (
        <PageListModal
          pageList={pageList}
          onClose={() => setShowPageModal(false)}
        />
      )}
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
          {DECORATE_TOOLS.map(({ icon, type }, index) => {
            const onSelect = (t) => {
              if (newDecorateComponent) {
                setIsOtherActionTriggered((prev) => !prev);
              }
              setSelectedTool(selectedTool === t ? null : t);
              setCanEditDecorateComponent(undefined);
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
                setIsOtherActionTriggered((prev) => !prev);
              }
              setSelectedTool(selectedTool === t ? null : t);
              setCanEditDecorateComponent(null);
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
