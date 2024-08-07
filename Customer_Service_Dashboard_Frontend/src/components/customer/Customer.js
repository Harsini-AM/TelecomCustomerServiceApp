import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Customer.css';

import { useNavigate } from 'react-router-dom';
import UserModal from '../General/UserModal';
import DonutChart from './DonutChart';
import RecentTicket from './RecentTicket';
import Chatbot from './ChatBot';
import { useAuth } from '../../context/AuthContext';

const CustomerPage = () => {
  const navigate = useNavigate();
  const { logout, isLoggedIn } = useAuth();
  const [userName, setUserName] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [userDetails, setUserDetails] = useState({});
  const [formData, setFormData] = useState({
    id: '',
    userName: '',
    fName: '',
    lName: '',
    state: '',
    city: '',
    phone_no: '',
    planType: '',
    planName: '',
    planDescription: ''
  });

  useEffect(() => {
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const parsedAuthData = JSON.parse(authData);
      const usernameFromStorage = parsedAuthData?.userName;
      if (usernameFromStorage) {
        setUserName(usernameFromStorage);
        axios.get(`${process.env.REACT_APP_CUSTOMER_GET_URL}/${usernameFromStorage}`)
          .then(response => {
            const data = response.data;
            setUserDetails(data);
            setFormData({
              id: data.id ?? '',
              userName: data.userName ?? '',
              fName: data.fName ?? '',
              lName: data.lName ?? '',
              state: data.state ?? '',
              city: data.city ?? '',
              phone_no: data.phone_no ?? '',
              planType: data.planType ?? '',
              planName: data.planName ?? '',
              planDescription: data.planDescription ?? '',
            });
          })
          .catch(err => {
            console.error("Error fetching user details", err);
          });
      } else {
        navigate('/');
      }
    } else {
      navigate('/');
    }
  }, [navigate, isLoggedIn]);

  const handleCircleClick = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const handleTicketsClick = () => {
    navigate('/customer/tickets');
  };

  const handleSupportClick=()=>{
    navigate('/customer/support')
  }
  const handleServiceClick=()=>{
    navigate('/customer/plans')
  }
  

  const displayName = userName ? userName.split('@')[0] : '';

  return (
    <div className='customer-page-container'>
      <div className="customer-navbar">
        <div className="customer-navbar-brand">LIT</div>
        <ul className='customer-navbar-list'>
          <li onClick={handleSupportClick} className="customer-navbar-home">Raise Query</li>
          <li onClick={handleServiceClick} className="customer-navbar-home">Our Services</li>
          <li onClick={handleTicketsClick} className="customer-navbar-home">My Tickets</li>
          <li onClick={handleLogout}>Logout</li>
        </ul>
        <div className="customer-navbar-profile" onClick={handleCircleClick}>
          <div className="customer-profile-circle">{displayName.charAt(0).toUpperCase()}</div>
          {displayName}
        </div>
      </div>

      <div className="customer-dashboard">
        <div className="plan-details glassmorphism">
          {formData.planType && formData.planName && formData.planDescription ? (
            <>
              <h2>Welcome {displayName}!</h2>
              <h3>Plan Details</h3>
              <p><strong>Type:</strong> {formData.planType}</p>
              <p><strong>Name:</strong> {formData.planName}</p>
              <p><strong>Description:</strong> {formData.planDescription}</p>
            </>
          ) : (
            <>
              <p>Explore our Plans and Services</p>
              <button onClick={handleServiceClick}>Know More</button>
            </>
          )}
        </div>

        <div className="three-details-row">
          <div className="ticket-details glassmorphism">
            <h3>Recent Tickets</h3>
            <RecentTicket/>
            <div className="button-container">
              <button className="ticket-button" onClick={handleTicketsClick}>View all Tickets</button>
              <button className="support-page" onClick={handleSupportClick}>Raise a Ticket</button>
            </div>
          </div>

          <div className="pie-chart-container glassmorphism">
          <h3>Ticket Status</h3>
            <DonutChart/>
          </div>

          <div className='our-plans-service glassmorphism'>
            <div className='intro'>
              <h3>Our Services</h3>
              <p>"Empower your mobile experience with our intuitive app, effortlessly recharge your phone, and seamlessly raise any queries you have, all in one place!"</p>
            </div>
            <div className='location'>
              <h3>Locations</h3>
              <p>"We provide services in Chennai, Mumbai, Hyderabad, Delhi, Kolkata, and Bangalore."</p>
            </div>
            <button className="service-page"onClick={handleServiceClick}>Know More!</button>
          </div>
        </div>
      </div>

      {showModal && (
        <UserModal
          userName={userName}
          onClose={handleCloseModal}
          userDetails={userDetails}
          formData={formData}
          setFormData={setFormData}
          setUserDetails={setUserDetails}
          role={"customer"}
        />
      )}

      <Chatbot/>

      <footer className="customer-footer">
        <p>© 2024 Your Company. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default CustomerPage;
