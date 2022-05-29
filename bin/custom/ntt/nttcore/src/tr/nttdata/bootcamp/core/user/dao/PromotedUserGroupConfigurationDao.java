package tr.nttdata.bootcamp.core.user.dao;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import tr.nttdata.bootcamp.core.model.PromotedUserGroupConfigurationModel;

import java.util.List;

public interface PromotedUserGroupConfigurationDao {

    List<PromotedUserGroupConfigurationModel> getActiveConfigurations();
    List<PromotedUserGroupConfigurationModel> getPassiveConfigurations();

    PromotedUserGroupConfigurationModel getConfigurationForUserGroup(UserGroupModel userGroup);

    List<CustomerModel> getCustomersForPromotedGroup(UserGroupModel userGroup);

    List<CustomerModel> getCustomersShouldBeAddedToGroup(PromotedUserGroupConfigurationModel config);
    List<CustomerModel> getCustomersShouldBeRemovedFromGroup(PromotedUserGroupConfigurationModel config);

}
