import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './TicketHistory.css';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const TicketHistory = () => {
  const [tickets, setTickets] = useState([]);
  const { logout } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true); // State to track loading status

  useEffect(() => {
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const parsedAuthData = JSON.parse(authData);
      console.log("Parsed auth data:", parsedAuthData);
      const idFromStorage = parsedAuthData?.id;
      if (idFromStorage) {
        axios.get(`${process.env.REACT_APP_CUSTOMER_GET_URL}/ticket/${idFromStorage}`)
          .then(response => {
            const data = response.data;
            console.log("Ticket data", data);
            setTickets(data);
          })
          .catch(err => {
            console.error("Error fetching ticket details", err);
          })
          .finally(() => {
            setLoading(false); // Update loading status after fetching data
          });
      } else {
        console.warn("ID not found in session storage");
      }
    } else {
      console.warn("Auth data not found in session storage");
    }
  }, []);

  const handleTicketsClick = () => {
    navigate('/customer');
  };

  const handleSupportClick = () => {
    navigate('/customer/support');
  };

  const handleServiceClick = () => {
    navigate('/customer/plans');
  };

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  // Sort tickets based on createdAt in descending order
  const sortedTickets = tickets.slice().sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

  return (
    <>
      <div className="customer-navbar">
        <div className="customer-navbar-brand">LIT</div>
        <ul className="customer-navbar-list">
          <li onClick={handleTicketsClick} className="customer-navbar-home">Home</li>
          <li onClick={handleSupportClick} className="customer-navbar-home">Raise Query</li>
          <li onClick={handleServiceClick} className="customer-navbar-home">Our Services</li>
          <li onClick={handleLogout}>Logout</li>
        </ul>
      </div>
      <div className="ticket-history-customer">
        <h2>Ticket History</h2>
        {loading ? ( // Display loading indicator while fetching data
          <p>Loading...</p>
        ) : (
          <>
            {tickets.length === 0 ? ( // Check if tickets array is empty
              <p>No tickets found.</p>
            ) : (
              <div className="ticket-table-container-customer">
                <table className="ticket-table-customer">
                  <thead>
                    <tr>
                      <th>Ticket ID</th>
                      <th>Agent Email</th>
                      <th>Description</th>
                      <th>Status</th>
                      <th>Domain</th>
                      <th>DATE</th>
                      <th>Created At</th>
                    </tr>
                  </thead>
                  <tbody>
                    {sortedTickets.map(ticket => (
                      <tr key={ticket.ticketId}>
                        <td>{ticket.ticketId}</td>
                        <td>{ticket.empUserName}</td>
                        <td>{ticket.description}</td>
                        <td>{ticket.status}</td>
                        <td>{ticket.domain}</td>
                        <td>{new Date(ticket.createdAt).toLocaleDateString()}</td> {/* Date */}
                        <td>{new Date(ticket.createdAt).toLocaleTimeString()}</td> {/* Time */}
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </>
        )}
      </div>
      <footer className="customer-footer">
        <p>© 2024 Your Company. All rights reserved.</p>
      </footer>
    </>
  );
};

export default TicketHistory;
