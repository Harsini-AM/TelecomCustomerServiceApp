import React from 'react'
import '../Manager/ManagerMainPage.css'
import { MdPeopleAlt } from 'react-icons/md';
import { RxDashboard } from 'react-icons/rx';
import { CiCircleList } from "react-icons/ci";
import { Link } from 'react-router-dom';

function AdminSidebar() {
  return (
    <div className="sidebar">
        <Link to='/admin'><RxDashboard className='icon'/> Dashboard</Link>
        <Link to='/admin/add'><MdPeopleAlt className='icon'/> Add Employee</Link>
        <Link to='/admin/list'><CiCircleList className='icon'/> View Employee</Link>
      </div>
  )
}

export default AdminSidebar
