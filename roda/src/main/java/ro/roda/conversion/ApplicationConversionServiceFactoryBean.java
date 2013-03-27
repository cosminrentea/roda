package ro.roda.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import ro.roda.domain.Catalog;
import ro.roda.domain.Users;

@RooConversionService
public class ApplicationConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}
    
    public Converter<Users, String> getUsersToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Users, java.lang.String>() {
            public String convert(Users users) {
                return new StringBuilder().append(users.getUsername()).toString();
            }
        };
    }

    public Converter<Catalog, String> getCatalogToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Catalog, java.lang.String>() {
            public String convert(Catalog catalog) {
                return new StringBuilder().append(catalog.getName()).append(" - ").append(catalog.getDescription()).toString();
            }
        };
    }

}
