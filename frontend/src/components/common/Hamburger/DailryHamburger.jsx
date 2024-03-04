import PropTypes from 'prop-types';
import * as S from './Hamburger.styled';
import { deleteDailry } from '../../../apis/dailryApi';

const DailryHamburger = (props) => {
  const { dailryId, setEditingDailry, anchor = 'left' } = props;
  const handleDeleteClick = async (e) => {
    e.stopPropagation();
    await deleteDailry(dailryId);
    setEditingDailry(Math.random());
  };

  const handlePatchClick = (e) => {
    e.stopPropagation();
    setEditingDailry(dailryId);
  };

  return (
    <S.HamburgerMenu anchor={anchor}>
      <S.HamburgerItem onClick={handleDeleteClick}>삭제하기</S.HamburgerItem>
      <S.HamburgerItem onClick={handlePatchClick}>이름 바꾸기</S.HamburgerItem>
      <S.HamburgerItem>페이지 다운로드</S.HamburgerItem>
    </S.HamburgerMenu>
  );
};

DailryHamburger.propTypes = {
  dailryId: PropTypes.number.isRequired,
  setEditingDailry: PropTypes.func.isRequired,
  anchor: 'right' || 'left',
};

export default DailryHamburger;
