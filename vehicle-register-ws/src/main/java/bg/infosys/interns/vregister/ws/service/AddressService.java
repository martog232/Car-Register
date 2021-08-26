package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.AddressDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Address;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.AddressDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.mapper.AddressMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class AddressService {
	private final AddressDAO addressDAO;
	private final AddressMapper addressMapper;
	
	@Autowired
	public AddressService(AddressDAO addressDAO, AddressMapper addressMapper) {
		this.addressDAO = addressDAO;
		this.addressMapper = addressMapper;
	}
	
	/**
	 * Returns list of all addresses. If there is no address in the database, returns an empty list.
	 * 
	 * @return list of all addresses
	 */
	public List<AddressDTO> findAll() {
		return addressDAO.findAll().stream()
								   .map(addressMapper::toDto)
								   .collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Returns page of addresses. If there is no address in the database, returns an empty list.
	 * 
	 * @param address address details (street, street number etc)
	 * @param cityName name of the city where the address is located
	 * @param isValid Variable indicating whether the address is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of addresses
	 */
	public Page<AddressDTO> findAllByFilter(String address, String cityName, Boolean isValid, PagingSorting pagingSorting) {
		List<Address> results = addressDAO.findAllByFilter(address, cityName, isValid, pagingSorting);
		
		return new Page<AddressDTO>(results.stream()
				                           .map(addressMapper::toDto)
				                           .collect(Collectors.toList()), addressDAO.countAllByFilter(address, cityName, isValid), pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * 
	 * Returns address with specified id.
	 * 
	 * @param id id of the address to return
	 * @return the address with the specified id
	 * @throws EntitiNotFoundException if the address with the specified id does not exist.
	 */
	public AddressDTO findById(Integer id) {
		return Optional.ofNullable(addressDAO.findById(id))
				       .map(addressMapper::toDto)
				       .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * 
	 * Creates new Address
	 * 
	 * @param addressDTO address to be created
	 * @return the address that is created
	 */
	@Transactional
	public  AddressDTO save(AddressDTO addressDTO) {
		addressDTO.setIsValid(true);
		Address newAddress = addressDAO.saveOrUpdate(addressMapper.toEntity(addressDTO));
		
		return addressMapper.toDto(newAddress);
	}
	
	/**
	 * 
	 * Updates address(details) and city of the Address with the specified id.
	 * 
	 * @param addressDTO addressDTO with updated address and city
	 * @param id id of the address to update
	 * @return the updated address
	 * @throws EntitiNotFoundException if the address with the specified id does not exist.
	 */
	@Transactional
	public AddressDTO update(AddressDTO addressDTO, Integer id) {
		Address updatedAddress = Optional.ofNullable(addressDAO.findById(id))
				                         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedAddress = addressDAO.saveOrUpdate(addressMapper.toUpdatedAddress(updatedAddress, addressDTO));
		
		return addressMapper.toDto(updatedAddress);
	}
	
	/**
	 * 
	 * Changes isValid of the specified address.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the address to change isValid
	 * @return the updated address
	 * @throws EntitiNotFoundException if the address with the specified id does not exist.
	 */
	@Transactional
	public AddressDTO changeIsValid(Integer id) {
		Address updatedAddress = Optional.ofNullable(addressDAO.findById(id))
								         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedAddress.setIsValid(!updatedAddress.getIsValid());
		updatedAddress = addressDAO.saveOrUpdate(updatedAddress);
				
		return addressMapper.toDto(updatedAddress);
	}
}
