import React from 'react'
import ListManagers from './ListManager/ListManagers'
import ListRepresentatives from './ListRepresentative/ListRepresentatives'
import './AddEmployee.css'

function ListEmployee() {
  
  return (
    <div className='list-employee'>
      <h2>List of Employees</h2>
      <ListManagers/>
      <ListRepresentatives/> 
    </div>
  )
}

export default ListEmployee
