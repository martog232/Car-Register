package bg.infosys.interns.vregister.ws.cron;

import org.apache.log4j.Logger;

import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.ws.dto.DriverDTO;
import bg.infosys.interns.vregister.ws.service.DriverService;

@Component
public class ScheduledTasks {
	private static final Logger logger=Logger.getLogger(ScheduledTask.class);
	private final DriverUtil driverUtil;
	private final DriverService driverService;
	
	public ScheduledTasks(DriverService driverService,DriverUtil driverUtil) {
		this.driverService = driverService;
		this.driverUtil=driverUtil;
	}
	
	public void generateDriver() {
		DriverDTO newDriver = driverUtil.generateDriver();
		driverService.save(newDriver);
		logger.info(newDriver.getName());
	}
	
}
