package bg.infosys.interns.vregister.ws.api.error;

public class APIError {
	private String code;
	private String name;
	private String description;
	
	public APIError(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}
