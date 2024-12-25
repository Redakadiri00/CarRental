import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const Reservation = () => {
  const { carId } = useParams();
  const [car, setCar] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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

  if (loading) return <p>Loading car details...</p>;
  if (error) return <p>{error}</p>;
  if (!car) return <p>No car details available.</p>;

  return (
    <div className="reservation">
      <h2>Reservation Details</h2>


      <img src={car.imageVoiture}  className="car-image" />
      <h3>{car.marque} {car.model}</h3>
      {/* <h3>{car.caracteristique[0]}</h3> */}
      <p>{car.tarif_de_location} MAD/jour</p>
      <p>Status: {car.status}</p>
     
    </div>
  );
};

export default Reservation;