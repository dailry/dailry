// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import { useState, useRef, useEffect } from 'react';
import html2canvas from 'html2canvas';
import saveAs from 'file-saver';
import { toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';
import * as S from './DailryPage.styled';
import Text from '../../components/common/Text/Text';
import ToolButton from '../../components/da-ily/ToolButton/ToolButton';
import {
  DECORATE_TOOLS,
  DECORATE_TOOLS_TOOLTIP,
  PAGE_TOOLS,
  PAGE_TOOLS_TOOLTIP,
} from '../../constants/toolbar';
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
import { useModalContext } from '../../hooks/useModalContext';
import { DecorateComponentDeleteButton } from '../../components/decorate/DeleteButton/DeleteButton.styled';
import { PATH_NAME } from '../../constants/routes';
import Tooltip from '../../components/common/Tooltip/Tooltip';

console.log(process.env.API_URI);

const DailryPage = () => {
  const pageRef = useRef(null);
  const moveableRef = useRef([]);
  const navigate = useNavigate();

  const [target, setTarget] = useState(null);

  const [selectedTool, setSelectedTool] = useState(null);
  const [pageList, setPageList] = useState([]);
  const [havePage, setHavePage] = useState(true);

  const { currentDailry, setCurrentDailry } = useDailryContext();
  const { modalType, setModalType, closeModal } = useModalContext();

  const {
    setUpdatedDecorateComponents,
    updatedDecorateComponents,
    addUpdatedDecorateComponent,
    modifyUpdatedDecorateComponent,
  } = useUpdatedDecorateComponents();

  const { appendPageDataToFormData, formData } = usePageData(
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

  const [deletedDecorateComponentIds, setDeletedDecorateComponentIds] =
    useState([]);

  const deleteDecorateComponent = (id) => {
    if (!deletedDecorateComponentIds.some((d) => d.id === id)) {
      setDeletedDecorateComponentIds((prev) => [...prev, id]);
    }

    setDecorateComponents((prev) => prev.filter((p) => p.id !== id));

    setTarget(null);
  };

  useEffect(() => {
    (async () => {
      if (dailryId) {
        const { status, data } = await getPages(dailryId);

        if (status === 200 && data.pages.length !== 0) {
          const { pages } = data;
          console.log(pages);
          setPageList(pages);
          const pageIdList = pages.map((page) => page.pageId);
          setCurrentDailry({
            ...currentDailry,
            pageIds: pageIdList,
            pageNumber: pageNumber ?? (pageIds.length === 0 ? 1 : null),
          });

          return setHavePage(true);
        }
      }

      return setHavePage(false);
    })();
  }, [dailryId, pageNumber, modalType]);

  useEffect(() => {
    (async () => {
      if (dailryId) {
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

  const patchPageData = () => {
    setTarget(null);

    setTimeout(async () => {
      const pageImg = await html2canvas(pageRef.current);

      pageImg.toBlob(async (pageImageBlob) => {
        appendPageDataToFormData(
          pageImageBlob,
          updatedDecorateComponents,
          deletedDecorateComponentIds,
        );
        await patchPage(pageIds[pageNumber - 1], formData);
      });

      setUpdatedDecorateComponents([]);
    }, 100);
  };

  const handleLeftArrowClick = async () => {
    if (pageNumber === 1) {
      toastify('첫 번째 페이지입니다');
      return;
    }
    if (canEditDecorateComponent) {
      completeModifyDecorateComponent();
      setTarget(null);

      setCanEditDecorateComponent(null);

      return;
    }

    if (newDecorateComponent) {
      completeCreateNewDecorateComponent();
      return;
    }

    if (
      updatedDecorateComponents.length > 0 &&
      window.confirm(
        '저장 하지 않은 꾸미기 컴포넌트가 존재합니다. 저장하시겠습니까?',
      )
    ) {
      patchPageData();
    }

    setUpdatedDecorateComponents([]);

    setCurrentDailry({ ...currentDailry, pageNumber: pageNumber - 1 });
  };

  const handleRightArrowClick = async () => {
    if (pageList.length === pageNumber) {
      toastify('마지막 페이지입니다');
      return;
    }

    if (canEditDecorateComponent) {
      completeModifyDecorateComponent();
      setTarget(null);

      setCanEditDecorateComponent(null);
      return;
    }

    if (newDecorateComponent) {
      completeCreateNewDecorateComponent();
      return;
    }

    if (
      updatedDecorateComponents.length > 0 &&
      window.confirm(
        '저장 하지 않은 꾸미기 컴포넌트가 존재합니다. 저장하시겠습니까?',
      )
    ) {
      patchPageData();
    }
    setUpdatedDecorateComponents([]);

    setCurrentDailry({ ...currentDailry, pageNumber: pageNumber + 1 });
  };

  const handleDownloadClick = async () => {
    try {
      const pageImg = await html2canvas(pageRef.current);
      pageImg.toBlob((pageImageBlob) => {
        if (pageImageBlob !== null) {
          saveAs(pageImageBlob, `dailry${dailryId}_${pageId}.png`);
        }
      });
    } catch (e) {
      console.error('이미지 변환 오류', e);
    }
  };

  const handleClickPage = (e) => {
    if (
      selectedTool === null ||
      (selectedTool === DECORATE_TYPE.MOVING && !canEditDecorateComponent)
    ) {
      return;
    }

    if (canEditDecorateComponent) {
      completeModifyDecorateComponent();
      setTarget(null);

      setCanEditDecorateComponent(null);
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

    if (selectedTool === DECORATE_TYPE.MOVING) {
      console.log('hell yeah');
      setTarget(index + 1);
    }

    if (
      canEditDecorateComponent &&
      canEditDecorateComponent.id !== element.id
    ) {
      completeModifyDecorateComponent();
      setTarget(null);
      setCanEditDecorateComponent(null);
      return;
    }

    if (newDecorateComponent) {
      completeCreateNewDecorateComponent();

      return;
    }

    if (
      editMode === EDIT_MODE.COMMON_PROPERTY ||
      (editMode === EDIT_MODE.TYPE_CONTENT && selectedTool === element.type)
    ) {
      setCanEditDecorateComponent(element);
    }

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

  const handleModalSelect = {
    select: (page) => {
      setCurrentDailry({
        ...currentDailry,
        pageId: page.pageId,
        pageNumber: page.pageNumber,
      });
    },
    download: () => {},
    share: (page) => {
      navigate(`${PATH_NAME.CommunityWrite}?pageImage=${page.thumbnail}`);
    },
  };

  return (
    <S.FlexWrapper>
      {modalType && (
        <PageListModal
          pageList={pageList}
          onClose={closeModal}
          onSelect={handleModalSelect[modalType]}
        />
      )}

      {havePage ? (
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
                {(target === index + 1 ||
                  canEditDecorateComponent?.id === element.id) && (
                  <DecorateComponentDeleteButton
                    onClick={() => {
                      deleteDecorateComponent(element.id);
                    }}
                  >
                    삭제
                  </DecorateComponentDeleteButton>
                )}

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
              <Tooltip key={index} text={DECORATE_TOOLS_TOOLTIP[type]}>
                <ToolButton
                  icon={icon}
                  selected={selectedTool === type}
                  onSelect={() => onSelect(type)}
                />
              </Tooltip>
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
                if (canEditDecorateComponent) {
                  completeModifyDecorateComponent();
                  setTarget(null);

                  setCanEditDecorateComponent(null);
                  return;
                }

                if (newDecorateComponent) {
                  completeCreateNewDecorateComponent();
                  return;
                }
                if (
                  updatedDecorateComponents.length > 0 &&
                  window.confirm(
                    '저장 하지 않은 꾸미기 컴포넌트가 존재합니다. 저장하시겠습니까?',
                  )
                ) {
                  patchPageData();
                }
                setUpdatedDecorateComponents([]);
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
                patchPageData();
              }
              if (t === 'share') {
                const currentPageThumbnail = pageList.find(
                  (page) => page.pageNumber === currentDailry.pageNumber,
                ).thumbnail;

                navigate(
                  `${PATH_NAME.CommunityWrite}?pageImage=${currentPageThumbnail}`,
                );
              }
            };
            return (
              <Tooltip key={index} text={PAGE_TOOLS_TOOLTIP[type]}>
                <ToolButton
                  key={index}
                  icon={icon}
                  selected={selectedTool === type}
                  onSelect={() => onSelect(type)}
                />
              </Tooltip>
            );
          })}
        </S.ToolWrapper>
        <S.ArrowWrapper>
          <S.ArrowButton direction={'left'} onClick={handleLeftArrowClick}>
            <LeftArrowIcon />
          </S.ArrowButton>
          <S.NumberButton onClick={() => setModalType('select')}>
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
