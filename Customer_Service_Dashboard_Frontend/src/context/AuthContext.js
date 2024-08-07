import React, { createContext, useContext, useState } from 'react';


const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role, setRole] = useState(null);

  const login = (userRole) => {
    setIsLoggedIn(true);
    setRole(userRole);
  };

 const logout = () => {
    setIsLoggedIn(false);
    setRole(null);
    sessionStorage.removeItem('auth');
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, role, login, logout, setIsLoggedIn }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
