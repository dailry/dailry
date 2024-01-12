import customAxios from './customAxios';

export const postJoinMember = async (memberInformation) => {
  try {
    const { username, password, nickname } = memberInformation;

    const joinedMember = await customAxios.post('/member/join', {
      username,
      password,
      nickname,
    });

    return joinedMember;
  } catch (e) {
    console.error(e);

    return e.response.data;
  }
};

export const getMember = async () => {
  try {
    const member = await customAxios.get('/member');

    return member;
  } catch (e) {
    console.error(e);

    return e.response.data;
  }
};

export const getCheckUserName = async (userName) => {
  try {
    const isUserNameDuplicated = await customAxios.get(
      `/member/check-username?username=${userName}`,
    );

    return isUserNameDuplicated.data.duplicated;
  } catch (e) {
    console.error(e);

    alert('아이디 중복 검사가 실패하였습니다.');

    return e.response.data;
  }
};

export const getCheckNickName = async (nickName) => {
  try {
    const isNickNameDuplicated = await customAxios.get(
      `/member/check-nickname?nickname=${nickName}`,
    );

    return isNickNameDuplicated.data.duplicated;
  } catch (e) {
    console.error(e);

    alert('아이디 중복 검사가 실패하였습니다.');

    return e.response.data;
  }
};
