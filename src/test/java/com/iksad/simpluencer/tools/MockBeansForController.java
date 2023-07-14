package com.iksad.simpluencer.tools;

import com.iksad.simpluencer.Properties.ServerProperties;
import com.iksad.simpluencer.exception.ExceptionParserFactory.ExceptionParserFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(ExceptionParserFactory.class)
@Import({ServerProperties.class})
public @interface MockBeansForController {
}
