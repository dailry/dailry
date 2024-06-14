import { toast, Zoom } from 'react-toastify';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PageListModal from '../PageListModal/PageListModal';
import { LeftArrowIcon, RightArrowIcon } from '../../../../assets/svg';
import { useDailryContext } from '../../../../hooks/useDailryContext';
import * as S from './PageNavigator.styled';
import 'react-toastify/dist/ReactToastify.css';
import { useModalContext } from '../../../../hooks/useModalContext';
import { getPreviewPages } from '../../../../apis/dailryApi';
import { PATH_NAME } from '../../../../constants/routes';

const PageNavigator = () => {
  const navigate = useNavigate();
  const { currentDailry, setCurrentDailry } = useDailryContext();
  const [pageList, setPageList] = useState([]);
  const { modalType, setModalType, closeModal } = useModalContext();
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

  const toastify = (message) => {
    toast(message, {
      position: 'bottom-right',
      autoClose: 300,
      hideProgressBar: true,
      closeOnClick: true,
      transition: Zoom,
    });
  };
  const { dailryId, pageIds, pageNumber } = currentDailry;

  const handleLeftArrowClick = async () => {
    if (pageNumber === 1) {
      toastify('첫 번째 페이지입니다');
      return;
    }

    setCurrentDailry({ ...currentDailry, pageNumber: pageNumber - 1 });
  };

  const handleRightArrowClick = async () => {
    if (pageList.length === pageNumber) {
      toastify('마지막 페이지입니다');
      return;
    }

    setCurrentDailry({ ...currentDailry, pageNumber: pageNumber + 1 });
  };
  useEffect(() => {
    (async () => {
      const { status, data } = await getPreviewPages(dailryId);
      console.log(data);
      if (status === 200 && data.pages.length !== 0) {
        const { pages } = data;

        setPageList(pages);
        const pageIdList = pages.map((page) => page.pageId);
        setCurrentDailry({
          ...currentDailry,
          pageIds: pageIdList,
          pageNumber: pageNumber ?? (pageIds.length === 0 ? 1 : null),
        });
      }
    })();
  }, [dailryId, pageNumber]);

  return (
    <S.ArrowWrapper>
      {modalType && (
        <PageListModal
          pageList={pageList}
          onClose={closeModal}
          onSelect={handleModalSelect[modalType]}
        />
      )}
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
  );
};

export default PageNavigator;
