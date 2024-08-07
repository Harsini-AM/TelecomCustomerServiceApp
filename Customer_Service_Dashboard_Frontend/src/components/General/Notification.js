import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
 
let isToastOpen = false;
 
export const notify = (message, options = {}) => {
  if (!isToastOpen) {
    toast.info(message, {
      ...options,
      autoClose: 2000,
      onClose: () => {
        isToastOpen = false;
      }
    });
    isToastOpen = true;
  }
};