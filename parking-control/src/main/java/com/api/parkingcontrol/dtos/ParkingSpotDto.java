package com.api.parkingcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ParkingSpotDto {
	@NotBlank(message = "the field parkingSpotNumber is required")
	private String parkingSpotNumber;

	@NotBlank(message = "the field licensePlateCar is required")
	@Size(max = 7, message = "the maximum size for licensePlateCar is 7")
	private String licensePlateCar;

	@NotBlank(message = "the field brandCar is required")
	private String brandCar;

	@NotBlank(message = "the field modelCar is required")
	private String modelCar;

	@NotBlank(message = "the field colorCar is required")
	private String colorCar;

	@NotBlank(message = "the field responsibleName is required")
	private String responsibleName;

	@NotBlank(message = "the field apartament is required")
	private String apartament;

	@NotBlank(message = "the field block is required")
	private String block;

	public String getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public void setParkingSpotNumber(String parkingSpotNumber) {
		this.parkingSpotNumber = parkingSpotNumber;
	}

	public String getLicensePlateCar() {
		return licensePlateCar;
	}

	public void setLicensePlateCar(String licensePlateCar) {
		this.licensePlateCar = licensePlateCar;
	}

	public String getBrandCar() {
		return brandCar;
	}

	public void setBrandCar(String brandCar) {
		this.brandCar = brandCar;
	}

	public String getModelCar() {
		return modelCar;
	}

	public void setModelCar(String modelCar) {
		this.modelCar = modelCar;
	}

	public String getColorCar() {
		return colorCar;
	}

	public void setColorCar(String colorCar) {
		this.colorCar = colorCar;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getApartament() {
		return apartament;
	}

	public void setApartament(String apartament) {
		this.apartament = apartament;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

}
