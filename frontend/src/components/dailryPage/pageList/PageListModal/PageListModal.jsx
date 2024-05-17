import PropTypes from 'prop-types';
import { useState } from 'react';
import * as S from './PageListModal.styled';
import { useDailryContext } from '../../../../hooks/useDailryContext';
import { deletePage } from '../../../../apis/dailryApi';

const PageListModal = (props) => {
  const { onClose, pageList, onSelect } = props;
  const [canToggle, setCanToggle] = useState(false);
  const [selectedPages, setSelectedPages] = useState(
    Array.from({ length: pageList.length + 1 }, () => 0),
  );

  const { currentDailry, setCurrentDailry } = useDailryContext();

  const handleDeleteClick = () => {
    selectedPages.forEach(async (pageId) => {
      if (pageId !== 0) {
        await deletePage(pageId);
      }
    });
    setCurrentDailry({ ...currentDailry, pageNumber: null });
    onClose();
  };

  const togglePage = ({ pageId, pageNumber }) => {
    const tmpSelectedPages = [...selectedPages];
    if (tmpSelectedPages[pageNumber] === 0) {
      tmpSelectedPages[pageNumber] = pageId;
      setSelectedPages(tmpSelectedPages);
      return;
    }
    tmpSelectedPages[pageNumber] = 0;
    setSelectedPages(tmpSelectedPages);
  };

  const handleItemClick = (page) => {
    if (!canToggle) {
      onSelect(page);
      onClose();
      return;
    }
    togglePage(page);
  };

  return (
    <S.ModalBackground>
      <S.ModalWrapper>
        <S.TopArea>
          <button onClick={() => setCanToggle(!canToggle)}>
            {canToggle ? '선택취소' : '다중선택'}
          </button>
          {canToggle && <button onClick={handleDeleteClick}>선택삭제</button>}
          <button onClick={onClose}>닫기</button>
        </S.TopArea>
        <S.ItemArea>
          {pageList.map((page) => {
            const { pageNumber, thumbnail } = page;
            return (
              <S.ItemWrapper
                key={pageNumber}
                onClick={() => handleItemClick(page)}
                selected={canToggle && !!selectedPages[pageNumber]}
              >
                <S.PageNumberArea>{pageNumber}</S.PageNumberArea>
                <S.ThumbnailArea src={thumbnail} />
              </S.ItemWrapper>
            );
          })}
        </S.ItemArea>
      </S.ModalWrapper>
    </S.ModalBackground>
  );
};

PageListModal.propTypes = {
  onClose: PropTypes.func.isRequired,
  pageList: PropTypes.object,
  deletable: PropTypes.bool,
  onSelect: PropTypes.func,
};
export default PageListModal;
