package tr.nttdata.bootcamp.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(
        { FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidUrlConstraintValidator.class)
@Documented
public @interface ValidUrlConstraint
{
    String message() default "{tr.nttdata.bootcamp.constraints.ValidUrlConstraint.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String validProtocol() default "";
}