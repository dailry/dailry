import { useEffect, useState } from 'react';
import * as S from './MyPage.styled';
import Button from '../../components/common/Button/Button';
import {
  getMember,
  postEmailVerificationRequest,
  postEmailVerificationConfirm,
} from '../../apis/memberApi';

const MyPage = () => {
  const [username, setUsername] = useState();
  const [nickname, setNickname] = useState();
  const [email, setEmail] = useState();
  const [certificationNumber, setCertificationNumber] = useState();
  const [editingEmail, setEditingEmail] = useState(false);
  const [verification, setVerification] = useState(false);

  useEffect(() => {
    (async () => {
      const response = await getMember();
      setUsername(await response.data.username);
      setNickname(await response.data.nickname);
      setEmail(await response.data.email);
    })();
  }, []);

  const handleChangeEmailClick = () => {
    setEditingEmail(true);
  };

  const handleVerificationRequestClick = async () => {
    const response = await postEmailVerificationRequest(email);
    if (response.status === 200) {
      setEditingEmail(false);
      setVerification(true);
    }
  };

  const handleVerificationConfirmClick = async () => {
    const response = await postEmailVerificationConfirm({
      email,
      certificationNumber,
    });
    if (response.status === 200) {
      setVerification(false);
    }
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handleCertificationNumberChange = (e) => {
    setCertificationNumber(e.target.value);
  };

  return (
    <S.MyPageWrapper>
      <S.RowWrapper>
        <div>닉네임</div>
        <div>{nickname}</div>
      </S.RowWrapper>
      <S.RowWrapper>
        <div>아이디</div>
        <div>{username}</div>
      </S.RowWrapper>
      <S.RowWrapper>
        <div>이메일</div>
        {editingEmail ? (
          <input type="text" value={email} onChange={handleEmailChange} />
        ) : (
          <div>{email}</div>
        )}
        <div>
          {editingEmail ? (
            <Button onClick={handleVerificationRequestClick} size={'sm'}>
              인증번호 전송
            </Button>
          ) : (
            <Button onClick={handleChangeEmailClick} size={'sm'}>
              {email ? '이메일 변경' : '이메일 등록'}
            </Button>
          )}
        </div>
      </S.RowWrapper>
      {verification && (
        <S.RowWrapper>
          <input
            type="text"
            value={certificationNumber}
            onChange={handleCertificationNumberChange}
          />
          <Button onClick={handleVerificationConfirmClick}>
            인증번호 확인
          </Button>
        </S.RowWrapper>
      )}
      <S.RowWrapper>비밀번호 (********) (비밀번호 변경)</S.RowWrapper>
    </S.MyPageWrapper>
  );
};

export default MyPage;
