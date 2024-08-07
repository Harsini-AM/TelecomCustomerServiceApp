import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import roleConfig, { roleToRouteMap } from '../../config/config';
import './Login.css';
import { useAuth } from '../../context/AuthContext';
import { notify } from '../General/Notification';

const Login = () => {
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const { login, isLoggedIn, setIsLoggedIn } = useAuth();
  const navigate = useNavigate();
 
  useEffect(() => {
    const authData = sessionStorage.getItem('auth');
    if (authData) {
      const { role } = JSON.parse(authData);
      if (role) setIsLoggedIn(true);
      if (isLoggedIn) {
        const route = roleToRouteMap[role] || '/';
        navigate(route);
      }
    }
  }, [isLoggedIn, navigate, setIsLoggedIn]);
 
  const handleData = (e) => {
    const { name, value } = e.target;
    name === 'userName' ? setUserName(value) : setPassword(value);
  };
 
  const loginhandle = async (e) => {
    e.preventDefault();
    const roleEntry = Object.values(roleConfig).find(({ domain }) => userName.includes(domain)) || roleConfig.customer;
    const { apiEndpoint } = roleEntry;
    console.log(apiEndpoint);
    try {
      const response = await axios.post(apiEndpoint, { userName, password });
      console.log("login response", response.data);
      if (response.data.message === "Login Successful") {
        const role = response.data?.role;
        const userName = response.data?.userName;
        const passwordChanged = response.data?.passwordChanged;
        const id = response.data?.id;
        const empId = response.data?.empId;
        sessionStorage.setItem('auth', JSON.stringify({ id, empId, role, userName, passwordChanged }));
        login(role);
        const route = roleToRouteMap[role] || '/';
        notify(response.data.message);
        navigate(route);
      } else {
        notify("Invalid Credentials");
      }
    } catch (err) {
      notify("Invalid Credentials");
    }
  };
 
  return (
    <div className='login-modal'>
      <div className='login-left'>
        <div className="login-card">
          <h2 className="typing-demo">Welcome to LIT!</h2>
          <p>"Empower your mobile experience with our intuitive app, effortlessly recharge your phone, and seamlessly raise any queries you have, all in one place!"</p>
        </div>
      </div>
      <div className='login-right'>
        <div className="login-card">
          <h3>Login</h3>
          <form className='login-form' onSubmit={loginhandle}>
            <label htmlFor='userName'>Username</label>
            <input type="email" name="userName" value={userName} onChange={handleData} /><br />
            <label htmlFor='password'>Password</label>
            <input type="password" name="password" value={password} onChange={handleData} /><br />
            <button type="submit">Login</button>
          </form>
          <p>Don't have an account? <Link to="/signup" className="signup-link">Click here</Link></p>
        </div>
      </div>
    </div>
  );
};
 
export default Login;
