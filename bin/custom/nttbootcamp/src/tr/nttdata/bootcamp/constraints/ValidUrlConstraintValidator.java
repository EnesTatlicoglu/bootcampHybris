package tr.nttdata.bootcamp.constraints;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URL;

public class ValidUrlConstraintValidator implements ConstraintValidator<ValidUrlConstraint, String> {

    private String validProtocol;

    @Override
    public void initialize(ValidUrlConstraint annotation) {
        validProtocol = annotation.validProtocol();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value) || StringUtils.isEmpty(validProtocol)){
            return true;
        }

        try {
            URL url = new URL(value);
            return validProtocol.equals(url.getProtocol());
        } catch (Exception ex){
            // Do nothing
        }
        return false;
    }
}