// 이 컴포넌트가 하는 일 : 이전/다음 페이지 넘기기, 프리뷰 보여주기 => 현재 다일리의 정보 필요
import { toast, Zoom } from 'react-toastify';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';
import PageListModal from '../PageListModal/PageListModal';
import { LeftArrowIcon, RightArrowIcon } from '../../../../assets/svg';
import * as S from './PageNavigator.styled';
import 'react-toastify/dist/ReactToastify.css';
import { useModalContext } from '../../../../hooks/useModalContext';
import { getPreviewPages } from '../../../../apis/dailryApi';
import { PATH_NAME } from '../../../../constants/routes';

const PageNavigator = (props) => {
  const { dailryId, pageNumber } = props;
  const [pages, setPages] = useState([]);
  const navigate = useNavigate();
  const { modalType, setModalType, closeModal } = useModalContext();

  useEffect(() => {
    (async () => {
      const { status, data } = await getPreviewPages(dailryId);
      if (status === 200 && data.pages.length !== 0) {
        setPages(data.pages);
      }
    })();
  }, [dailryId]);

  const handleModalSelect = {
    select: (page) => {
      navigate(`${PATH_NAME.Dailry}/${dailryId}/${page.pageNumber}`);
    },
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

  const handleLeftArrowClick = async () => {
    if (pageNumber === 1) {
      toastify('첫 번째 페이지입니다');
      return;
    }
    navigate(`${PATH_NAME.Dailry}/${dailryId}/${pageNumber - 1}`);
  };

  const handleRightArrowClick = async () => {
    if (pages.length === pageNumber) {
      toastify('마지막 페이지입니다');
      return;
    }
    navigate(`${PATH_NAME.Dailry}/${dailryId}/${pageNumber + 1}`);
  };

  return (
    <S.ArrowWrapper>
      {modalType && (
        <PageListModal
          pageList={pages}
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

PageNavigator.propTypes = {
  dailryId: PropTypes.string.isRequired,
  pageNumber: PropTypes.number.isRequired,
};

export default PageNavigator;
