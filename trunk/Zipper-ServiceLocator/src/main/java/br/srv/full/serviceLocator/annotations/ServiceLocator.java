/**
 * 
 */
package br.srv.full.serviceLocator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Carlos Delfino
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,ElementType.FIELD,ElementType.METHOD})
public @interface ServiceLocator {
	String  value();
	ServiceType type() default ServiceType.Properties;

}
