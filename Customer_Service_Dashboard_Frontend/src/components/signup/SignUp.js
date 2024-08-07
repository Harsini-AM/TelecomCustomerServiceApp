import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './SignUp.css';
import { notify } from '../General/Notification';

const SignUp = () => {
  const [formData, setFormData] = useState({
    userName: '',
    password: '',
    confirmPassword: '',
    city:'',
    state:'',
    phone_no:'',
  });

  const statesAndCities = {
    'Tamil Nadu': ['Chennai'],
    'Karnataka': ['Bangalore'],
    'Telangana': ['Hyderabad'],
    'Maharashtra': ['Mumbai'],
    'West Bengal': ['Kolkata'],
    'Delhi': ['New Delhi']
  };

  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handleData = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
    validateField(name, value);
  };

  const validateField = (name, value) => {
    let newErrors = { ...errors };
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    const phone_noRegex=/^[6-9]\d{9}$/;

    switch (name) {
      case 'userName':
        if (!emailRegex.test(value)) {
          newErrors.userName = 'Invalid email address';
        } else if (/@LITR\.com|@LITM\.com|@admin\.com$/.test(value)) {
          newErrors.userName = 'Email cannot contain @LITR.com, @LITM.com, or @admin.com';
        } else {
          delete newErrors.userName;
        }
        break;
      case 'password':
        if (!passwordRegex.test(value)) {
          newErrors.password = 'Password must have at least one uppercase, one lowercase, one number, and one special character';
        } else {
          delete newErrors.password;
        }
        break;
      case 'confirmPassword':
        if (value !== formData.password) {
          newErrors.confirmPassword = 'Passwords do not match';
        } else {
          delete newErrors.confirmPassword;
        }
        break;
      case 'phone_no':
      if (!phone_noRegex.test(value)) {
        newErrors.phone_no = 'Phone number must be 10 digits and start with 6-9';
      } else {
        delete newErrors.phone_no;
      }
      break;
      default:
        break;
    }

    setErrors(newErrors);
  };

  const validateForm = () => {
    const newErrors = {};
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    if (!emailRegex.test(formData.userName)) {
      newErrors.userName = 'Invalid email address';
    } else if (/@LIT.com|@admin\.com$/.test(formData.userName)) {
      newErrors.userName = 'Email cannot contain @LIT.com or @admin.com';
    }

    if (!passwordRegex.test(formData.password)) {
      newErrors.password = 'Password must have at least one uppercase, one lowercase, one number, and one special character';
    }

    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = 'Passwords do not match';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
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

  const handleSubmit = (e) => {
    e.preventDefault();

    if (validateForm()) {
      axios.post(`${process.env.REACT_APP_CUSTOMER_REGISTER}`, { 
        userName: formData.userName, 
        password: formData.password ,
        firstName: '',
        lastName:'',
        city:formData.city,
        state:formData.state,
        phone_no:formData.phone_no,
        planName:'',
        planDescription:''

      })
      .then(response => {
        console.log("register response",response.data)
        if (response.status === 200) {
          notify(response.data.message)
          navigate('/', { state: { userName: formData.userName , password:formData.password } });
        }
        else{
          notify(response.data)
        } 
      })
      .catch(err => {
        notify("Sign up failed due to", err.response?.data || err.message);
      });
    }
  };

  return (
    <div className='signup-modal'>
      <div className='signup-left'>
        <div className='signup-card'>
        <h2 className='typing-demo'>Welcome to LIT!</h2>
        <p>"Empower your mobile experience with our intuitive app, effortlessly recharge your phone, and seamlessly raise any queries you have, all in one place!"</p>
      </div>
      </div>
      <div className='signup-right'>
        <h2>Sign Up</h2>
        <form className='signup-form' onSubmit={handleSubmit}>
          <label htmlFor='userName'>UserName</label>
          <input type="email" name="userName" value={formData.userName} onChange={handleData} style={{ color: 'black' }}/>
          {errors.userName && <p className="signup-error">{errors.userName}</p>}<br />
          <label htmlFor='password'>Password</label>
          <input type="password" name="password" value={formData.password} onChange={handleData} style={{ color: 'black' }}/>
          {errors.password && <p className="signup-error">{errors.password}</p>}<br />
          <label htmlFor='confirmPassword'>Confirm Password</label>
          <input type="password" name="confirmPassword" value={formData.confirmPassword} onChange={handleData}style={{ color: 'black' }} />
          {errors.confirmPassword && <p className="signup-error">{errors.confirmPassword}</p>}<br />
          <label htmlFor='phoneNumber'>Phone Number</label>
          <input type="text" name="phone_no" value={formData.phone_no} onChange={handleData}style={{ color: 'black' }} />
          {errors.phone_no && <p className="signup-error">{errors.phone_no}</p>}<br />
          <label>City:</label>
          <select name="city" value={formData.city} onChange={handleCityChange}style={{ color: 'black' }}>
            <option disabled value="">Select a city</option>
            {Object.keys(statesAndCities).map(state =>
              statesAndCities[state].map(city =>
                <option key={city} value={city}>{city}</option>
              )
            )}
          </select>
          <label>State:</label>
          <input type="text" name="state" value={formData.state} onChange={handleData}  disabled style={{ color: 'white' }}/>
          <button type="submit">Sign Up</button>
        </form>
      </div>
    </div>
  );
};

export default SignUp;
