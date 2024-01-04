import axios from 'axios';

const customAxios = axios.create({
  baseURL: 'http://3.39.121.23/api',
  headers: {
    Authorization: '',
  },
});

customAxios.defaults.headers.post['Content-Type'] =
  'application/json;charset=UTF-8';

export default customAxios;
