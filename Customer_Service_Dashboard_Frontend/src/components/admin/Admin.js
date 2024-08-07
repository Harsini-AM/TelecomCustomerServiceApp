import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useEffect,useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import AdminHomepage from './AdminHomepage';
import './AdminHomepage.css'
import AdminSidebar from '../Navbar/AdminSidebar';
 
const AdminPage = () => {
  const { logout ,isLoggedIn} = useAuth();
  const [userName, setUserName] = useState('');
  const navigate=useNavigate();

  const handleLogout = () => {
    logout() // Call logout function
    navigate('/')
  };
 
  useEffect(() => {
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const parsedAuthData = JSON.parse(authData);
      console.log("Parsed auth data:", parsedAuthData);
      const usernameFromStorage = parsedAuthData?.userName;
      if (usernameFromStorage) {
        setUserName(usernameFromStorage);
      } else {
        console.warn("Username not found in session storage");
        navigate('/');
      }
    } else {
      console.warn("Auth data not found in session storage");
      navigate('/');
    }
  }, [navigate, isLoggedIn]);
  return (
    <div className='grid-container'>
     <nav className="admin-navbar">
        <div className="admin-navbar-brand">LIT</div>
        <div className="admin-navbar-profile">
          <div className="admin-profile-circle">A</div>
          Admin
        </div>
        <button onClick={handleLogout}>Logout</button>
      </nav>
     <AdminSidebar/>
      <div className="main-content">
        <AdminHomepage/>
      </div>
    </div>
  );
};
 
export default AdminPage;