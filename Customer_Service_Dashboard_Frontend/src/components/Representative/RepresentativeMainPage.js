import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useAuth } from '../../context/AuthContext';
import RepresentativeDashboard from './RepresentativeDashboard'
import ChangePassword from '../changePassword/ChangePassword';
import UserModal from '../General/UserModal';
import './RepresentativeFile.css';

 
const RepresentativeMainPage = () => {
  const { logout, isLoggedIn } = useAuth();
  const [userName, setUserName] = useState('');
  const [role,setRole]=useState()
  const [showChangePasswordButton, setShowChangePasswordButton] = useState(false);
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [userDetails, setUserDetails] = useState({});
  const [formData, setFormData] = useState({
    empId: '',
    userName: '',
    fName: '',
    lName: '',
    role: '',
    domain: '',
    managerId: '',
    state: '',
    city: '',
    phone_no:'',
  });
  const navigate = useNavigate();
 
  useEffect(() => {
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const parsedAuthData = JSON.parse(authData);
      console.log("Parsed auth data:", parsedAuthData);
      const usernameFromStorage = parsedAuthData.userName;
      const passwordChangedFromStorage = parsedAuthData.passwordChanged;
      const role=parsedAuthData.role;
      setRole(role);
      setShowChangePasswordButton(!passwordChangedFromStorage);
      if (usernameFromStorage) {
        setUserName(usernameFromStorage);
        axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/${usernameFromStorage}?role=${role}`)
          .then(response => {
            const data = response.data;
            console.log("Before Update", data);
            setUserDetails(data);
            setFormData({
              empId: data.empId ?? '',
              userName: data.userName ?? '',
              fName: data.fName ?? '',
              lName: data.lName ?? '',
              domain: data.domain ?? '',
              managerId: data.managerId ?? '',
              state: data.state ?? '',
              city: data.city ?? '',
              phone_no:data.phone_no ?? '',
            });
          })
          .catch(err => {
            console.error("Error fetching user details", err);
          });
      } else {
        console.warn("Username not found in session storage");
        navigate('/');
      }
    } else {
      console.warn("Auth data not found in session storage");
      navigate('/');
    }
  }, [navigate, isLoggedIn]);
 
  const handleModalClose = () => {
    setShowModal(false);
  };
 
  const handleProfileClick = () => {
    setShowModal(true);
  };
 
  const handleLogout = () => {
    logout();
  };
 
  const handlePasswordChangeCompleted = () => {
    setShowChangePasswordButton(false);
    setShowChangePasswordModal(false);
    // Update session storage to reflect that the password has been changed
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const parsedAuthData = JSON.parse(authData);
      parsedAuthData.passwordChanged = true;
      sessionStorage.setItem('auth', JSON.stringify(parsedAuthData));
    }
  };
 
  const displayName = userName ? userName.split('@')[0] : '';
 
  const handleChangePasswordClick = () => {
    setShowChangePasswordModal(true);
  };
 
  return (
    <div>
      <nav className="rep-navbar" >
        <div className="rep-navbar-brand">LIT</div>
        <div className="rep-navbar-profile">
          <Link to='/representative'>Dashboard</Link>
          <Link to='/representative/tickets'>Tickets</Link>
          <div onClick={handleProfileClick} className="rep-profile-circle">{displayName.charAt(0).toUpperCase()}</div>
          <div onClick={handleProfileClick}>{displayName.toUpperCase()}
          </div>
          {showChangePasswordButton && (
          <div className="change-password-text" onClick={handleChangePasswordClick}>
          Please Change Password
        </div>
        )}
        </div>
        <button onClick={handleLogout}>Logout</button>
      </nav>
     
      {showChangePasswordModal && (
        <ChangePassword
          userName={userName}
          onPasswordChangeCompleted={handlePasswordChangeCompleted}
          role={role}
        />
      )}

      {showModal && (
        <UserModal
          userName={userName}
          userDetails={userDetails}
          formData={formData}
          setFormData={setFormData}
          setUserDetails={setUserDetails}
          onClose={handleModalClose}
          role={role}
        />
      )}
      <div>
        <RepresentativeDashboard/>
      </div>
    </div>
  );
};
 
export default RepresentativeMainPage;