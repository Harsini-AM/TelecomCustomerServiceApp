import React from 'react'

function Navbar({handleLogout}) {
  return (
    <nav className="man-navbar">
        <div className="man-navbar-brand">LIT</div>
        <div className="man-navbar-profile">
          <div className="man-profile-circle">A</div>
          Admin
        </div>
        <button onClick={handleLogout}>Logout</button>
      </nav>
  )
}

export default Navbar
