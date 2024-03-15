import customAxios from './customAxios';

export const postPosts = async (postData) => {
  try {
    return await customAxios.post('/posts', postData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const getPosts = async (pageInfo) => {
  try {
    const { page, size } = pageInfo;
    return await customAxios.get(`/posts?page=${page}&size=${size}`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const deletePosts = async (postId) => {
  try {
    return await customAxios.delete(`posts/${postId}`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};
