package tr.nttdata.bootcamp.facades.user.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.core.model.PromotedUserGroupConfigurationModel;
import tr.nttdata.bootcamp.core.user.service.PromotedUserGroupConfigurationService;
import tr.nttdata.bootcamp.facades.user.PromotedUserGroupFacade;
import tr.nttdata.bootcamp.facades.user.data.PromotedUserGroupData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultPromotedUserGroupFacade implements PromotedUserGroupFacade {

    private PromotedUserGroupConfigurationService promotedUserGroupConfigurationService;
    private UserService userService;
    private Converter<PromotedUserGroupConfigurationModel, PromotedUserGroupData> promotedUserGroupConverter;

    @Override
    public List<PromotedUserGroupData> getPromotedUserGroups() {
        UserModel currentUser = getUserService().getCurrentUser();
        if (!(currentUser instanceof CustomerModel) || getUserService().isAnonymousUser(currentUser)){
            return Collections.emptyList();
        }

        final CustomerModel customer = (CustomerModel) currentUser;
        final List<PromotedUserGroupConfigurationModel> configurations = getPromotedUserGroupConfigurationService().getActiveConfigurations();
        if (CollectionUtils.isEmpty(configurations)){
            return Collections.emptyList();
        }

        final List<PromotedUserGroupData> promotedUserGroupData = new ArrayList<>(configurations.size());
        configurations.forEach(c -> {
            PromotedUserGroupData data = getPromotedUserGroupConverter().convert(c);
            data.setCustomerOrderTotal(getPromotedUserGroupConfigurationService().getTotalOrderAmountForCustomer(customer, c));
            promotedUserGroupData.add(data);
        });
        return promotedUserGroupData;
    }

    public PromotedUserGroupConfigurationService getPromotedUserGroupConfigurationService() {
        return promotedUserGroupConfigurationService;
    }

    public void setPromotedUserGroupConfigurationService(PromotedUserGroupConfigurationService promotedUserGroupConfigurationService) {
        this.promotedUserGroupConfigurationService = promotedUserGroupConfigurationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Converter<PromotedUserGroupConfigurationModel, PromotedUserGroupData> getPromotedUserGroupConverter() {
        return promotedUserGroupConverter;
    }

    public void setPromotedUserGroupConverter(Converter<PromotedUserGroupConfigurationModel, PromotedUserGroupData> promotedUserGroupConverter) {
        this.promotedUserGroupConverter = promotedUserGroupConverter;
    }
}
