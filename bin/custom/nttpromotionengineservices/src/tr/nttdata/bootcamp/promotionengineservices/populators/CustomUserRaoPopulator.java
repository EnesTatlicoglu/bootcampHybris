package tr.nttdata.bootcamp.promotionengineservices.populators;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ruleengineservices.converters.populator.UserRaoPopulator;
import de.hybris.platform.ruleengineservices.rao.UserGroupRAO;
import de.hybris.platform.ruleengineservices.rao.UserRAO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.LinkedHashSet;

public class CustomUserRaoPopulator extends UserRaoPopulator {

    @Override
    public void populate(UserModel source, UserRAO target) {
        super.populate(source, target);
        if (source instanceof CustomerModel){
            CustomerModel customer = (CustomerModel) source;
            UserGroupModel promotedUserGroup = customer.getPromotedUserGroup();
            if (promotedUserGroup != null){
                UserGroupRAO userGroupRAO = getUserGroupConverter().convert(promotedUserGroup);
                if(CollectionUtils.isEmpty(target.getGroups())){
                    target.setGroups(new LinkedHashSet(Collections.singleton(userGroupRAO)));
                }else{
                    target.getGroups().add(userGroupRAO);
                }
            }
        }

    }

}
