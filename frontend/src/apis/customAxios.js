import axios from 'axios';

const customAxios = axios.create({
  baseURL:
    process.env.NODE_ENV === 'production'
      ? 'https://api.dailry.co.kr/api/'
      : 'https://api.da-ily.site/api/',
  headers: {
    Authorization: '',
  },
  withCredentials: true,
});

customAxios.defaults.headers.post['Content-Type'] =
  'application/json;charset=UTF-8';

export default customAxios;
