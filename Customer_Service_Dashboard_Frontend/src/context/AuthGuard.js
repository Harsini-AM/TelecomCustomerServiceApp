import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

const AuthGuard = ({ children, roles }) => {
  const { isLoggedIn, role } = useAuth();

  if (!isLoggedIn) {
    return <Navigate to="/" />;
  }

  if (roles && roles.length > 0 && !roles.includes(role)) {
    return <Navigate to="/" />;
  }

  return children;
};

export default AuthGuard;
