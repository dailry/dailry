import customAxios from './customAxios';

export const postLogin = async (memberInfo) => {
  try {
    const { username, password } = memberInfo;
    return await customAxios.post('/login', { username, password });
  } catch (e) {
    return e.response.data;
  }
};

export const postLogout = async () => {
  try {
    return await customAxios.post('/logout');
  } catch (e) {
    return e.response.data;
  }
};

export const postAccessToken = async () => {
  try {
    return await customAxios.post('/token');
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};
