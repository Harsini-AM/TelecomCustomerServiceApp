import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TopFiveReps from './TopFiveReps';
import './Manager.css'
 
function ManagerCharts() {
    return (
     
      <div className='manager-chart-container'>
         <h2>Manager Dashboard</h2>
          
        <div className='manager-charts'>
            <TopFiveReps type='response'/>
            <TopFiveReps type='resolution'/>
        </div>
      </div>
   
    );
}
 
export default ManagerCharts;