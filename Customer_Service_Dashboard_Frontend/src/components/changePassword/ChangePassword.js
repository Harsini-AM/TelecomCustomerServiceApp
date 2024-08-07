import React, { useState } from 'react';
import axios from 'axios';
import { notify } from '../General/Notification';
import './ChangePassword.css';
 
const ChangePassword = ({ userName, onPasswordChangeCompleted, role }) => {
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
 
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === 'currentPassword') {
      setCurrentPassword(value);
    } else if (name === 'newPassword') {
      setNewPassword(value);
    } else if (name === 'confirmPassword') {
      setConfirmPassword(value);
    }
  };
 
  const validatePassword = (password) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{10,}$/;
    return regex.test(password);
  };
 
  const handleSubmit = (e) => {
    e.preventDefault();
 
    if (newPassword !== confirmPassword) {
      setError("New Password and Confirm Password don't match");
      return;
    }
 
    if (!validatePassword(confirmPassword)) {
      setError("Confirm Password must be at least 10 characters long and include one special character, one uppercase letter, one lowercase letter, and one number");
      return;
    }
 
    if (!validatePassword(newPassword)) {
      setError("New Password must be at least 10 characters long and include one special character, one uppercase letter, one lowercase letter, and one number");
      return;
    }
 
    let url;
    if (userName.includes('@LIT.com') && (role === "manager" || role === "representative")) {
      url = `${process.env.REACT_APP_EMPLOYEE_CHANGEPASSWORD_URL}/${userName}?role=${role}`;
    } else {
      setError("Invalid userName domain");
      return;
    }
 
    axios.put(url, { currentPassword, newPassword })
      .then(response => {
        console.log(response.data);
        notify("Password Changed Successfully");
        onPasswordChangeCompleted();  // Notify the parent component
      })
      .catch(err => {
        notify("Password Change failed");
        console.log("Password change failed due to", err);
        setError("Password change failed: " + (err.response?.data || err.message));
      });
  };
 
  return (
    <div className="modal-password">
      <div className="modal-content-password">
        <span className="close-password" onClick={onPasswordChangeCompleted}>&times;</span>
        <h2>Change Password</h2>
        {error && <p className="error">{error}</p>}
        <form className="password-form" onSubmit={handleSubmit}>
          <label htmlFor='currentPassword'>Current Password</label>
          <input type="password" name="currentPassword" value={currentPassword} onChange={handleChange} required style={{color:'black'}} /><br />
          <label htmlFor='newPassword'>New Password</label>
          <input type="password" name="newPassword" value={newPassword} onChange={handleChange} required style={{color:'black'}}/><br />
          <label htmlFor='confirmPassword'>Confirm Password</label>
          <input type="password" name="confirmPassword" value={confirmPassword} onChange={handleChange} required style={{color:'black'}}/><br />
          <button type="submit">Change Password</button>
        </form>
      </div>
    </div>
  );
};
 
export default ChangePassword;