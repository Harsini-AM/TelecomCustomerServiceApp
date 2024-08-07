import React, { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';
import { Chart, LineElement, Tooltip, Legend, Title, CategoryScale, LinearScale, PointElement } from 'chart.js';

Chart.register(LineElement, Tooltip, Legend, Title, CategoryScale, LinearScale, PointElement);

const ErrorRateChart = () => {
  const [errorRateData, setErrorRateData] = useState({
    labels: [],  
    datasets: [
      {
        label: 'Error Rate (%)',
        data: [], 
        borderColor: 'rgba(255, 99, 132, 1)',
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        fill: true,
        tension: 0.4,
      },
    ],
  });

  useEffect(() => {
    const generateDummyData = () => {
      const labels = ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5', 'Week 6'];
      const data = [5, 4, 6, 3, 7, 4];  

      setErrorRateData({
        labels,
        datasets: [
          {
            label: 'Error Rate (%)',
            data,
            borderColor: 'rgba(255, 99, 132, 1)',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            fill: true,
            tension: 0.4,
          },
        ],
      });
    };

    generateDummyData();
  }, []);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        labels: {
          color: 'white',
        },
      },
    },
    scales: {
      x: {
        ticks: {
          color: 'white',
        },
      },
      y: {
        beginAtZero: true,
        ticks: {
          color: 'white',
        },
      },
    },
  };

  const containerStyles = {
    width: '200px',
    height: '400px',
    margin: '0 auto',
  };

  return (
    <div>
      <Line data={errorRateData} options={options} />
    </div>
  );
};

export default ErrorRateChart;
