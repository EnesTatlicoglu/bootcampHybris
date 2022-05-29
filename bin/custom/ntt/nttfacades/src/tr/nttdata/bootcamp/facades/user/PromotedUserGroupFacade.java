package tr.nttdata.bootcamp.facades.user;

import tr.nttdata.bootcamp.facades.user.data.PromotedUserGroupData;

import java.util.List;

public interface PromotedUserGroupFacade {

    List<PromotedUserGroupData> getPromotedUserGroups();

}
