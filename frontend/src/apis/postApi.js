import customAxios from './customAxios';

export const getPosts = async (pageNumber) => {
  try {
    const { content, hashtags } = postData;
    return await customAxios.post('/posts', { title });
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};
