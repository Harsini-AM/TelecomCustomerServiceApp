import axios from "axios";
import { toast } from "react-toastify";
import emailjs from 'emailjs-com';
export  const deleteRepresentative = (id) => {
    axios.delete(`http://localhost:8086/employee/deleteRepresentative/${id}`)
        .then(response => {
            toast.success('deletion successful')
        })
        .catch(error => {
          toast.error('Deletion failed')
        });
};
export  const handleDelete = async (managerId) => {
    console.log(managerId);
    try {
        await axios.delete(`http://localhost:8086/employee/deleteManager/${managerId}`);
        toast.success('Successful deletion of manager');
    } catch (error) {
        if (error.response && error.response.data) {
            toast.error(`Deletion failed: ${error.response.data}`);
        } else {
            toast.error('Deletion failed: An unknown error occurred.');
        }
    }
};
export const sendEmail = (username, password) => {
  emailjs.send("service_1vpzcfm", "template_604oi6k", {
      username: username,
      password: password,
  }, "Xbbcaz1v6pvhKiKnM")
      .then((response) => {
          console.log('Email sent successfully!', response.status, response.text);
      })
      .catch((error) => {
          console.error('Failed to send email:', error);
      });
};