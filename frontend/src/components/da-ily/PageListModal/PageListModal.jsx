import PropTypes from 'prop-types';
import { useState } from 'react';
import * as S from './PageListModal.styled';
import { useDailryContext } from '../../../hooks/useDailryContext';

const PageListModal = (props) => {
  const { onClose, pageList } = props;
  const [canSelect, setCanSelect] = useState(false);

  const { currentDailry, setCurrentDailry } = useDailryContext();

  const handleItemClick = ({ pageId, pageNumber }) => {
    if (!canSelect) {
      setCurrentDailry({
        dailryId: currentDailry.dailryId,
        pageId,
        pageNumber,
      });
      onClose();
    }
  };

  return (
    <S.ModalBackground>
      <S.ModalWrapper>
        <S.TopArea>
          <button onClick={() => setCanSelect(!canSelect)}>선택</button>
          <button onClick={onClose}>닫기</button>
        </S.TopArea>
        <S.ItemArea>
          {pageList.map((page) => {
            const { pageNumber, thumbnail } = page;
            return (
              <S.ItemWrapper
                key={pageNumber}
                onClick={() => handleItemClick(page)}
              >
                <S.PageNumberArea>{pageNumber}</S.PageNumberArea>
                <S.ThumbnailArea>{thumbnail}</S.ThumbnailArea>
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
};
export default PageListModal;
