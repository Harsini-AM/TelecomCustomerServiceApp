import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './RepDetails.css'; // Import CSS for styling
import { toast } from 'react-toastify';

function RepDetails() {
  const { managerId } = useParams();
  const [representatives, setRepresentatives] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchRepData = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getRepByManagerId/${managerId}`);
        setRepresentatives(response.data);
        setIsLoading(false);
      } catch (error) {
        console.error(error);
        toast.error(error.response.data)
      }
    };

    fetchRepData();
  }, [managerId]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="rep-details-container">
      <div className="rep-card">
        <h2>Representative Details</h2>
        <table className="rep-table">
          <thead>
            <tr>
              <th>RepId</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email Address</th>
              <th>Domain Name</th>
              <th>Phone Number</th>
            </tr>
          </thead>
          <tbody>
            {representatives.map((rep) => (
              <tr key={rep.empId}>
                <td>{rep.empId}</td>
                <td>{rep.fName}</td>
                <td>{rep.lName}</td>
                <td>{rep.userName}</td>
                <td>{rep.domain}</td>
                <td>{rep.phone_no}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default RepDetails;
