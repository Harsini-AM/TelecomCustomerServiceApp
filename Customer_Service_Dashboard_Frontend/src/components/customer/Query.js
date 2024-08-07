import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Query.css'; 
import { useNavigate } from 'react-router-dom';
import { notify } from '../General/Notification';


const Query = () => {
  const [id, setId] = useState('');
  const [queryType, setQueryType] = useState('');
  const [queryText, setQueryText] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate=useNavigate();

  useEffect(() => {
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const parsedAuthData = JSON.parse(authData);
      setId(parsedAuthData?.id ?? '');
    }
  }, []);

  const handleQueryTypeChange = (event) => {
    setQueryType(event.target.value);
  };

  const handleQueryTextChange = (event) => {
    if (event.target.value.length <= 250) {
      setQueryText(event.target.value);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (!queryType || !queryText) {
      setErrorMessage('Select a domain and type the description');
      return;
    }

    const queryData = {
      domain: queryType,
      description: queryText,
      customerId: id
    };

    axios.post(`${process.env.REACT_APP_CUSTOMER_GET_URL}/createTicket?id=${id}`, queryData)
      .then(response => {
        console.log('Query submitted successfully:', response.data);
        notify('Ticket raised successfully')
        setQueryType('');
        setQueryText('');
        setErrorMessage('');
        setTimeout(() => {
          navigate('/customer/tickets');
        }, 2000);
      })
      .catch(error => {
        if (error.response && error.response.status === 404) {
          setErrorMessage('No representative available for the specified domain');
          notify('No representative available for the specified domain')
        } else {
          setErrorMessage('Unable to submit ticket');
          notify('Unable to submit ticket')
        }
        notify('No representative available for the specified domain')
        console.error('Error submitting query:', error);
      });
  };

  return (
    <>
    <div className="query-component">
      <h3>Raise a Query</h3>
      {errorMessage && <p className="query-error-message">{errorMessage}</p>}
      <form onSubmit={handleSubmit}>
        <div className="query-form-group">
          <label htmlFor="queryType">Query Type</label>
          <select
            id="queryType"
            value={queryType}
            onChange={handleQueryTypeChange}
            className="form-control">
            <option value="">Select an Issue Type</option>
            <option value="billing">Payment issues</option>
            <option value="technical">Need Technical Support</option>
            <option value="plans">Plan Management Issues</option>
            <option value="outage">Outage Issues</option>
            <option value="network">Network and Roaming Issues</option>
          </select>
        </div>
        <div className="query-form-group">
          <label htmlFor="queryText">Your Query</label>
          <textarea
            id="queryText"
            value={queryText}
            onChange={handleQueryTextChange}
            className="query-form-control"
            maxLength="250"
          ></textarea>
          <small className="text-muted">{queryText.length}/250 characters</small>
        </div>
        <button type="submit" className="btn btn-primary">Submit</button>
      </form>
    </div>
    </>   
  );
};

export default Query;
