import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/login/Login'
import AdminPage from './components/admin/Admin';
import { ToastContainer } from 'react-toastify';
import RepresentativeMainPage from './components/Representative/RepresentativeMainPage';
import CustomerPage from './components/customer/Customer';
import TicketHistory from './components/customer/TicketHistory';
import Support from './components/customer/Support';
import PlanContainer from './components/customer/PlanContainer';
import ManagerMainPage from './components/Manager/ManagerMainPage'
import SignUp from './components/signup/SignUp'
import { AuthProvider } from './context/AuthContext';
import AuthGuard from './context/AuthGuard'
import RepDetails from './components/Manager/RepDetails/RepDetails';
import AddPage from './components/admin/AddPage/AddPage';
import ListPage from './components/admin/ListPage/ListPage';
import ManagerDetails from './components/admin/viewPage/ManagerDetails';
import RepresentativeDetails from './components/admin/viewPage/RepresentativeDetails';
import TicketMainPage from './components/Representative/TicketMainPage';
import RepListMainPage from './components/Representative/RepListMainPage';
import OutagePage from './components/customer/OutagePage';
 
const App = () => {
  return (
    <AuthProvider>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/signup" element={<SignUp/>}></Route>
          <Route path="/admin" element={<AuthGuard><AdminPage /></AuthGuard>} />
          <Route path='/admin/add' element={<AuthGuard><AddPage type='add'/></AuthGuard>}/>
          <Route path='/admin/list' element={<AuthGuard><ListPage/></AuthGuard>}/>
          <Route path="/representative" element={<RepresentativeMainPage />} />
          <Route path="/representative/:repId" element={<AuthGuard><RepresentativeDetails/></AuthGuard>}/>
          <Route path='/representative/update/:id' element={<AuthGuard><AddPage type='update' role='representative'/></AuthGuard>}></Route>
          {/* <Route path='/representative/tickets' element={<AuthGuard><TicketDashboard/></AuthGuard>}></Route> */}
          <Route path="/manager" element={<AuthGuard><ManagerMainPage /></AuthGuard>} />
          <Route path="/manager/:managerId" element={<AuthGuard><ManagerDetails/></AuthGuard>}/>
          <Route path='/manager/update/:id' element={<AuthGuard><AddPage type='update' role='manager'/></AuthGuard>}/>
          <Route path='/manager/reps/:managerId' element={<AuthGuard><RepListMainPage/></AuthGuard>}/>
          <Route path="/customer" element={<AuthGuard><CustomerPage /></AuthGuard>}></Route>
          <Route path="/customer/tickets" element={<AuthGuard><TicketHistory /></AuthGuard>} />
          <Route path="/customer/support" element={<AuthGuard><Support /></AuthGuard>} />
          <Route path="/customer/plans" element={<AuthGuard><PlanContainer /></AuthGuard>} />
          <Route path="/representative/tickets" element={<AuthGuard><TicketMainPage/></AuthGuard>}/>
          <Route path="/outagepage" element={<OutagePage></OutagePage>}></Route>
          <Route path='/manager/repList' element={<RepDetails/>}/>
        </Routes>
      <ToastContainer />
    </AuthProvider>
  );
};
 
export default App;
