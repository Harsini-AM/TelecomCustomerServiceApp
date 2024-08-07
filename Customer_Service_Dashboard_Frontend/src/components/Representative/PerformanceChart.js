import axios from 'axios';
import React, { useEffect, useState } from 'react'
import '../Representative/RepresentativeFile.css'

function PerformanceChart({type,empId}) {
    const [responseTime, setResponseTime] = useState(0);
    const [resolutionTime, setResolutionTime] = useState(0);

    useEffect(() => {
        const loadResponseData = async () => {
            try {
              const authData = sessionStorage.getItem('auth');
              if(authData)
                {
                  const parsedAuthData = JSON.parse(authData);
                  console.log("Parsed auth data:", parsedAuthData);
                  const empId = parsedAuthData?.empId
              const url = `${process.env.REACT_APP_EMPLOYEE_GET_URL}/averageResponseTime/${empId}`;
              const response = await axios.get(url);
              setResponseTime(response.data);
            }} catch (err) {
                console.error('Error fetching Response data:', err);
            } 
          };
        loadResponseData()
      }, []);

      useEffect(() => {
        const loadResponseData = async () => {
            try {
              const authData = sessionStorage.getItem('auth');
              if(authData)
                {
                  const parsedAuthData = JSON.parse(authData);
                  console.log("Parsed auth data:", parsedAuthData);
                  const empId = parsedAuthData?.empId
                  if(empId){
                      const url=`${process.env.REACT_APP_EMPLOYEE_GET_URL}/averageResolutionTime/${empId}`;
                      const response = await axios.get(url);
                  
                      setResolutionTime(response.data);
                    // console.log("Response is :" ,response.data)
                    } }
          }catch (err) {
                console.error('Error fetching Resolution data:', err);
            } 
          };
        loadResponseData()
      }, []);


    

  return (
    <div className="representative-stats-container"> 
        <h3>Your Performance Metrics</h3>
        <div className='parameter'>
        <div className="circle response-time">
          <div className="stats">
            <span>Average Response Time</span>
            <span>{(Number(responseTime)/60).toFixed(1)} minutes</span>
          </div>
        </div>
        <div className="circle resolution-time">
          <div className="stats">
            <span>Average Resolution Time</span>
            <span>{(Number(resolutionTime)/(60*60)).toFixed(0)} Hours</span>
          </div>
        </div>
        </div>
  </div>

  )
}

export default PerformanceChart
