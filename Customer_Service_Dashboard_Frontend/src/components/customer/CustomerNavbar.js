import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import '../customer/Customer.css'
 
function CustomerNavbar() {
    const { logout, isLoggedIn } = useAuth();
    const navigate = useNavigate();
    const [userName, setUserName] = useState('');
    const [userDetails, setUserDetails] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [formData, setFormData] = useState({
        empId: '',
        userName: '',
        fName: '',
        lName: '',
        domain: '',
        managerId: '',
        state: '',
        city: '',
        phone_no: '',
    });
    const [showChangePassword, setShowChangePassword] = useState(true);
    const [role, setRole] = useState('');
 
    useEffect(() => {
        const authData = sessionStorage.getItem('auth');
        if (authData) {
            const parsedAuthData = JSON.parse(authData);
            console.log("Parsed auth data:", parsedAuthData);
            const usernameFromStorage = parsedAuthData.userName;
            const passwordChangedFromStorage = parsedAuthData.passwordChanged;
            const role = parsedAuthData.role;
            setRole(role);
            if (passwordChangedFromStorage) {
                setShowChangePassword(false);
            }
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
                            phone_no: data.phone_no ?? '',
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
    }, [navigate, isLoggedIn, setRole, setShowChangePassword, setUserName]);
 
    const handleLogout = () => {
        logout();
        navigate('/');
    };
 
    const handleCircleClick = () => {
        setShowModal(true);
      };
   
      const handleCloseModal = () => {
        setShowModal(false);
      };
   
      const handleHomeClick=()=>{
        navigate('/customer')
      }
   
      const handleTicketsClick = () => {
        navigate('/customer/tickets');
      };
   
      const handleSupportClick=()=>{
        navigate('/customer/support')
      }
      const handleServiceClick=()=>{
        navigate('/customer/plans')
      }
 
    const displayName = `${formData.fName} ${formData.lName}`;
 
    return (
       
        <div className="customer-navbar">
        <div className="customer-navbar-brand">LIT</div>
        <ul className='customer-navbar-list'>
            <li onClick={handleHomeClick} className="customer-navbar-home">Home</li>
          <li onClick={handleSupportClick} className="customer-navbar-home">Raise Query</li>
          <li onClick={handleServiceClick} className="customer-navbar-home">Our Services</li>
          <li onClick={handleTicketsClick} className="customer-navbar-home">My Tickets</li>
          <li onClick={handleLogout}>Logout</li>
 
        </ul>
 
      </div>
       
    );
}
 
export default CustomerNavbar;