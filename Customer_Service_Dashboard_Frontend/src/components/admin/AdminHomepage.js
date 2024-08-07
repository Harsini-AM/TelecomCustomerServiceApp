import React, { useEffect, useState } from 'react';
import { Chart, registerables } from 'chart.js';
import axios from 'axios';
import './AdminHomepage.css';
import Outage from './outage/Outage';



Chart.register(...registerables);

function AdminHomepage() {
    const [sortedLocationWiseTicketCount, setSortedLocationWiseTicketCount] = useState([]);
    const [domainData, setDomainData] = useState({});
    const [customerSatisfaction, setCustomerSatisfaction] = useState(0);
    const [customerCounts, setCustomerCounts] = useState({});

    useEffect(() => {
        // Fetch tickets data
        axios.get('http://localhost:8087/customer/countCustomersByLocation')
            .then(response => {
                const data = response.data;
                const locationWiseTicketCount = {};

                data.forEach(([location, count]) => {
                    locationWiseTicketCount[location] = count;
                });

                const sortedLocationWise = Object.entries(locationWiseTicketCount)
                    .sort(([locationA], [locationB]) => locationA.localeCompare(locationB))
                    .map(([, count]) => count);

                setSortedLocationWiseTicketCount(sortedLocationWise);
                initializeLocationBarChart(sortedLocationWise);
            })
            .catch(error => {
                console.error('Error fetching tickets:', error);
            });

        // Fetch domain data
        axios.get('http://localhost:8087/customer/countByDomain')
            .then(response => {
                const data = response.data;
                const domainWiseTicketCount = {};

                data.forEach(([domain, count]) => {
                    domainWiseTicketCount[domain] = count;
                });

                setDomainData(domainWiseTicketCount);
                initializeDomainPieChart(domainWiseTicketCount);
            })
            .catch(error => {
                console.error('Error fetching domain data:', error);
            });

        // Fetch customer data
        axios.get('http://localhost:8087/customer/countCustomersByLocation')
            .then(response => {
                const data = response.data;
                const customerCountsByCity = {};

                data.forEach(([city, count]) => {
                    customerCountsByCity[city] = count;
                });

                setCustomerCounts(customerCountsByCity);
                initializeCustomerBarChart(customerCountsByCity);
            })
            .catch(error => {
                console.error('Error fetching customers:', error);
            });

        // Fetch customer feedback data
        axios.get('http://localhost:8087/customer/getCustomerRating')
            .then(response => {
                const data = response.data;
                setCustomerSatisfaction(data);
                initializeCustomerSatisfactionChart(data);
            })
            .catch(error => {
                console.error('Error fetching customer satisfaction:', error);
            });
    }, []);

    const initializeLocationBarChart = (customerCounts) => {
        const barChartCanvas = document.getElementById('barChart');
        const labels = ['Bangalore', 'Chennai', 'Delhi', 'Hyderabad', 'Mumbai', 'Pune'];
        const counts = Object.values(customerCounts);

        if (barChartCanvas) {
            new Chart(barChartCanvas, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'No of Tickets',
                        data: counts,
                        backgroundColor: [
                            'rgba(0, 128, 128, 0.7)',
                            'rgba(72, 209, 204, 0.7)',
                            'rgba(32, 178, 170, 0.7)',
                            'rgba(0, 139, 139, 0.7)',
                            'rgba(0, 206, 209, 0.7)',
                            'rgba(64, 224, 208, 0.7)'
                        ],
                        borderColor: [
                            'rgba(0, 128, 128, 1)',
                            'rgba(72, 209, 204, 1)',
                            'rgba(32, 178, 170, 1)',
                            'rgba(0, 139, 139, 1)',
                            'rgba(0, 206, 209, 1)',
                            'rgba(64, 224, 208, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            display: false // Hide the legend
                        },
                        tooltip: {
                            callbacks: {
                                label: function(tooltipItem) {
                                    return `${tooltipItem.label}: ${tooltipItem.raw}`;
                                }
                            }
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            grid: {
                                display: false
                            },
                            ticks: {
                                color: '#ffffff', // White color for y-axis labels
                                stepSize: 20,
                            }
                        },
                        x: {
                            grid: {
                                display: false // Remove vertical grid lines
                            },
                            ticks: {
                                color: '#ffffff' // White color for x-axis labels
                            }
                        }
                    },
                    animation: {
                        duration: 1000,
                        easing: 'easeOutBounce'
                    }
                }
            });
        }
    };

    const initializeDomainPieChart = (domainWiseTicketCount) => {
        const pieChartCanvas = document.getElementById('domainPieChart');
        const labels = Object.keys(domainWiseTicketCount);
        const counts = Object.values(domainWiseTicketCount);

        if (pieChartCanvas) {
            new Chart(pieChartCanvas, {
                type: 'doughnut',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'No of Tickets',
                        data: counts,
                        backgroundColor: [
                            'rgba(0, 128, 128, 0.7)',
                            'rgba(72, 209, 204, 0.7)',
                            'rgba(32, 178, 170, 0.7)',
                            'rgba(0, 139, 139, 0.7)',
                            'rgba(0, 206, 209, 0.7)',
                            'rgba(64, 224, 208, 0.7)'
                        ],
                        borderColor: [
                            'rgba(0, 128, 128, 1)',
                            'rgba(72, 209, 204, 1)',
                            'rgba(32, 178, 170, 1)',
                            'rgba(0, 139, 139, 1)',
                            'rgba(0, 206, 209, 1)',
                            'rgba(64, 224, 208, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                color: '#ffffff' // White color for legend labels
                            }
                        }
                    },
                    animation: {
                        animateScale: true,
                        duration: 1500,
                        easing: 'easeInOutQuart'
                    }
                }
            });
        }
    };

    const initializeCustomerSatisfactionChart = (customerSatisfaction) => {
        const pieChartCanvas = document.getElementById('customerPieChart');
        const labels = ['Satisfied', 'Unsatisfied'];
        const data = [82.33, 100 - 82.33];

        if (pieChartCanvas) {
            new Chart(pieChartCanvas, {
                type: 'doughnut',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Customer Satisfaction',
                        data: data,
                        backgroundColor: [
                            'rgba(0, 128, 128, 0.7)',
                            'rgba(72, 209, 204, 0.7)'
                        ],
                        borderColor: [
                            'rgba(0, 128, 128, 1)',
                            'rgba(72, 209, 204, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            position: 'bottom',
                            labels: {
                                color: '#ffffff' // White color for legend labels
                            }
                        }
                    },
                    animation: {
                        animateScale: true,
                        duration: 1500,
                        easing: 'easeInOutQuart'
                    }
                }
            });
        }
    };

    const initializeCustomerBarChart = (customerCounts) => {
        const barChartCanvas = document.getElementById('customerBarChart');
        const labels = ['Bangalore', 'Chennai', 'Delhi', 'Hyderabad', 'Mumbai', 'Pune'];
        const counts = Object.values(customerCounts);

        if (barChartCanvas) {
            new Chart(barChartCanvas, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'No of Customers',
                        data: counts,
                        backgroundColor: [
                            'rgba(0, 128, 128, 0.7)',
                            'rgba(72, 209, 204, 0.7)',
                            'rgba(32, 178, 170, 0.7)',
                            'rgba(0, 139, 139, 0.7)',
                            'rgba(0, 206, 209, 0.7)',
                            'rgba(64, 224, 208, 0.7)'
                        ],
                        borderColor: [
                            'rgba(0, 128, 128, 1)',
                            'rgba(72, 209, 204, 1)',
                            'rgba(32, 178, 170, 1)',
                            'rgba(0, 139, 139, 1)',
                            'rgba(0, 206, 209, 1)',
                            'rgba(64, 224, 208, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            display: false // Hide the legend
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            grid: {
                                display: false
                            },
                            ticks: {
                                color: '#ffffff', // White color for y-axis labels
                                stepSize: 20,
                            }
                        },
                        x: {
                            grid: {
                                display: false // Remove vertical grid lines
                            },
                            ticks: {
                                color: '#ffffff' // White color for x-axis labels
                            }
                        }
                    },
                    animation: {
                        duration: 1000,
                        easing: 'easeOutBounce'
                    }
                }
            });
        }
    };

    return (
        <div className="admin-container my-4 pl-5">
            <div className="row">
                <div className="col-md-6 mb-4">
                    <div className="card glass-card h-100">
                        <div className="card-body">
                            <h3 className="card-title">Location vs No of Tickets</h3>
                             <canvas id="barChart" className="chart-canvas"></canvas> 
                            
                        </div>
                    </div>
                </div>
                <div className="col-md-6 mb-4">
                    <div className="card glass-card h-100 d-flex align-items-center justify-content-center">
                        <div className="card-body">
                            <h3 className="card-title">Domain vs No of Tickets</h3>
                            <div className="pie-chart-container">
                                <canvas id="domainPieChart" className="chart-canvas"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-md-6 mb-4">
                    <div className="card glass-card h-100 d-flex align-items-center justify-content-center">
                        <div className="card-body">
                            <h3 className="card-title">Customer Satisfaction</h3>
                            <div className="pie-chart-container">
                                <canvas id="customerPieChart" className="chart-canvas"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-md-6 mb-4">
                    <div className="card glass-card h-100">
                        <div className="card-body">
                            <h3 className="card-title">No of Customers vs Location</h3>
                            <canvas id="customerBarChart" className="chart-canvas"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div className="row">
                <Outage />
            </div>
        </div>
    );
}

export default AdminHomepage;
