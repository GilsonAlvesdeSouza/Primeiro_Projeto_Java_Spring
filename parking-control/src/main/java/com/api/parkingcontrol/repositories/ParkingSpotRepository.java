package com.api.parkingcontrol.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.parkingcontrol.models.ParkingSpotModel;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {
	boolean existsByLicensePlateCar(String lincesePlateCar);

	boolean existsByParkingSpotNumber(String parkingSpotNumber);

	boolean existsByApartamentAndBlock(String apartament, String block);
}
