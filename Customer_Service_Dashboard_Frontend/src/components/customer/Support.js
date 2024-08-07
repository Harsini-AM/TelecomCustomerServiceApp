import React from 'react';
import './Support.css'; // Import the CSS file for Support layout
import FAQSection from './FAQSection';
import Query from './Query';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const Support = () => {
    const { logout} = useAuth();
    const navigate=useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/');
      };
    
      const handleTicketsClick = () => {
        navigate('/customer/tickets');
      };
    
      const handleSupportClick=()=>{
        navigate('/customer')
      }
      const handleServiceClick=()=>{
        navigate('/customer/plans')
      }
  return (
    <>
    <div className="customer-navbar">
      <div className="customer-navbar-brand">LIT</div>
        <ul className='customer-navbar-list'>
        <li onClick={handleSupportClick} className="customer-navbar-home">Home</li>
        <li onClick={handleServiceClick} className="customer-navbar-home">Our Services</li>
        <li onClick={handleTicketsClick} className="customer-navbar-home">My Tickets</li>
        <li onClick={handleLogout}>Logout</li>
        </ul>
    </div>
    <div className="support-container">
      <div className="faq-section">
        <FAQSection />
      </div>
      <div className="query-section">
        <Query />
      </div>
    </div>
    <footer className="customer-footer">
        <p>© 2024 Your Company. All rights reserved.</p>
    </footer>
    </>
  );
};

export default Support;
