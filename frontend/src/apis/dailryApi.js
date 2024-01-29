import customAxios from './customAxios';

export const getDailry = async (dailryId) => {
  try {
    const dailry = dailryId
      ? await customAxios.get(`/dailry/${dailryId}`)
      : await customAxios.get('/dailry');
    return dailry;
  } catch (e) {
    console.error(e);

    return e.response.data;
  }
};

export const postDailry = async (dailryData) => {
  try {
    const { id, title } = dailryData;
    return await customAxios.post('/dailry', {
      id,
      title,
    });
  } catch (e) {
    console.error(e);

    return e.response.data;
  }
};
