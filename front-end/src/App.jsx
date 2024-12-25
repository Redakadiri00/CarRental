import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./Component/Navbar";
import CarList from "./Component/CarList";
import Reservation from "./Component/Reservation";

const App = () => {
  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          <Route path="/" element={<CarList />} />
          <Route path="/reservation/:carId" element={<Reservation />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;