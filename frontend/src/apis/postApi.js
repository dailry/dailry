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

export const postEditedPosts = async (postId, postData) => {
  try {
    return await customAxios.post(`posts/${postId}/edit`, postData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const getPost = async (postId) => {
  try {
    return await customAxios.get(`/posts/${postId}`);
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

export const getHotPosts = async (pageInfo) => {
  try {
    const { page, size } = pageInfo;
    return await customAxios.get(`/hotPosts?page=${page}&size=${size}`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const getPostsByHashtags = async (pageInfo) => {
  try {
    const { hashtags, page, size } = pageInfo;
    return await customAxios.get(
      `/posts/search?hashtags=${hashtags}&page=${page}&size=${size}`,
    );
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

export const postLikes = async (postId) => {
  try {
    return await customAxios.post(`posts/${postId}/likes`);
  } catch (e) {
    console.error(e);
    return e.response;
  }
};

export const deleteLikes = async (postId) => {
  try {
    return await customAxios.delete(`posts/${postId}/likes`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const getLikes = async (postIds) => {
  const postIdsQuery = postIds.map((postId) => `postIds=${postId}`).join('&');
  try {
    return await customAxios.get(`posts/likes?${postIdsQuery}`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const getHotHashtags = async () => {
  try {
    return await customAxios.get('/posts/hotHashtags');
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};
