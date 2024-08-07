import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './TicketHistory.css';

const RecentTicket = () => {
  const [tickets, setTickets] = useState([]);
  const [error, setError] = useState(null); 

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
            setError("You have No tickets"); 
          });
      } else {
        console.warn("ID not found in session storage");
        setError("ID not found in session storage."); 
      }
    } else {
      console.warn("Auth data not found in session storage");
      setError("Auth data not found in session storage."); 
    }
  }, []);

  // Sort tickets based on createdAt in descending order and get only 3
  const sortedTickets = tickets
    .slice()
    .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
    .filter(ticket => ticket.status.toLowerCase() === 'open')  
    .slice(0, 3);  

  return (
    <div className="ticket-table-customer">
      {error ? (
        <div>{error}</div>
      ) : (
        <div className="ticket-table-container-customer">
          <table className="ticket-table-customer">
          <thead>
            <tr>
              <th>Ticket ID</th>
              <th>Description</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {sortedTickets.map(ticket => (
              <tr key={ticket.ticketId}>
                <td>{ticket.ticketId}</td>
                <td>{ticket.description}</td>
                <td>{ticket.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      )}
    </div>
  );
};

export default RecentTicket;
