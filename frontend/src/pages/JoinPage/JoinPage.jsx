// Join, JoinDone
import { useState } from 'react';
import JoinForm from './JoinForm';
import JoinDone from './JoinDone';
import * as S from './JoinPage.styled';
import Text from '../../components/common/Text/Text';

const JoinPage = () => {
  const [joinCompletedMember, setJoinCompletedMember] = useState(null);

  return (
    <S.JoinWrapper>
      <Text as={'h1'} size={24}>
        회원가입
      </Text>
      {joinCompletedMember ? (
        <JoinDone nickname={joinCompletedMember.nickname} />
      ) : (
        <JoinForm setJoinCompletedMember={setJoinCompletedMember} />
      )}
    </S.JoinWrapper>
  );
};

export default JoinPage;
