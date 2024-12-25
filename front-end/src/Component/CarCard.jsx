import React from "react";
import { useNavigate } from "react-router-dom";
import "../css/CarCard.css"; // Import CSS for styling

function CarCard({ car }) {

    const navigate = useNavigate();
    
    const handleBookNow = () => {
        navigate(`Reservation/${car.id_voiture}`);
    };



  return (
    <div className="car-card">
      <div>
      <img src={car.imageVoiture} alt={car.name} className="car-image" />
      <span>{car.status}</span>

    </div>
      <h3>{car.marque} {car.model}</h3>
      <h3>{car.caracteristique[0]}</h3>
      
      <p>{car.tarif_de_location} MAD/jour</p>

      <button className="book-now" onClick={handleBookNow}>Book Now</button>
    </div>
  );
};

export default CarCard;
