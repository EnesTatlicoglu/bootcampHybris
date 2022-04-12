package tr.nttdata.bootcamp.impex;

import de.hybris.platform.impex.jalo.translators.AbstractValueTranslator;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.enums.BadgeStatus;

public class BadgeStatusTranslator extends AbstractValueTranslator {

    private static final Logger LOG = LoggerFactory.getLogger(BadgeStatusTranslator.class);

    @Override
    public Object importValue(String val, Item item) throws JaloInvalidParameterException {
        LOG.info("Entered translator for import with value {} and item {}", val, item);
        return "X".equals(val) ? BadgeStatus.ACTIVE : BadgeStatus.PASSIVE;
    }

    @Override
    public String exportValue(Object object) throws JaloInvalidParameterException {
        LOG.info("Entered translator for export with value {}", object);
        if(object instanceof EnumerationValue){
            return BadgeStatus.ACTIVE.getCode().equals(((EnumerationValue) object).getCode()) ? "X" : "";
        }
        return "";
    }

}