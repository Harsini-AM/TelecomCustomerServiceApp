import React from 'react'
import Navbar from '../../Navbar/Navbar'
import AdminSidebar from '../../Navbar/AdminSidebar'

function AdminPage() {

  return (
    <div className='grid-container'>
      <Navbar handleLogout={handleLogout}/>
      <AdminSidebar/>
      <div className="main-content">
        <ManagerDashboard />
      </div>
    </div>
  )
}

export default AdminPage
