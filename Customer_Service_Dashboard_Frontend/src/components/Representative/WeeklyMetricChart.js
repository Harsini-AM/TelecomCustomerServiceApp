import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';
import axios from 'axios';

function formatDate(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Month is zero-based
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
}

function subtractDaysFromDate(date, days) {
    const result = new Date(date);
    result.setDate(result.getDate() - days);
    return formatDate(result);
}

const WeeklyMetricChart = ({ empId, type }) => {
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: [{
            label: '',
            data: [],
            backgroundColor: '',
            borderColor: '',
            borderWidth: 1
        }]
    });
    const [loading, setLoading] = useState(true);
    const currentDate = new Date(); 
    const currentDateFormatted = formatDate(currentDate); 
    const newDate = subtractDaysFromDate(currentDate, 7);

    const charOptions = {
        scales: {
            x: {
                title: {
                    display: true,
                    text: `Timeline : ${newDate} - ${currentDateFormatted}`,
                    font: {
                        size: 16, 
                        weight: 'bold' ,
                    },
                    color: 'white', 
                },
                ticks: {
                    font: {
                        size: 16, 
                        weight: 'bold' 
                    },
                    color: 'white', 
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Time (Minutes)',
                    font: {
                        size: 16, 
                        weight: 'bold' 
                    },
                    color: 'white', 
                },
                ticks: {
                    font: {
                        size: 16, 
                        weight: 'bold' 
                    },
                    color: 'white',
                    beginAtZero: true
                }
            }
        },
        plugins: {
            legend: {
                display: true,
                position: 'top',
                align: 'end',
                labels: {
                    font: {
                        size: 18, 
                    },
                    color: 'white', 
                }
            }
        },
        responsive: true,
        barThickness: 50, 
        maxBarThickness: 60 
    }

    useEffect(() => {
        const fetchData = async () => {
            const authData = sessionStorage.getItem('auth');
            if(authData) {
                const parsedAuthData = JSON.parse(authData);
                console.log("Parsed auth data:", parsedAuthData);
                const empId = parsedAuthData?.empId
                try {
                    const url = `${process.env.REACT_APP_EMPLOYEE_GET_URL}/weeklyResponseTime/${empId}`
                    const response = await axios.get(url);
                    console.log(response)
                    const labels = Object.keys(response.data);
                    const data = Object.values(response.data);

                    setChartData({
                        labels,
                        datasets: [{
                            label: 'Average Response Time (minutes)',
                            data,
                            backgroundColor: 'rgb(50,77,99,0.8)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                        }]
                    });
                    setLoading(false);
                } catch (error) {
                    console.error('Error fetching data:', error);
                    setLoading(false);
                }
            }
        };

        fetchData();
    }, [type]);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Bar
                data={chartData}
                options={charOptions}
            />
        </div>
    );
};

export default WeeklyMetricChart;
