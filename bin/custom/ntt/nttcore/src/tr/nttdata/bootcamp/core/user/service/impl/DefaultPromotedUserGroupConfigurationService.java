package tr.nttdata.bootcamp.core.user.service.impl;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import tr.nttdata.bootcamp.core.model.PromotedUserGroupConfigurationModel;
import tr.nttdata.bootcamp.core.user.dao.NttUserDao;
import tr.nttdata.bootcamp.core.user.dao.PromotedUserGroupConfigurationDao;
import tr.nttdata.bootcamp.core.user.service.PromotedUserGroupConfigurationService;

import java.util.List;

public class DefaultPromotedUserGroupConfigurationService implements PromotedUserGroupConfigurationService {

    private PromotedUserGroupConfigurationDao promotedUserGroupConfigurationDao;
    private NttUserDao nttUserDao;

    @Override
    public List<PromotedUserGroupConfigurationModel> getActiveConfigurations() {
        return getPromotedUserGroupConfigurationDao().getActiveConfigurations();
    }

    @Override
    public List<PromotedUserGroupConfigurationModel> getPassiveConfigurations() {
        return getPromotedUserGroupConfigurationDao().getPassiveConfigurations();
    }

    @Override
    public PromotedUserGroupConfigurationModel getConfigurationForUserGroup(UserGroupModel userGroup) {
        return getPromotedUserGroupConfigurationDao().getConfigurationForUserGroup(userGroup);
    }

    @Override
    public Double getTotalOrderAmountForCustomer(CustomerModel customer, PromotedUserGroupConfigurationModel config) {
        return getNttUserDao().getOrderTotalForCustomer(customer, config.getTimePeriod());
    }

    @Override
    public List<CustomerModel> getCustomersForPromotedGroup(UserGroupModel userGroup) {
        return getPromotedUserGroupConfigurationDao().getCustomersForPromotedGroup(userGroup);
    }

    @Override
    public List<CustomerModel> getCustomersShouldBeAddedToGroup(PromotedUserGroupConfigurationModel config) {
        return getPromotedUserGroupConfigurationDao().getCustomersShouldBeAddedToGroup(config);
    }

    @Override
    public List<CustomerModel> getCustomersShouldBeRemovedFromGroup(PromotedUserGroupConfigurationModel config) {
        return getPromotedUserGroupConfigurationDao().getCustomersShouldBeRemovedFromGroup(config);
    }


    public PromotedUserGroupConfigurationDao getPromotedUserGroupConfigurationDao() {
        return promotedUserGroupConfigurationDao;
    }

    public void setPromotedUserGroupConfigurationDao(PromotedUserGroupConfigurationDao promotedUserGroupConfigurationDao) {
        this.promotedUserGroupConfigurationDao = promotedUserGroupConfigurationDao;
    }

    public NttUserDao getNttUserDao() {
        return nttUserDao;
    }

    public void setNttUserDao(NttUserDao nttUserDao) {
        this.nttUserDao = nttUserDao;
    }
}
