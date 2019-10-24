package realestate.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realestate.utils.HtmlFileReader;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class AppBeanConfig {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public HtmlFileReader htmlFileReader() {
        return new HtmlFileReader();
    }
}
