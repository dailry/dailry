import axios from 'axios';

const customAxios = axios.create({
  baseURL: 'https://api.da-ily.site/api/',
  headers: {
    Authorization: '',
  },
});

customAxios.defaults.headers.post['Content-Type'] =
  'application/json;charset=UTF-8';

export default customAxios;
