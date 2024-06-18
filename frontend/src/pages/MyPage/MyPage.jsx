import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toastify } from '../../utils/toastify';
import * as S from './MyPage.styled';
import Button from '../../components/common/Button/Button';
import Text from '../../components/common/Text/Text';
import {
  getMember,
  postEmailVerificationRequest,
  postEmailVerificationConfirm,
  patchNickname,
  patchPassword,
  postWithdrawl,
} from '../../apis/memberApi';
import { MENU, TEXT } from '../../styles/color';
import { PATH_NAME } from '../../constants/routes';
import { LikeIcon } from '../CommunityPage/CommunityPage.styled';

const MyPage = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState();
  const [nickname, setNickname] = useState();
  const [email, setEmail] = useState();
  const [presentPassword, setPresentPassword] = useState('');
  const [updatePassword, setUpdatePassword] = useState('');
  const [certificationNumber, setCertificationNumber] = useState();
  const [editingNickname, setEditingNickname] = useState(false);
  const [editingEmail, setEditingEmail] = useState(false);
  const [editingPassword, setEditingPassword] = useState(false);
  const [verification, setVerification] = useState(false);

  useEffect(() => {
    (async () => {
      const response = await getMember();
      setUsername(await response.data.username);
      setNickname(await response.data.nickname);
      setEmail(await response.data.email);
    })();
  }, []);

  const handleChangeNicknameClick = () => {
    setEditingNickname(true);
  };

  const handleConfirmNicknameClick = async () => {
    const response = await patchNickname(nickname);
    if (response.status === 200) {
      setEditingNickname(false);
      return;
    }
    if (response.status === 409) {
      toastify('이미 존재하는 닉네임입니다');
    }
  };

  const handleNicknameChange = (e) => {
    setNickname(e.target.value);
  };

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

  const handleChangePasswordClick = () => {
    setEditingPassword(true);
  };

  const handlePresentPasswordChange = (e) => {
    setPresentPassword(e.target.value);
  };

  const handleUpdatePasswordChange = (e) => {
    setUpdatePassword(e.target.value);
  };

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();
    const response = await patchPassword({ updatePassword, presentPassword });
    if (response.status === 200) {
      setEditingPassword(false);
      return;
    }
    if (response.status === 401) {
      toastify('현재 비밀번호가 틀렸습니다');
    }
  };

  const handleWithdrawalClick = async () => {
    const response = await postWithdrawl();
    if (response.status === 200) {
      navigate(PATH_NAME.Home);
    }
  };

  return (
    <S.MyPageWrapper>
      <S.NameArea>
        <S.RowWrapper>
          {editingNickname ? (
            <input
              type="text"
              value={nickname}
              onChange={handleNicknameChange}
            />
          ) : (
            <Text size={24} color={TEXT.white} weight={700}>
              {nickname}
            </Text>
          )}
          <Button
            onClick={
              editingNickname
                ? handleConfirmNicknameClick
                : handleChangeNicknameClick
            }
            size={'sm'}
          >
            {editingNickname ? '확인' : '닉네임 변경'}
          </Button>
        </S.RowWrapper>
        <Text size={16} color={MENU.text} weight={700}>
          {username}
        </Text>
      </S.NameArea>
      <S.UserInfoBox>
        <S.FormWrapper>
          <S.RowWrapper>
            <S.LabelWrapper>이메일</S.LabelWrapper>
            {editingEmail ? (
              <input type="text" value={email} onChange={handleEmailChange} />
            ) : (
              <div>{email}</div>
            )}
            {editingEmail ? (
              <Button onClick={handleVerificationRequestClick} size={'sm'}>
                인증번호 전송
              </Button>
            ) : (
              <Button onClick={handleChangeEmailClick} size={'sm'}>
                {email ? '이메일 변경' : '이메일 등록'}
              </Button>
            )}
          </S.RowWrapper>
          {verification && (
            <S.RowWrapper>
              <S.LabelWrapper>이메일 인증</S.LabelWrapper>
              <input
                type="text"
                value={certificationNumber}
                onChange={handleCertificationNumberChange}
              />
              <Button onClick={handleVerificationConfirmClick} size={'sm'}>
                인증번호 확인
              </Button>
            </S.RowWrapper>
          )}
        </S.FormWrapper>
        {editingPassword ? (
          <S.FormWrapper onSubmit={handlePasswordSubmit}>
            <S.RowWrapper>
              <S.LabelWrapper>현재 비밀번호</S.LabelWrapper>
              <input
                type="password"
                value={presentPassword}
                onChange={handlePresentPasswordChange}
              />
            </S.RowWrapper>
            <S.RowWrapper>
              <S.LabelWrapper>새 비밀번호</S.LabelWrapper>
              <input
                type="password"
                value={updatePassword}
                onChange={handleUpdatePasswordChange}
              />
              <Button size={'sm'} type={'submit'}>
                확인
              </Button>
            </S.RowWrapper>
          </S.FormWrapper>
        ) : (
          <S.RowWrapper>
            <S.LabelWrapper>비밀번호</S.LabelWrapper>
            <div>********</div>
            <Button onClick={handleChangePasswordClick} size={'sm'}>
              비밀번호 변경
            </Button>
          </S.RowWrapper>
        )}
      </S.UserInfoBox>
      <S.ActivityNameWrapper>
        <S.ActivityNameButton current={true}>
          내 커뮤니티 글 보기
        </S.ActivityNameButton>
        <S.ActivityNameButton current={false}>
          내가 단 댓글 보기
        </S.ActivityNameButton>
      </S.ActivityNameWrapper>
      <S.UserActivityWrapper>
        <S.UserPostsArea>
          <S.PostImageWrapper
            src={`
https://data.da-ily.site/post/2024-06-15/59/f8a15bf1-4b5e-40c3-8edf-f3eb59ec5f3e.png`}
          />
          <S.PostContentWrapper>
            <Text size={12}>2024-06-15 02:25:03</Text>
            <Text size={16}>버그수집가님의 글 훔치기~</Text>
          </S.PostContentWrapper>
          <S.LikeWrapper>
            <LikeIcon liked={false} />
            <Text>12</Text>
          </S.LikeWrapper>
        </S.UserPostsArea>
        <S.UserPostsArea>
          <S.PostImageWrapper
            src={`
https://data.da-ily.site/post/2024-06-15/59/f8a15bf1-4b5e-40c3-8edf-f3eb59ec5f3e.png`}
          />
          <S.PostContentWrapper>
            <Text size={12}>2024-06-15 02:25:03</Text>
            <Text size={16}>버그수집가님의 글 훔치기~</Text>
          </S.PostContentWrapper>
          <S.LikeWrapper>
            <LikeIcon liked={false} />
            <Text>12</Text>
          </S.LikeWrapper>
        </S.UserPostsArea>
        <S.UserCommentArea>
          <Text size={12}>2024-06-15 02:25:03</Text>
          <Text size={16}>버그수집가님의 글 훔치기~</Text>
        </S.UserCommentArea>
        <S.UserCommentArea>
          <Text size={12}>2024-06-15 02:25:03</Text>
          <Text size={16}>버그수집가님의 글 훔치기~</Text>
        </S.UserCommentArea>
      </S.UserActivityWrapper>
      <S.PageButtonWrapper>
        <S.PageButton>1</S.PageButton>
        <S.PageButton>2</S.PageButton>
        <S.PageButton>3</S.PageButton>
      </S.PageButtonWrapper>
      <S.WithdrawalWrapper>
        <S.WithdrawalButton onClick={handleWithdrawalClick}>
          회원탈퇴
        </S.WithdrawalButton>
      </S.WithdrawalWrapper>
    </S.MyPageWrapper>
  );
};

export default MyPage;
