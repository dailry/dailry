import PropTypes from 'prop-types';
import { useModalContext } from '../../../hooks/useModalContext';
import * as S from './Hamburger.styled';
import { deleteDailry } from '../../../apis/dailryApi';

const DailryHamburger = (props) => {
  const {
    dailryId,
    setEditingDailry,
    anchor = 'left',
    setDeletedDailryId,
  } = props;
  const { setModalType } = useModalContext();

  const handleDeleteClick = async (e) => {
    e.stopPropagation();
    const res = await deleteDailry(dailryId);

    if (res.data.successful) {
      setEditingDailry(Math.random());
      setDeletedDailryId(dailryId);
    }
  };

  const handlePatchClick = (e) => {
    e.stopPropagation();
    setEditingDailry(dailryId);
  };

  const handleShareClick = (e) => {
    e.stopPropagation();
    setModalType('share');
  };

  return (
    <S.HamburgerMenu anchor={anchor}>
      <S.HamburgerItem onClick={handleDeleteClick}>삭제하기</S.HamburgerItem>
      <S.HamburgerItem onClick={handlePatchClick}>이름 바꾸기</S.HamburgerItem>
      <S.HamburgerItem onClick={handleShareClick}>공유하기</S.HamburgerItem>
    </S.HamburgerMenu>
  );
};

DailryHamburger.propTypes = {
  dailryId: PropTypes.number.isRequired,
  setEditingDailry: PropTypes.func.isRequired,
  anchor: 'right' || 'left',
  setDeletedDailryId: PropTypes.func,
};

export default DailryHamburger;
