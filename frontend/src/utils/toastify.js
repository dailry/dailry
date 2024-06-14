import { toast, Zoom } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const toastify = (
  message,
  options = {
    position: 'bottom-right',
    autoClose: 300,
    hideProgressBar: true,
    closeOnClick: true,
    transition: Zoom,
  },
) => {
  toast(message, options);
};
