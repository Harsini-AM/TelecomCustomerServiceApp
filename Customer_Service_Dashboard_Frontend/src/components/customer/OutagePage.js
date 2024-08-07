import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
 
const OutagePage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { outageMessage } = location.state || { outageMessage: { message: 'You are in an outage location.', userName: '' } };
 
  // Check if outageMessage is an object and extract the message and userName
  const message = typeof outageMessage === 'object' ? outageMessage.message : outageMessage;
  const userName = typeof outageMessage === 'object' ? outageMessage.userName : userName;
 
  return (
    <div className="outage-page-container">
      <h3>Outage Notification</h3>
      {userName && <p>Welcome: {userName}</p>}
      <p>Sorry We are Unable to provide our Service!{message}</p>
      <button onClick={() => navigate('/')}>Go to Login</button>
    </div>
  );
};
 
export default OutagePage;
 