package bg.infosys.interns.vregister.ws.dto.mapper;

public interface IModelMapper<T, U> {
	T toDto(U entity);
	U toEntity(T dto);
}
