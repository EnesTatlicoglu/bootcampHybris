package tr.nttdata.bootcamp.core.user.dao;

import de.hybris.platform.core.model.user.UserModel;

public interface NttUserDao {

    /**
     * Checks whether user has order
     * @param user user
     * @return true if user has order, false otherwise
     */
    boolean hasOrder(UserModel user);

}