import React, { useState,useEffect,useRef} from 'react';
import axios from 'axios';
import './UserModal.css';
import { statesAndCities, plans } from './assets';



const UserModal = ({ userName, onClose, setUserDetails, formData, setFormData, role }) => {
  const [error, setError] = useState('');
  const formRef = useRef(null);

  useEffect(() => {
    if (error && formRef.current) {
      console.log("Form ref:", formRef.current);
      console.log(error)
      formRef.current.scrollTop = 0; 
    }
  }, [error]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleCityChange = (e) => {
    const selectedCity = e.target.value;
    const selectedState = Object.keys(statesAndCities).find(state =>
      statesAndCities[state].includes(selectedCity)
    );
    setFormData({
      ...formData,
      city: selectedCity,
      state: selectedState,
    });
  };

  const handlePlanTypeChange = (e) => {
    const selectedPlanType = e.target.value;
    setFormData({
      ...formData,
      planType: selectedPlanType,
      planName: '',
      planDescription: '',
    });
  };

  const handlePlanNameChange = (e) => {
    const selectedPlanName = e.target.value;
    const selectedPlan = plans[formData.planType].find(plan => plan.name === selectedPlanName);
    setFormData({
      ...formData,
      planName: selectedPlanName,
      planDescription: selectedPlan ? selectedPlan.description : '',
    });
  };

  const validateForm = () => {
    if (!/^[a-zA-Z]{1,16}$/.test(formData.fName)) {
      return "First name should contain only letters and not exceed 16 characters.";
    }
    if (!/^[a-zA-Z]{1,16}$/.test(formData.lName)) {
      return "Last name should contain only letters and not exceed 16 characters.";
    }
    return "";
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const errorMsg = validateForm();
    if (errorMsg) {
      setError(errorMsg);
      return;
    }

    let apiUrl;
    switch (role) {
      case 'representative':
      case 'manager':
        apiUrl = `${process.env.REACT_APP_EMPLOYEE_UPDATE_URL}/${userName}?role=${role}`;
        break;
      case 'customer':
        apiUrl = `${process.env.REACT_APP_CUSTOMER_UPDATE_URL}/${userName}`;
        break;
      default:
        apiUrl = `${process.env.REACT_APP_DEFAULT_UPDATE_URL}/${userName}`;
    }
    
    axios.put(apiUrl, formData)
      .then(response => {
        console.log("After update", response.data);
        setUserDetails(response.data);
        onClose();
      })
      .catch(err => {
        console.error("Error updating user details", err);
        setError("Error updating user details");
      });
  };

  const renderAdditionalFields = () => {
    switch (role) {
      case 'representative':
        return (
          <>
            <label>Manager Id:</label>
            <input type="text" name="managerId" value={formData.managerId} onChange={handleChange} disabled style={{ color: 'white' }}/>
            <label>Employee Id:</label>
            <input type="text" name="empId" value={formData.empId} disabled style={{ color: 'white' }}/>
            <label>Domain Name:</label>
            <input type="text" name="domain" value={formData.domain} disabled style={{ color: 'white' }} />
          </>
        );
      case 'manager':
        return (
          <>
            <label>Employee Id:</label>
            <input type="text" name="empId" value={formData.empId} disabled style={{ color: 'white' }}/>
            <label>Domain Name:</label>
            <input type="text" name="domain" value={formData.domain} disabled style={{ color: 'white' }} />
          </>
        );
      case 'customer':
        return (
          <>
            <label>Plan Type:</label>
            <select name="planType" value={formData.planType} onChange={handlePlanTypeChange} style={{ color: 'black' }}>
              <option disabled value="">Select a plan type</option>
              <option value="pre-paid">Pre-Paid</option>
              <option value="post-paid">Post-Paid</option>
            </select>
            <label>Plan Name:</label>
            <select name="planName" value={formData.planName} onChange={handlePlanNameChange} disabled={!formData.planType} style={{ color: 'black' }}>
              <option disabled value="">Select a plan name</option>
              {formData.planType && plans[formData.planType].map(plan =>
                <option key={plan.name} value={plan.name}>{plan.name}</option>
              )}
            </select>
            <label>Plan Description:</label>
            <textarea name="planDescription" value={formData.planDescription} onChange={handleChange} disabled style={{ color: 'white' }} />
          </>
        );
      default:
        return null;
    }
  };

  return (
    <div className="user-modal">
      <div className="user-modal-content"  ref={formRef}>
        <span className="user-close" onClick={onClose}>&times;</span>
        <h2>User Details</h2>
        {error && <p className="usermodal-error">{error}</p>}
        <form onSubmit={handleSubmit}>
          <label>UserName:</label>
          <input type="text" name="userName" value={formData.userName} disabled style={{ color: 'white' }}/>
          <label>First Name:</label>
          <input type="text" name="fName" value={formData.fName} onChange={handleChange} required  style={{ color: 'black' }}/>
          <label>Last Name:</label>
          <input type="text" name="lName" value={formData.lName} onChange={handleChange} required style={{ color: 'black' }} />
          <label>City:</label>
          <select name="city" value={formData.city} onChange={handleCityChange} required style={{ color: 'black' }}>
            <option disabled value="">Select a city</option>
            {Object.keys(statesAndCities).map(state =>
              statesAndCities[state].map(city =>
                <option key={city} value={city}>{city}</option>
              )
            )}
          </select>
          <label>State:</label>
          <input type="text" name="state" value={formData.state} onChange={handleChange} disabled style={{ color: 'white' }} />
          <label>Phone Number:</label>
          <div className="phone-number-field">
            <span className="phone-prefix">+91</span>
            <input type="text" name="phone_no" value={formData.phone_no} disabled style={{ color: 'white' }}/>
          </div>
          {renderAdditionalFields()}
          <button className="usermodal-btn" type="submit">Update</button>
        </form>
      </div>
    </div>
  );
};

export default UserModal;
