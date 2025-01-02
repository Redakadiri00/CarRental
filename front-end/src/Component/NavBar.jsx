import React from 'react';
import "../css/NavBar.css";


const Navbar = () => {
    return (
      <nav className="navbar">
        <div className="navbar-logo">
          <a href="/">MyWebsite</a>
        </div>
        <ul className="navbar-links">
          <li><a href="/">Home</a></li>
          <li><a href="/about">About</a></li>
          <li><a href="/services">Services</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
      </nav>
    );
  };
  
  export default Navbar;