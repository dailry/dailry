import axios from 'axios';
import { DEV_API_URI } from '../constants/api';

const customAxios = axios.create({
  baseURL:
    process.env.NODE_ENV === 'production' ? process.env.API_URI : DEV_API_URI,
  withCredentials: true,
});

customAxios.defaults.headers.post['Content-Type'] =
  'application/json;charset=UTF-8';

export default customAxios;
