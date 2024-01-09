// Da-ily 회원, 비회원, 다일리 있을때, 없을때를 조건문으로 나눠서 렌더링
import * as S from './DailyPage.styled';

const DailyPage = () => {
  return (
    <S.FlexWrapper>
      <S.CanvasWrapper>내용이 없어서 이러는거야???</S.CanvasWrapper>
      <S.ToolWrapper>
        <div>tool1크기</div>
        <div>tool2</div>
      </S.ToolWrapper>
    </S.FlexWrapper>
  );
};

export default DailyPage;
