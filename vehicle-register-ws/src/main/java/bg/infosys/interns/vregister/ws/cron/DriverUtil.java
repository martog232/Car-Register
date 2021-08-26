package bg.infosys.interns.vregister.ws.cron;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import bg.infosys.interns.vregister.ws.dto.AddressDTO;
import bg.infosys.interns.vregister.ws.dto.DriverDTO;
import bg.infosys.interns.vregister.ws.service.AddressService;

@Service
public class DriverUtil {

	private static Random random = new Random();
	
	private AddressService addressService;

	public DriverUtil(AddressService addressService) {
		this.addressService = addressService;
	}

	public DriverDTO generateDriver() {
		DriverDTO newDriver = new DriverDTO();
		
		newDriver.setName(generateDriverName());
		newDriver.setAddress(generateAddress());
		newDriver.setAge(generageAge(20,80));
		
		return newDriver;
	}
	
	public static final String[] DRIVER_NAME = { "Claudio", "Carrara", "Josiah", "Littler", "Tomi", "Hite", "Barrie",
			"Reisman", "Trista", "Kleinschmidt", "Joslyn", "Grote", "Lue", "Crowson", "Richelle", "Espinal", " Roxanne",
			"Whitmer", "Anja", "Schack", "Nichole", "Rasnick", "Byron", "Kitson", "Josef", "Husband", "Vernetta",
			"Markee", "Humberto", "Wolski", "Jackie", "Philyaw", "Carson", "Lenton", "Shaniqua", "Fogel", "Jeffie",
			" Barraza", "Lilliana", "Forshee" };

	public static String generateDriverName() {return DRIVER_NAME[random.nextInt(DRIVER_NAME.length)];}
	
	private AddressDTO generateAddress() {
		List<AddressDTO> addresses = addressService.findAll();
		int index = random.nextInt(addresses.size());
		return addresses.get(index);
	}

	public static Short generageAge(int min,int max) {
		return (short) (min + (short) Math.round(Math.random() * (max - min)));
		}
}
