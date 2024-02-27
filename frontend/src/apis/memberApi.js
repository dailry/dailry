import customAxios from './customAxios';

export const postJoinMember = async (memberInformation) => {
  try {
    const { username, password, nickname } = memberInformation;

    const joinedMember = await customAxios.post('/members/join', {
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
    const member = await customAxios.get('/members');

    return member;
  } catch (e) {
    console.error(e);

    return e.response.data;
  }
};

export const getCheckUserName = async (userName) => {
  try {
    const isUserNameDuplicated = await customAxios.get(
      `/members/check-username?username=${userName}`,
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
      `/members/check-nickname?nickname=${nickName}`,
    );

    return isNickNameDuplicated.data.duplicated;
  } catch (e) {
    console.error(e);

    alert('아이디 중복 검사가 실패하였습니다.');

    return e.response.data;
  }
};

export const postRecoverUsername = async (email) => {
  try {
    return await customAxios.post(`/members/recover-username`, { email });
  } catch (e) {
    return e.response.data;
  }
};

export const postRecoverPassword = async (userInfo) => {
  try {
    const { username, email } = userInfo;
    return await customAxios.post(`/members/recover-password`, {
      username,
      email,
    });
  } catch (e) {
    return e.response.data;
  }
};

export const patchRecoverPassword = async (passwordInfo) => {
  try {
    const { passwordResetToken, password } = passwordInfo;
    return await customAxios.patch(`/members/recover-password`, {
      passwordResetToken,
      password,
    });
  } catch (e) {
    return e.response.data;
  }
};

export const postWithdrawl = async () => {
  try {
    return await customAxios.post('/members/withdrawal');
  } catch (e) {
    return e.response.data;
  }
};
