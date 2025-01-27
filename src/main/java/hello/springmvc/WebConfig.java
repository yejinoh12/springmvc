package hello.springmvc;

import hello.springmvc.mvc2.typeconverter.converter.IntegerToStringConverter;
import hello.springmvc.mvc2.typeconverter.converter.IpPortToStringConverter;
import hello.springmvc.mvc2.typeconverter.converter.StringToIntegerConverter;
import hello.springmvc.mvc2.typeconverter.converter.StringToIpPortConverter;
import hello.springmvc.mvc2.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry){

        //registry.addConverter(new StringToIntegerConverter());
        //registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addConverter(new StringToIpPortConverter());

        //추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
