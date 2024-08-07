import React, { useEffect, useState } from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart, ArcElement, Tooltip, Legend } from 'chart.js';
import axios from 'axios';
import './RepresentativeFile.css';

Chart.register(ArcElement, Tooltip, Legend);

const TicketStatusChart = () => {
  const [ticketCounts, setTicketCounts] = useState({ open: 0, 'in-progress': 0, closed: 0 });

  useEffect(() => {
    const loadTicketData = async () => {
      try {
        const authData = sessionStorage.getItem('auth');
        if (authData) {
          const parsedAuthData = JSON.parse(authData);
          console.log("Parsed auth data:", parsedAuthData);
          const empId = parsedAuthData?.empId;
          if (empId) {
            const response = await axios.get(`http://localhost:8086/employee/statusCountsForRep/${empId}`);
            console.log(response.data);
            setTicketCounts({
              open: response.data['OPEN'] || 0,
              'in-progress': response.data['in-progress'] || 0,
              closed: response.data['close'] || 0,
            });
          }
        }
      } catch (err) {
        console.error(err);
      }
    };

    loadTicketData();
  }, []);

  const chartData = {
    labels: ['Open', 'In-progress', 'Close'],
    datasets: [
      {
        data: [ticketCounts.open, ticketCounts['in-progress'], ticketCounts.closed],
        backgroundColor: [
          'rgba(239, 138, 70, 0.7)',
          'rgba(223, 223, 79, 0.7)',
          'rgba(0, 149, 139, 0.7)',
        ],
      },
    ],
  };

  const containerStyles = {
    width: '250px',
    height: '350px',
    margin: '0 auto',
    color: 'white'
  };

  const chartOptions = {
    plugins: {
      legend: {
        display: true,
        labels: {
          generateLabels: (chart) => {
            const data = chart.data;
            if (data.labels.length && data.datasets.length) {
              return data.labels.map((label, i) => {
                const count = data.datasets[0].data[i];
                return {
                  text: `${label} (${count} Tickets)`,
                  fillStyle: data.datasets[0].backgroundColor[i],
                  hidden: isNaN(data.datasets[0].data[i]),
                  strokeStyle: data.datasets[0].borderColor ? data.datasets[0].borderColor[i] : data.datasets[0].backgroundColor[i],
                  textAlign: 'left',
                  fontColor: 'white',
                };
              });
            }
            return [];
          },
          color: 'white',
          font: {
            size: 16
          },
        },
      },
    },
  };

  return (
    <div style={containerStyles}>
      <Doughnut data={chartData} options={chartOptions} />
    </div>
  );
};

export default TicketStatusChart;
