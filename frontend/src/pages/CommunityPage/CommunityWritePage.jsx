import * as S from './CommunityPage.styled';
import Button from '../../components/common/Button/Button';
import Text from '../../components/common/Text/Text';

const CommunityWritePage = () => {
  return (
    <S.CommunityWrapper>
      <S.DailryWrapper></S.DailryWrapper>
      <S.WriteContentArea></S.WriteContentArea>
      <S.WriteTagArea>
        <Text>태그</Text>
        <button>+</button>
      </S.WriteTagArea>
      <Button onClick={() => {}}>공유하기</Button>
    </S.CommunityWrapper>
  );
};

export default CommunityWritePage;
