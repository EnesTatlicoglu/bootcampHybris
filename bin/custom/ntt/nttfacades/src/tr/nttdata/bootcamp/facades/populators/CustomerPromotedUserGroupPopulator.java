package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import tr.nttdata.bootcamp.core.model.PromotedUserGroupConfigurationModel;
import tr.nttdata.bootcamp.core.user.service.PromotedUserGroupConfigurationService;
import tr.nttdata.bootcamp.facades.user.data.PromotedUserGroupData;

public class CustomerPromotedUserGroupPopulator implements Populator<CustomerModel, CustomerData> {

    private PromotedUserGroupConfigurationService promotedUserGroupConfigurationService;
    private Converter<PromotedUserGroupConfigurationModel, PromotedUserGroupData> promotedUserGroupConverter;

    @Override
    public void populate(CustomerModel source, CustomerData target) throws ConversionException {
        UserGroupModel userGroup = source.getPromotedUserGroup();
        if (userGroup != null) {
            PromotedUserGroupConfigurationModel config = getPromotedUserGroupConfigurationService().getConfigurationForUserGroup(userGroup);
            if (config != null) {
                target.setPromotedUserGroup(getPromotedUserGroupConverter().convert(config));
            }
        }
    }

    public PromotedUserGroupConfigurationService getPromotedUserGroupConfigurationService() {
        return promotedUserGroupConfigurationService;
    }

    public void setPromotedUserGroupConfigurationService(PromotedUserGroupConfigurationService promotedUserGroupConfigurationService) {
        this.promotedUserGroupConfigurationService = promotedUserGroupConfigurationService;
    }

    public Converter<PromotedUserGroupConfigurationModel, PromotedUserGroupData> getPromotedUserGroupConverter() {
        return promotedUserGroupConverter;
    }

    public void setPromotedUserGroupConverter(Converter<PromotedUserGroupConfigurationModel, PromotedUserGroupData> promotedUserGroupConverter) {
        this.promotedUserGroupConverter = promotedUserGroupConverter;
    }
}
