import React, { useEffect, useState } from 'react';
import 'leaflet/dist/leaflet.css';
import Outage from './OutageDashboard';
import ManagerCharts from './ManagerCharts';
import './Manager.css'
import NewRepPage from '../Representative/newRep/NewRepPage';
import axios from 'axios';
import { toast } from 'react-toastify';
import TicketStatusChartForRep from '../Representative/TicketStatusChartForRep';
import ManagerTicketStatus from './TicketChart/ManagerTicketStatus';
 
 
function ManagerDashboard() {
  const [count, setCount] = useState(false);
  const [totalTickets, setTotalTickets] = useState(0);
  const [ticketData, setTicketData] = useState(null)
  const statusOrder = ["OPEN", "in-progress", "close"];
 
 
  useEffect(() => {
    const fetchManagerData = async () => {
        try {
          const authData = sessionStorage.getItem('auth');
          if(authData)
          {
            const parsedAuthData = JSON.parse(authData);
            console.log("Parsed auth data:", parsedAuthData);
            const empId = parsedAuthData?.empId
            const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getCountManager/${empId}`);
            console.log(response.data);
            if(response.data===0){
              setCount(true);
            }
        } }catch (err) {
            console.error(err.response.data);
            toast.error(err.response.data)
        } 
    };
 
    fetchManagerData();
}, []);


const loadData = () => {
  console.log('reload');
  const authData = sessionStorage.getItem('auth');
  if(authData)
  {
    const parsedAuthData = JSON.parse(authData);
    console.log("Parsed auth data:", parsedAuthData);
    const empId = parsedAuthData?.empId
    axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/statusCounts/${empId}`)
    .then(response => {
      console.log('ticket data is', response.data);
      setTicketData(response.data);
      const total = Object.values(response.data).reduce((sum, count) => sum + count, 0);
      setTotalTickets(total);
      
  
    })
    .catch(error => {
      console.error('Error fetching ticket data:', error);
      toast.error(error?.response?.data)
    });

  }
  
};


      useEffect(() => {
        loadData();
      }, []);


  return (
      <div className='manager-dashboard'>
            
            {count ? <NewRepPage/> : <>
                <ManagerCharts/>
                <div className="ticketChart">
                <h3>Ticket Status</h3>
                    <div className='status'>
                      {statusOrder.map(status => (
                        <ManagerTicketStatus
                          key={status}
                          status={status}
                          count={ticketData ? ticketData[status] : 0}
                          totalTickets={totalTickets}
                        />
                      ))}
                    </div>
                </div>
                <Outage/>
            </>}
      </div>
  )
}
 
export default ManagerDashboard