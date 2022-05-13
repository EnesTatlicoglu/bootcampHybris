package tr.nttdata.bootcamp.core.user.dao.impl;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import tr.nttdata.bootcamp.core.user.dao.NttUserDao;

import java.util.Collections;

public class DefaultNttUserDao implements NttUserDao {

    private static final String USER_HAS_ORDER_QUERY = "SELECT {" + ItemModel.PK + "}" +
            " FROM {" + OrderModel._TYPECODE + "}" +
            " WHERE {" + OrderModel.USER + "} = ?" + OrderModel.USER;

    private final FlexibleSearchService flexibleSearchService;

    public DefaultNttUserDao(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    @Override
    public boolean hasOrder(UserModel user) {
        final FlexibleSearchQuery fsq = new FlexibleSearchQuery(USER_HAS_ORDER_QUERY);
        fsq.addQueryParameter(OrderModel.USER, user);
        fsq.setCount(1);
        fsq.setResultClassList(Collections.singletonList(PK.class));
        return flexibleSearchService.search(fsq).getCount() > 0;
    }

}