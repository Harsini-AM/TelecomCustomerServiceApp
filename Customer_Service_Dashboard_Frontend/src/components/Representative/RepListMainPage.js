import React from 'react';
import { useAuth } from '../../context/AuthContext';
import './RepresentativeMainPage.css';
import RepDetails from '../Manager/RepDetails/RepDetails';
import Navbar from '../Navbar/Navbar';
import AdminSidebar from '../Navbar/AdminSidebar';

 
const RepListMainPage = () => {
  const { logout} = useAuth();
 
  const handleLogout = () => {
    logout();
  };
 
 
  return (
    <div className='grid-container'>
      <Navbar handleLogout={handleLogout}></Navbar>     
      <AdminSidebar/>
      <div className="main-content">
        <RepDetails/>
      </div>
    </div>
  );
};
 
export default RepListMainPage;