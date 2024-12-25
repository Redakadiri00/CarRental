// front-end/src/Component/CarList.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import CarCard from "./CarCard";
import FilterBar from "./FilterBar";

const CarList = () => {
  const [cars, setCars] = useState([]); // State to hold car data
  const [filteredCars, setFilteredCars] = useState([]); // State to hold filtered car data
  const [loading, setLoading] = useState(true); // State to handle loading status
  const [error, setError] = useState(null); // State to handle errors

  // Fetch data from the backend
  useEffect(() => {
    const fetchCars = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/v1/vehicules"); // Replace with your Spring Boot endpoint
        setCars(response.data); // Assuming the response data is an array of car objects
        setFilteredCars(response.data); // Initialize filtered cars with all cars
        setLoading(false); // Set loading to false after fetching data
      } catch (err) {
        console.error("Error fetching car data:", err);
        setError("Failed to fetch car data.");
        setLoading(false);
      }
    };

    fetchCars();
  }, []); // Empty dependency array ensures this runs once when the component mounts

  // Handle filtering
  const handleFilter = ({ minPrice, maxPrice }) => {
    const filtered = cars.filter((car) => {
      const price = car.tarif_de_location;
      return (
        (minPrice === "" || price >= minPrice) &&
        (maxPrice === "" || price <= maxPrice)
      );
    });
    setFilteredCars(filtered);
  };

  // Render loading, error, or car list
  if (loading) return <p>Loading cars...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <FilterBar onFilter={handleFilter} />
      <div className="car-list">
        {filteredCars.map((car) => (
          <CarCard key={car.id_voiture} car={car} />
        ))}
      </div>
    </div>
  );
};

export default CarList;