import customAxios from './customAxios';

const joinMember = async (memberInformation) => {
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

const getMember = async () => {
  try {
    const member = await customAxios.get('/member');

    return member;
  } catch (e) {
    console.error(e);

    return e.response.data;
  }
};

export { joinMember, getMember };
