import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import './TicketStatusChartForRep.css'; // Import the CSS file

ChartJS.register(ArcElement, Tooltip, Legend);

const TicketStatusChartForRep = ({ status, count, totalTickets }) => {
  

  const chartData = {
    labels: [`${status} (${count} tickets)`, `Other (${totalTickets - count} tickets)`],
    datasets: [
      {
        data: [count, totalTickets - count],
        backgroundColor: [
          'rgba(0, 128, 128, 0.7)',
          'rgba(152, 209, 204, 0.7)',
          
        ],
        hoverBackgroundColor: [
          'rgba(0, 128, 128, 0.5)',
          'rgba(72, 209, 204, 0.5)',
          
        ]
      }
    ]
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
          }
        }
      }
    },
    legend: {
        labels: {
          font: {
            size: 8 
          }
        }
    }
  };

  return (
    <div className="ticket-chart-container">
      <h4>{status}</h4>
      <Doughnut data={chartData} options={options} />
    </div>
  );
};

export default TicketStatusChartForRep;
