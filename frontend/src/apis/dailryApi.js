import customAxios from './customAxios';

export const getDailry = async (dailryId) => {
  try {
    return dailryId
      ? await customAxios.get(`/dailry/${dailryId}`)
      : await customAxios.get('/dailry');
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const postDailry = async (dailryData) => {
  try {
    const { title } = dailryData;
    return await customAxios.post('/dailry', { title });
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const deleteDailry = async (dailryId) => {
  try {
    return await customAxios.delete(`/dailry/${dailryId}`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const patchDailry = async (dailryData) => {
  try {
    const { title, dailryId } = dailryData;
    return await customAxios.patch(`/dailry/${dailryId}`, { title });
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const postPage = async (dailryId) => {
  try {
    return await customAxios.post(`dailry/${dailryId}/pages`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const getPages = async (dailryId) => {
  try {
    return await customAxios.get(`/dailry/${dailryId}/pages`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};

export const deletePage = async (pageId) => {
  try {
    return await customAxios.delete(`/dailry/pages/${pageId}`);
  } catch (e) {
    console.error(e);
    return e.response.data;
  }
};
