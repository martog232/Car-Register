package bg.infosys.interns.vregister.ws.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
	@Bean
	  public Docket petApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .select()
	        .apis(RequestHandlerSelectors.basePackage("bg.infosys.interns.vregister.ws.api"))
	        .paths(PathSelectors.any())
	        .build()
	    	.apiInfo(apiInfo());
	  }
	  
	  private ApiInfo apiInfo() {
		    return new ApiInfo(
		      "Infosys Academy Vehicle Register 2021", 
		      "Infosys Academy Vehicle Register 2021 API documentation", 
		      "v.1.0", 
		      "-", 
		      new Contact("Infosystems International Ltd.", "www.infosys.bg", "office@infosys.bg"), 
		      "-", "-", Collections.emptyList());
	  }
}
