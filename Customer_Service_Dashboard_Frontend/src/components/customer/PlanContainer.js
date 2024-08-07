import React, { useState, useEffect } from 'react';
import './PlanContainer.css';
import { plans } from '../../components/General/assets';

import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const PlanContainer = () => {
  const [showPrepaid, setShowPrepaid] = useState(false);
  const [showPostpaid, setShowPostpaid] = useState(false);
  const { logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const createBubbles = () => {
      const container = document.getElementById('bubble-container');
      const numBubbles = 20; // Number of bubbles to create
      for (let i = 0; i < numBubbles; i++) {
        const bubble = document.createElement('div');
        bubble.classList.add('bubble');
        bubble.style.left = `${Math.random() * 100}vw`; // Random horizontal position
        bubble.style.animationDuration = `${Math.random() * 5 + 5}s`; // Random animation duration
        container.appendChild(bubble);
      }
    };

    createBubbles();
  }, []);

  const togglePrepaid = () => {
    setShowPrepaid(!showPrepaid);
    setShowPostpaid(false); // Ensure only one section is open at a time
  };

  const togglePostpaid = () => {
    setShowPostpaid(!showPostpaid);
    setShowPrepaid(false); // Ensure only one section is open at a time
  };

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const handleHome = () => {
    navigate("/customer");
  };

  const handleSupport = () => {
    navigate("/customer/support");
  };

  const handleTicketsClick = () => {
    navigate('/customer/tickets');
  };

  return (
    <>
      <div className="customer-navbar">
        <div className="customer-navbar-brand">LIT</div>
        <ul className='customer-navbar-list'>
          <li onClick={handleHome} className="customer-navbar-home">Home</li>
          <li onClick={handleSupport} className="customer-navbar-home">Raise Query</li>
          <li onClick={handleTicketsClick} className="customer-navbar-home">My Tickets</li>
          <li onClick={handleLogout}>Logout</li>
        </ul>
      </div>
      <div id="bubble-container" className="bubble-container"></div>
      <div className="customer-plan-container">
        <div className={`prepaid-display ${showPrepaid ? 'active' : ''}`} onClick={togglePrepaid}>
          {/* Prepaid Plan content */}
          <h2>Prepaid Plans</h2>
          <p>Freedom to talk, freedom to surf - explore our prepaid options</p>
          <p>We offer 5 different Plans!</p>
          <ul>
            <li>Gold Pre-Plus</li>
            <li>Daily Pre-Delight</li>
            <li>Super Pre-Saver</li>
            <li>Power Pre-Plus</li>
            <li>Daily Pre-Connect </li>
          </ul>
        </div>
        <div className={`postpaid-display ${showPostpaid ? 'active' : ''}`} onClick={togglePostpaid}>
          {/* Postpaid Plan content */}
          <h2>Postpaid Plans</h2>
          <p>Elevate your connectivity with our hassle-free postpaid plans</p>
          <p>We offer 5 different Plans!</p>
          <ul>
            <li>Gold Plus</li>
            <li>Daily Delight</li>
            <li>Super Saver</li>
            <li>Power Plus</li>
            <li>Daily Connect</li>
          </ul>
        </div>
      </div>
      {showPrepaid &&
        <div className="prepaid-container">
          {/* Render prepaid plans */}
          {plans['pre-paid'].map((plan, index) => (
            <div key={index} className="pre-plan">
              <h3>{plan.name}</h3>
              <h5>Description</h5>
              <p>{plan.description}</p>
            </div>
          ))}
        </div>
      }
      {showPostpaid &&
        <div className="postpaid-container">
          {/* Render postpaid plans */}
          {plans['post-paid'].map((plan, index) => (
            <div key={index} className="post-plan">
              <h3>{plan.name}</h3>
              <h5>Description</h5>
              <p>{plan.description}</p>
            </div>
          ))}
        </div>
      }
      <footer className="customer-footer">
        <p>© 2024 Your Company. All rights reserved.</p>
      </footer>
    </>
  );
};

export default PlanContainer;
