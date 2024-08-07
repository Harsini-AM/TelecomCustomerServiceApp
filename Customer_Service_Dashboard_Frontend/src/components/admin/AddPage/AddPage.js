import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useEffect,useState } from 'react';

import '../AdminMainPage.css'

import AddEmployee from '../../Employee/AddEmployee'
import { useAuth } from '../../../context/AuthContext';
import Navbar from '../../Navbar/Navbar'
import AdminSidebar from '../../Navbar/AdminSidebar';
 
const AddPage = ({type, role}) => {
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
        console.log(userName)
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
       <Navbar handleLogout={handleLogout}/>
       <AdminSidebar/>
      <div className="main-content">
        {type==='add' ? <AddEmployee type={'add'} role={role}/> : <AddEmployee type={'update'} role={role}/>}
      </div>
    </div>
  );
};
 
export default AddPage;