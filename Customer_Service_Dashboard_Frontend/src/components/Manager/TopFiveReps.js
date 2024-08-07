import React, { useEffect, useState } from 'react';
import { Bar } from 'react-chartjs-2';
import axios from 'axios';
import './Manager.css';

const TopFiveReps = ({ type }) => {
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
    const [representatives, setRepresentatives] = useState(null);

    useEffect(() => {
        const fetchManagerData = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getRepresentatives`);
                setRepresentatives(response.data);
                //console.log(response.data)
            } catch (err) {
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchManagerData();
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            const authData = sessionStorage.getItem('auth');
            if (authData) {
                const parsedAuthData = JSON.parse(authData);
                //console.log("Parsed auth data:", parsedAuthData);
                const empId = parsedAuthData?.empId;
                try {
                    const url = type === 'response' ?
                        `${process.env.REACT_APP_EMPLOYEE_GET_URL}/responseAverage/${empId}` :
                        `${process.env.REACT_APP_EMPLOYEE_GET_URL}/resolutionAverage/${empId}`;

                    let response = await axios.get(url);

                    console.log(representatives);
                    if (response.data) {
                        const labels = Object.keys(response.data);
                        const data = Object.values(response.data);

                       
                        const names = labels.map(id => {
                            const rep = representatives?.find(rep => rep.empId === Number(id));
                            return rep.fName; 
                        });
                        console.log(names)

                        setChartData({
                            labels: names,
                            datasets: [{
                                label: type === 'response' ? 'Average Response Time (minutes)' : 'Average Resolution Time (days)',
                                data,
                                backgroundColor: type === 'response' ? 'rgba(75, 192, 192, 0.8)' : 'rgb(50,127,99,0.8)',
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            }]
                        });
                    }
                    setLoading(false);
                } catch (error) {
                    console.error('Error fetching data:', error);
                    setLoading(false);
                }
            }
        };

        fetchData();
    }, [type, representatives]);

    const chartOptions = {
        scales: {
            x: {
                title: {
                    display: true,
                    text: "Top 5 Representatives",
                    font: {
                        size: 18,
                        weight: 'bold',
                        color: 'white'
                    }
                },
                ticks: {
                    font: {
                        size: 16,
                        weight: 'bold',
                        color: 'white'
                    }
                }
            },
            y: {
                title: {
                    display: true,
                    text: type === 'response' ? 'Time (minutes)' : 'Time (days)',
                    font: {
                        size: 18,
                        weight: 'bold',
                        color: 'white'
                    }
                },
                ticks: {
                    font: {
                        size: 16,
                        weight: 'bold',
                        color: 'white'
                    },
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
                    color: 'white',
                    font: {
                        size: 16
                    }
                }
            }
        },
        responsive: true,
        barThickness: 50,
        maxBarThickness: 60
    }

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className='glass-card-top5'>
            <h4>Top 5 Representative's {type === 'response' ? 'Response ' : 'Resolution'} Time</h4>
            <Bar
                data={chartData}
                options={chartOptions}
            />
        </div>
    );
};

export default TopFiveReps;
