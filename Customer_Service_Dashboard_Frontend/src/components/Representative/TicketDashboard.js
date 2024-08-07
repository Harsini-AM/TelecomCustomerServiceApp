import React, { useState, useEffect } from 'react';
import axios from 'axios';
import emailjs from 'emailjs-com';
import './TicketDashboard.css';


export default function TicketDashboard() {
    const [tickets, setTickets] = useState([]);
    const [userName, setUserName] = useState('');
    const [filterStatus, setFilterStatus] = useState('');
 
    useEffect(() => {
        const authData = sessionStorage.getItem('auth');
        if (authData) {
            const parsedAuthData = JSON.parse(authData);
            console.log("Parsed auth data:", parsedAuthData);
            const idFromStorage = parsedAuthData?.empId;
            if (idFromStorage) {
                axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getTicketsByEmpId/${idFromStorage}`)
                    .then(response => {
                        const data = response.data;
                        console.log("Ticket data from backend", data);
                        setTickets(data);
                    })
                    .catch(err => {
                        console.error("Error fetching ticket details", err);
                    });
            } else {
                console.warn("ID not found in session storage");
            }
        } else {
            console.warn("Auth data not found in session storage");
        }
    }, []);
 
    useEffect(() => {
        if (userName && tickets.length > 0) {
            const closeTicket = tickets.find(ticket => ticket.status === 'close');
            if (closeTicket) {
                const to_email = "devatharshini91@gmail.com";
                const ticketId = closeTicket.ticketId;
                sendEmail(to_email, ticketId);
                console.log('Email sent for ticket ID:', ticketId);
            }
        }
    }, [userName, tickets]);
 
    const sendEmail = (to_email, ticketId) => {
        emailjs.send("service_1vpzcfm", "template_t4hi4ze", {
            to_email: to_email,
            ticketId: ticketId,
        }, "Xbbcaz1v6pvhKiKnM")
            .then((response) => {
                console.log('Email sent successfully!', response.status, response.text);
            })
            .catch((error) => {
                console.error('Failed to send email:', error);
            });
    };
 
    const handleStatusChange = (index, event) => {
        const newTickets = [...tickets];
        const newStatus = event.target.value;
 
        const updatedTicket = { ...newTickets[index] };
        updatedTicket.status = newStatus;
 
        newTickets[index] = updatedTicket;
        setTickets(newTickets);
 
        axios.put(`${process.env.REACT_APP_CUSTOMER_GET_URL}/updateTicket/${updatedTicket.ticketId}`, updatedTicket)
            .then(response => {
                console.log('Status and response time updated successfully in DB:', response.data);
                const data = response.data;
                setUserName(data.customerUserName);
            })
            .catch(error => {
                console.log('Error updating status:', error);
            });
    };
 
    const handleFilterChange = (event) => {
        setFilterStatus(event.target.value);
    };
 
    const filteredTickets = filterStatus
        ? tickets.filter(ticket => ticket.status === filterStatus || filterStatus === 'All')
        : tickets;
 
    return (
        <div>
            <div className="filter-container">
            <label>Filter by Status:</label>
                <select value={filterStatus} onChange={handleFilterChange}>
                    <option value="">All</option>
                    <option value="OPEN">Open</option>
                    <option value="in-progress">In Progress</option>
                    <option value="close">Close</option>
                </select>
            </div>
            <table className="ticket-table-history">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Domain</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Response Time(min)</th>
                        <th>Resolution Time(Days)</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredTickets.length === 0 ? (
                        <tr>
                            <td colSpan="7">No data available</td>
                        </tr>
                    ) : (
                        filteredTickets.map((ticket, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{ticket.domain}</td>
                                <td>{ticket.description}</td>
                                <td>
                                    <select
                                        value={ticket.status}
                                        onChange={(event) => handleStatusChange(index, event)}
                                        disabled={ticket.status === 'close'}
                                    >
                                        {ticket.status !== 'in-progress' && (
                                            <option value="OPEN">Open</option>
                                        )}
                                        <option value="in-progress">In Progress</option>
                                        <option value="close">Close</option>
                                    </select>
                                </td>
                                <td>{(Number(ticket.responseTime)/60).toFixed(0)}</td>
                                <td>{(Number(ticket.resolutionTime)/(60*60)).toFixed(0)} </td>
                                <td>{ticket.createdAt}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
}
