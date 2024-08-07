import React, { useEffect, useState } from 'react';
import PerformanceChart from './PerformanceChart';
import './RepresentativeFile.css';
import ResponseTimeChart from './WeeklyMetricChart';
import NewRepPage from './newRep/NewRepPage';
import TicketStatusChart from './TicketStatusChart';
import ErrorRateChart from './errorRate/ErrorChartRate';

function RepresentativeDashboard() {
  const [newRep, setNewRep] = useState(false);
 

  if(newRep) return <NewRepPage/>

  return (
    <div className='rep-main-dashboard'>
      <h2>Performance Metrics of Representative</h2>
      
      <div className='rep-dashboard'>
        <div className='dashboard-row'>
          <div className="performanceChart">
            <PerformanceChart type='representative' />
          </div>
          <div className="rep-ticketChart">
            <h4>Ticket Status</h4>
              <TicketStatusChart/>
          </div>
          <div className="error-ticketChart">
            <h4>Error Rate</h4>
              <ErrorRateChart/>
          </div>
        </div>
        <div className='dashboard-row chart-row'>
          <div className="weekly-chart">
          <h3>Average Response Time per Day of Week</h3>
            <ResponseTimeChart type='response'/>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RepresentativeDashboard;

