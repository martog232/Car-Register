package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bg.infosys.interns.vregister.core.dao.BrandDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Brand;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.BrandDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.mapper.BrandMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class BrandService {
	private final BrandDAO brandDAO;
	private final BrandMapper brandMapper;

	@Autowired
	public BrandService(BrandDAO brandDAO, BrandMapper brandMapper) {
		this.brandDAO = brandDAO;
		this.brandMapper = brandMapper;
	}

	/**
	 * Returns all brands
	 * 
	 * @return list of brands
	 */
	public List<BrandDTO> findAll() {
		return brandDAO.findAll().stream().map(s -> brandMapper.toDto(s)).collect(Collectors.toList());
	}

	/**
	 * 
	 * Returns page of brands. If there is no Brand in the database, returns an
	 * empty list.
	 * 
	 * @param name          name of the Brand
	 * @param countryName   name of the country where the Brand is located
	 * @param isValid Variable indicating whether the brand is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of Brands
	 */
	public Page<BrandDTO> findAllByFilter(String name, String countryName, Boolean isValid, PagingSorting pagingSorting) {
		List<Brand> results = brandDAO.findAllByFilter(name, countryName, isValid, pagingSorting);

		return new Page<BrandDTO>(results.stream().map(brandMapper::toDto).collect(Collectors.toList()),
				brandDAO.countAllByFilter(name, countryName, isValid), pagingSorting.getPageNumber(),
				pagingSorting.getPageSize());
	}

	/**
	 * Returns the service type matching the passed id
	 * 
	 * @param BrandId id of the service type
	 * @return Brand with the passed id
	 * @throws EntitiNotFoundException if the service type with the specified id
	 *                                 doesn't exists.
	 */
	public BrandDTO findById(Integer id) {
		return Optional.ofNullable(brandDAO.findById(id)).map(brandMapper::toDto)
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}

	/**
	 * Creates new Brand
	 * 
	 * @param BrandDTO to be created
	 * @return created new Brand
	 */
	@Transactional
	public BrandDTO save(BrandDTO brandDTO) {
		brandDTO.setIsValid(true);
		Brand brand = brandDAO.saveOrUpdate(brandMapper.toEntity(brandDTO));

		return brandMapper.toDto(brand);
	}

	/**
	 * Updates the Brand's name description and code with given id
	 * 
	 * @param BrandDTO with updated parameters
	 * @param BrandId  id of Brand for update
	 * @return update Brand
	 */
	@Transactional
	public BrandDTO update(BrandDTO BrandDTO, Integer id) {
		Brand updatedBrand = Optional.ofNullable(brandDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedBrand = brandDAO.saveOrUpdate(brandMapper.toUpdatedBrand(updatedBrand, BrandDTO));

		return brandMapper.toDto(updatedBrand);
	}

	/**
	 * Changes activity of the specified CarType.
	 * 
	 * If the CarType is valid the method changes the CarType to invalid. If the
	 * CarType is invalid the method changes the CarType to valid.
	 * 
	 * @param id id of the CarType to change activity
	 * @return the updated CarType
	 * @throws EntitiNotFoundException if the CarType with the specified id does not
	 *                                 exist.
	 */
	@Transactional
	public BrandDTO changeIsValid(Integer id) {
		Brand updatedCarType = Optional.ofNullable(brandDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));

		updatedCarType.setIsValid(!updatedCarType.getIsValid());
		updatedCarType = brandDAO.saveOrUpdate(updatedCarType);

		return brandMapper.toDto(updatedCarType);
	}
}
