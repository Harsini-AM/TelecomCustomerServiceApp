// config.js
const roleConfig = {
  admin: {
    domain: '@admin.com',
    apiEndpoint: process.env.REACT_APP_ADMIN_LOGIN_URL,
  },
  employee: {
    domain: '@LIT.com',
    apiEndpoint: process.env.REACT_APP_EMPLOYEE_LOGIN_URL,
  },
  customer: {
    domain: '',
    apiEndpoint: process.env.REACT_APP_CUSTOMER_LOGIN_URL,
  },
};
 
export const roleToRouteMap = {
  admin: '/admin',
  representative: '/representative',
  manager: '/manager',
  customer: '/customer',
};

export default roleConfig;