// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import html2canvas from 'html2canvas';
import saveAs from 'file-saver';
import { toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import * as S from './DailryPage.styled';
import Text from '../../components/common/Text/Text';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import { DECORATE_TOOLS, PAGE_TOOLS } from '../../constants/toolbar';
import { LeftArrowIcon, RightArrowIcon } from '../../assets/svg';
import { useDailryContext } from '../../hooks/useDailryContext';
import { postPage, getPages, patchPage, getPage } from '../../apis/dailryApi';
import { DECORATE_TYPE, EDIT_MODE } from '../../constants/decorateComponent';
import useNewDecorateComponent from '../../hooks/useNewDecorateComponent/useNewDecorateComponent';
import DecorateWrapper from '../../components/decorate/DecorateWrapper';
import TypedDecorateComponent from '../../components/decorate/TypedDecorateComponent';
import PageListModal from '../../components/da-ily/PageListModal/PageListModal';
import useCompleteCreation from '../../hooks/useNewDecorateComponent/useCompleteCreation';
import useEditDecorateComponent from '../../hooks/useEditDecorateComponent';
import useDecorateComponents from '../../hooks/useDecorateComponents';
import useUpdatedDecorateComponents from '../../hooks/useUpdatedDecorateComponents';
import { TEXT } from '../../styles/color';
import MoveableComponent from '../../components/da-ily/Moveable/Moveable';
import usePageData from '../../hooks/usePageData';

const DailryPage = () => {
  const pageRef = useRef(null);
  const moveableRef = useRef([]);

  const [target, setTarget] = useState(null);

  const [selectedTool, setSelectedTool] = useState(null);
  const [showPageModal, setShowPageModal] = useState(false);
  const [pageList, setPageList] = useState([]);
  const [havePage, setHavePage] = useState(true);
  const { currentDailry, setCurrentDailry } = useDailryContext();

  const {
    updatedDecorateComponents,
    addUpdatedDecorateComponent,
    modifyUpdatedDecorateComponent,
  } = useUpdatedDecorateComponents();

  const { getPageFormData, onUploadFile } = usePageData(
    updatedDecorateComponents,
  );

  const {
    decorateComponents,
    setDecorateComponents,
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

  const { dailryId, pageId, pageIds, pageNumber } = currentDailry;

  useEffect(() => {
    (async () => {
      const { status, data } = await getPages(dailryId);

      if (status === 200 && data.pages.length !== 0) {
        const { pages } = data;
        const pageIdList = pages.map((page) => page.pageId);
        setPageList(pageIdList);

        setCurrentDailry({
          ...currentDailry,
          pageIds: pageIdList,
          pageNumber: pageIds ? 1 : null,
        });

        return setHavePage(true);
      }

      return setHavePage(false);
    })();
  }, [dailryId]);

  useEffect(() => {
    (async () => {
      setDecorateComponents([]);
      const page = await getPage(pageIds[pageNumber - 1]);

      if (page.data?.elements.length > 0) {
        const datas = page.data?.elements.map((i) => ({
          ...i,
          initialStyle: {
            ...i.initialStyle,
            position: i?.position,
            size: i?.size,
            rotation: i?.rotation,
          },
        }));
        setDecorateComponents(datas);
      }
    })();
  }, [pageIds, pageNumber]);

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
    if (pageNumber === 1) {
      toastify('첫 번째 페이지입니다');
      return;
    }
    setCurrentDailry({ ...currentDailry, pageNumber: pageNumber - 1 });
  };

  const handleRightArrowClick = () => {
    if (pageList.length === pageNumber) {
      toastify('마지막 페이지입니다');
      return;
    }
    setCurrentDailry({ ...currentDailry, pageNumber: pageNumber + 1 });
  };

  const handleDownloadClick = async () => {
    try {
      const pageImg = await html2canvas(pageRef.current);
      pageImg.toBlob((blob) => {
        if (blob !== null) {
          saveAs(blob, `dailry${dailryId}_${pageId}.png`);
        }
      });
    } catch (e) {
      console.error('이미지 변환 오류', e);
    }
  };

  const handleClickPage = (e) => {
    if (selectedTool === null) {
      return;
    }

    if (editMode === EDIT_MODE.COMMON_PROPERTY) {
      if (canEditDecorateComponent) {
        completeModifyDecorateComponent();
        setTarget(null);

        setCanEditDecorateComponent(null);
      }
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

    if (
      canEditDecorateComponent &&
      canEditDecorateComponent.id !== element.id
    ) {
      completeModifyDecorateComponent();
      setTarget(null);
      setCanEditDecorateComponent(null);
      return;
    }

    setTarget(index + 1);

    if (newDecorateComponent) {
      completeCreateNewDecorateComponent();

      return;
    }

    setCanEditDecorateComponent(element);

    if (
      canEditDecorateComponent &&
      canEditDecorateComponent.id !== element.id
    ) {
      setTarget(null);
    }
  };
  useEffect(() => {
    console.log(canEditDecorateComponent);
  }, [canEditDecorateComponent]);

  return (
    <S.FlexWrapper>
      {showPageModal && (
        <PageListModal
          pageList={pageList}
          onClose={() => setShowPageModal(false)}
        />
      )}

      {havePage ? (
        <S.CanvasWrapper ref={pageRef} onMouseDown={handleClickPage}>
          <input type="file" alt="what" onChange={onUploadFile} />
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
      ) : (
        <S.NoCanvas>
          <Text size={30} weight={1000} color={TEXT.disabled}>
            다일리 또는 페이지를 만들어주세요
          </Text>
        </S.NoCanvas>
      )}
      <S.SideWrapper>
        <S.ToolWrapper>
          {DECORATE_TOOLS.map(({ icon, type }, index) => {
            const onSelect = (t) => {
              if (newDecorateComponent) {
                completeCreateNewDecorateComponent();
              }
              if (canEditDecorateComponent) {
                completeModifyDecorateComponent();
                setTarget(null);
                setCanEditDecorateComponent(null);
              }
              setSelectedTool(selectedTool === t ? null : t);
              if (t === DECORATE_TYPE.MOVING) {
                setEditMode(EDIT_MODE.COMMON_PROPERTY);
              } else {
                setEditMode(EDIT_MODE.TYPE_CONTENT);
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
          {PAGE_TOOLS.map(({ icon, type }, index) => {
            const onSelect = async (t) => {
              if (newDecorateComponent) {
                completeCreateNewDecorateComponent();
              }
              if (canEditDecorateComponent) {
                completeModifyDecorateComponent();
                setTarget(null);
                setCanEditDecorateComponent(null);
              }
              setSelectedTool(selectedTool === t ? null : t);
              setTimeout(() => {
                setSelectedTool(null);
              }, 150);
              if (t === 'add') {
                const response = await postPage(dailryId);
                setCurrentDailry({
                  ...currentDailry,
                  pageId: response.data.pageId,
                  pageNumber: response.data.pageNumber,
                });
              }
              if (t === 'download') {
                await handleDownloadClick();
              }
              if (t === 'save') {
                const formData = getPageFormData(updatedDecorateComponents);
                console.log(updatedDecorateComponents);
                await patchPage(pageIds[pageNumber - 1], formData);
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
            {pageNumber}
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
