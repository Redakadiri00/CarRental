import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { useParams } from "react-router-dom";
import axios from "axios";
import "../css/Reservation.css";
import DateComponent from "./DateComponent";


const Reservation = () => {
  const navigate = useNavigate();
  const { carId } = useParams();
  const [car, setCar] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  useEffect(() => {
    const fetchCar = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/vehicules/${carId}`);
        setCar(response.data);
        setLoading(false);
      } catch (err) {
        console.error("Error fetching car data:", err);
        setError("Failed to fetch car data.");
        setLoading(false);
      }
    };

    fetchCar();
  }, [carId]);

  const handleBookNow = async () => {
    try {
      const reservationData = {
        vehiculeId: car.id_voiture,
        dateDebut: startDate,
        dateFin: endDate,
        clientId: 1, // Hardcoded for now

      };

      console.log("Reservation data:", reservationData);

      await axios.post('http://localhost:8080/api/v1/reservations', reservationData);
      navigate(`../Payment/${car.id_voiture}`);
    } catch (err) {
      console.error("Error creating reservation:", err);
      setError("Failed to create reservation.");
    }
  };

  if (loading) return <p>Loading car details...</p>;
  if (error) return <p>{error}</p>;
  if (!car) return <p>No car details available.</p>;

  return (
    <div className="reservation-container">
      <h2>Reservation Details</h2>
      <div className="reservation">
        <div className="car-info">
          <img
            src="https://www.shutterstock.com/image-photo/seattle-washington-usa-march-31-600nw-2284495465.jpg"
            className="car-image"
          />
          <h3>
            {car.marque} {car.model}
          </h3>
          <div className="caracteristique-list">
            {car.caracteristique.map((item, index) => (
              <span key={index} className="caracteristique-item">
                {item}
              </span>
            ))}
          </div>
          <p>{car.tarif_de_location} MAD/jour</p>
          <p>Status: {car.status}</p>
          <DateComponent
            startDate={startDate}
            endDate={endDate}
            setStartDate={setStartDate}
            setEndDate={setEndDate}
          />
          <button className="book-now" onClick={handleBookNow}>
            Reserver
          </button>
        </div>
      </div>
    </div>
  );
};

export default Reservation;
