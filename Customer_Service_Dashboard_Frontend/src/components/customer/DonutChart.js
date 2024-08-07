import React, { useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip } from 'chart.js';
import axios from 'axios';
import './Customer.css'

// Register required Chart.js components
Chart.register(ArcElement, Tooltip);

const DonutChart = () => {
  const [ticketCounts, setTicketCounts] = useState({ OPEN: 0, 'in-progress': 0, close: 0 });
  const [error, setError] = useState(null);

  useEffect(() => {
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const parsedAuthData = JSON.parse(authData);
      console.log("Parsed auth data:", parsedAuthData);
      const idFromStorage = parsedAuthData?.id;
      if (idFromStorage) {
        axios.get(`${process.env.REACT_APP_CUSTOMER_GET_URL}/ticket/chart/${idFromStorage}`)
          .then(response => {
            const data = response.data;
            console.log("Ticket status data", data);
            if (Object.keys(data).length === 0 && data.constructor === Object) {
              setError("You have No tickets")
            } else {
              setTicketCounts(data);
            }
          })
          .catch(err => {
            console.error("Error fetching ticket details", err);
            setError("You have No tickets")
          });
      } else {
        console.warn("ID not found in session storage");
      }
    } else {
      console.warn("Auth data not found in session storage");
    }
  }, []);

  console.log(ticketCounts)

  // Data for the doughnut chart
  const chartData = {
    labels: ['Open', 'In-Progress', 'Close'],
    datasets: [
      {
        data: [ticketCounts.OPEN, ticketCounts['in-progress'], ticketCounts.close],
        backgroundColor: ['rgba(239, 138, 70, 0.7)',
          'rgba(223, 223, 79, 0.7)',
          'rgba(0, 149, 139, 0.7)',] 
      },
    ],
  };

  // Styles for the container div
  const containerStyles = {
    width: '250px',
    height: '250px',
    margin: '10px auto',
    marginTop: '30px',
    color:'white',
  };

  const chartOptions = {
    plugins: {
      legend: {
        display: true,
        labels: {
          color: 'white',
          font: {
            size: 16
          },
        },
      },
    },
  };

  return (
    <div className="donut-chart-container" style={containerStyles}>
      {error ? ( // Conditional rendering for error message
        <div>
          <h3 className="donut-chart-title">Ticket Status</h3>
          <p style={{ color: '#fff' }}>{error}</p>
        </div>
      ) : (
        <>
          <Doughnut data={chartData} options={chartOptions} />
        </>
      )}
    </div>
  );
};

export default DonutChart;
