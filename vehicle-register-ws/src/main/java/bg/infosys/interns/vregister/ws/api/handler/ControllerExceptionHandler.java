package bg.infosys.interns.vregister.ws.api.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bg.infosys.interns.vregister.ws.api.error.APIError;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.exception.DuplicateEntityException;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);	
	private static final String STACK_TRACE_LOG = "Stack trace: ";
	
	@ExceptionHandler({EntityNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<APIError> handleException(EntityNotFoundException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		
        APIErrorCode code = APIErrorCode.ENTITY_NOT_FOUND;
        
        return ResponseEntity.status(404).body(new APIError(code.getCode(), code.getMessage(), code.getDescription()));
    }
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<APIError> handleException(MethodArgumentNotValidException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		
		 String errorMessage = (ex != null && ex.getBindingResult().getAllErrors().get(0).getDefaultMessage() != null ?
				 ex.getBindingResult().getAllErrors().get(0).getDefaultMessage() : "Unexpected error.");
		APIErrorCode code = APIErrorCode.METHOD_ARG_NOT_VALID;
		
		return ResponseEntity.status(400).body(new APIError(code.getCode(), code.getMessage(), errorMessage));
	}
	
	@ExceptionHandler({DuplicateEntityException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<APIError> handleException(DuplicateEntityException ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		
        APIErrorCode code = APIErrorCode.DUPLICATE_ENTITY;
        
        return ResponseEntity.status(409).body(new APIError(code.getCode(), code.getMessage(), code.getDescription()));
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<APIError> handleException(Exception ex) {
		LOGGER.error(STACK_TRACE_LOG, ex);
		
		APIErrorCode code = APIErrorCode.UNKNOWN_SERVER_EXCEPTION;
		
		return ResponseEntity.status(500).body(new APIError(code.getCode(), code.getMessage(), code.getDescription()));
	}
}
