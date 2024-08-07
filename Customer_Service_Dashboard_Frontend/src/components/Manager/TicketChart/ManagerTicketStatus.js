import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import './ManagerTicketStatus.css'; 
import { color } from 'chart.js/helpers';

ChartJS.register(ArcElement, Tooltip, Legend);

const ManagerTicketStatus = ({ status, count, totalTickets }) => {
  const chartData = {
    labels: [`${status.toUpperCase()} (${count} tickets)`],
    datasets: [
      {
        data: [count, totalTickets - count],
        backgroundColor: [
          'rgba(0, 128, 100, 0.7)', 
          'rgba(128, 128, 128, 0.7)',
        ],
        hoverBackgroundColor: [
          'rgba(0, 128, 0, 0.5)', 
          'rgba(128, 128, 128, 0.5)', 
        ],
      },
    ],
  };

  const options = {
    plugins: {
      tooltip: {
        callbacks: {
          label: (tooltipItem) => {
            const label = tooltipItem.label || '';
            const value = tooltipItem.raw;
            const percentage = ((value / totalTickets) * 100).toFixed(2);
            return `${label}: ${value} tickets (${percentage}%)`;
          },
        },
      },
      legend: {
        display: true,
        labels: {
          color: 'white',
          font: {
              size: 16
          }
        },
      },
    },
  };

  return (
    <div className="ticket-chart-container">
      <h4>{status.toUpperCase()}</h4>
      <Doughnut data={chartData} options={options} />
    </div>
  );
};

export default ManagerTicketStatus;
