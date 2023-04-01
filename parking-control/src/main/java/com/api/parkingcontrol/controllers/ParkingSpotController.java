package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.Any;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
	final ParkingSpotService parkingSpotService;

	public ParkingSpotController(ParkingSpotService parkingSpotService) {
		this.parkingSpotService = parkingSpotService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingSpots(@PathVariable(value = "id") UUID id){
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		
		if(!parkingSpotModelOptional.isPresent()) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Parking Spot not found!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}

	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {

		if (parkingSpotService.existesByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Conflict: License Plate is already in use!");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}

		if (parkingSpotService.existesByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Conflict: Parking Spot is already in use!");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}

		if (parkingSpotService.existesByApartamentAndBlock(parkingSpotDto.getApartament(), parkingSpotDto.getBlock())) {
			Map<String, String> error = new HashMap<>();
			error.put("error", "Conflict: Parking Spot is already registred for this apartament/block!");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
		}

		var parkingSpotModel = new ParkingSpotModel();

		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);

		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}

//	padrão para capturar erros
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> msg = new ArrayList<>();
		Map<String, List<String>> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			msg.add(errorMessage);
			System.out.println(errors);
		});

		errors.put("errors", msg);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

}
