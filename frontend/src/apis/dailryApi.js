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
    return await customAxios.post('/dailry', {
      title,
    });
  } catch (e) {
    console.error(e);

    return e.response.data;
  }
};
