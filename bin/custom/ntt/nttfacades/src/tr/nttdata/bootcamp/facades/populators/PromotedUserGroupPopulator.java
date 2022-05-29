package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import tr.nttdata.bootcamp.core.model.PromotedUserGroupConfigurationModel;
import tr.nttdata.bootcamp.facades.user.data.PromotedUserGroupData;

public class PromotedUserGroupPopulator implements Populator<PromotedUserGroupConfigurationModel, PromotedUserGroupData> {
    @Override
    public void populate(PromotedUserGroupConfigurationModel source, PromotedUserGroupData target) throws ConversionException {
        target.setDescription(source.getDescription());
        target.setThreshold(source.getOrderThreshold());
        target.setTimePeriod(source.getTimePeriod());
    }
}
