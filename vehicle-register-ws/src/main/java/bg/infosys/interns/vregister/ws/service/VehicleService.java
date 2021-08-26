package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.VehicleDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Vehicle;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.VehicleDTO;
import bg.infosys.interns.vregister.ws.dto.mapper.VehicleMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class VehicleService {
	private final VehicleDAO vehicleDAO;
	private final VehicleMapper vehicleMapper;
	
	@Autowired
	public VehicleService(VehicleDAO vehicleDAO, VehicleMapper vehicleMapper) {
		this.vehicleDAO = vehicleDAO;
		this.vehicleMapper = vehicleMapper;
	}
	
	/**
	 * Returns list of all Vehicles. If there is no Vehicle in the database, returns an empty list.
	 * 
	 * @return list of all Vehicles
	 */
	public List<VehicleDTO> findAll() {
		return vehicleDAO.findAll().stream()
								   .map(vehicleMapper::toDto)
								   .collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Returns page of Vehicles. If there is no Vehicle in the database, returns an empty list.
	 * 
	 * @param year Year in which the car was manufactured
	 * @param carTypeName Type of the vehicle
	 * @param seats Number of seats that the vehicle has
	 * @param doors Number of doors that the vehicle has
	 * @param color Color of the vehicle
	 * @param power Horse power that the vehicle has
	 * @param fuelTypeName Fuel type of the vehicle
	 * @param countryName Country where the vehicle is manufacture
	 * @param driverName Driver of the vehicle
	 * @param brandName Brand of the vehicle
	 * @param regNumber Registration number of the vehicle
	 * @param isValid Variable indicating whether the vehicle is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of Vehicles
	 */ 
	public Page<VehicleDTO> findAllByFilter(Integer year, 
                                            String carTypeName, 
                                            Integer seats, 
                                            Integer doors, 
                                            String color, 
                                            Integer power, 
                                            String fuelTypeName, 
                                            String countryName, 
                                            String driverName, 
                                            String brandName, 
                                            String regNumber, 
                                            Boolean isValid, 
                                            PagingSorting pagingSorting) {
		List<Vehicle> results = vehicleDAO.findAllByFilter(year, carTypeName, seats, doors, color, power, 
				fuelTypeName, countryName, driverName, brandName, regNumber, isValid, pagingSorting);
		
		return new Page<VehicleDTO>(results.stream()
				                           .map(vehicleMapper::toDto)
				                           .collect(Collectors.toList()), 
				                           vehicleDAO.countAllByFilter(
		year, carTypeName, seats, doors, color, power, fuelTypeName, countryName, driverName, brandName, regNumber, isValid), 
		                                   pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * 
	 * Returns Vehicle with specified id.
	 * 
	 * @param id id of the Vehicle to return
	 * @return the Vehicle with the specified id
	 * @throws EntitiNotFoundException if the Vehicle with the specified id does not exist.
	 */
	public VehicleDTO findById(Integer id) {
		return Optional.ofNullable(vehicleDAO.findById(id))
				       .map(vehicleMapper::toDto)
				       .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * 
	 * Creates new Vehicle
	 * 
	 * @param vehicleDTO vehicle to be created
	 * @return the vehicle that is created
	 */
	@Transactional
	public  VehicleDTO save(VehicleDTO vehicleDTO) {
		vehicleDTO.setIsValid(true);
		Vehicle newVehicle = vehicleDAO.saveOrUpdate(vehicleMapper.toEntity(vehicleDTO));
		
		return vehicleMapper.toDto(newVehicle);
	}
	
	/**
	 * 
	 * Updates year, carTypeName, seats, doors, color, power, fuelTypeName, 
	 * countryName, driverName, brandName and regNumber of the Vehicle with the specified id.
	 * 
	 * @param vehicleDTO vehicleDTO with updated parameters
	 * @param id id of the Vehicle to update
	 * @return the updated Vehicle
	 * @throws EntitiNotFoundException if the Vehicle with the specified id does not exist.
	 */
	@Transactional
	public VehicleDTO update(VehicleDTO vehicleDTO, Integer id) {
		Vehicle updatedVehicle = Optional.ofNullable(vehicleDAO.findById(id))
				                         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedVehicle = vehicleDAO.saveOrUpdate(vehicleMapper.toUpdatedVehicle(updatedVehicle, vehicleDTO));
		
		return vehicleMapper.toDto(updatedVehicle);
	}
	
	/**
	 * 
	 * Changes isValid of the specified Vehicle.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the Vehicle to change isValid
	 * @return the updated Vehicle
	 * @throws EntitiNotFoundException if the Vehicle with the specified id does not exist.
	 */
	@Transactional
	public VehicleDTO changeIsValid(Integer id) {
		Vehicle updatedVehicle = Optional.ofNullable(vehicleDAO.findById(id))
								         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedVehicle.setIsValid(!updatedVehicle.getIsValid());
		updatedVehicle = vehicleDAO.saveOrUpdate(updatedVehicle);
				
		return vehicleMapper.toDto(updatedVehicle);
	}
}
