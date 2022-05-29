package tr.nttdata.bootcamp.core.user.dao.impl;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import tr.nttdata.bootcamp.core.user.dao.NttUserDao;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DefaultNttUserDao implements NttUserDao {

    private static final String GET_ORDER_TOTAL_FOR_CUSTOMER = "SELECT SUM({o:" + AbstractOrderModel.TOTALPRICE + "})"+
            " FROM {" + OrderModel._TYPECODE + " AS o}"+
            " WHERE {o:" + OrderModel.VERSIONID + "} IS NULL " +
            " AND {o:" + OrderModel.USER + "} = ?customer" +
            " AND {o:" + OrderModel.DATE + "} > ?date";

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

    @Override
    public Double getOrderTotalForCustomer(CustomerModel customer, int timePeriod) {
        final FlexibleSearchQuery fsq = new FlexibleSearchQuery(GET_ORDER_TOTAL_FOR_CUSTOMER);
        fsq.addQueryParameter("customer", customer);
        fsq.addQueryParameter("date", DateUtils.truncate(DateUtils.addDays(new Date(), -timePeriod), Calendar.MINUTE));
        fsq.setCount(1);
        fsq.setResultClassList(Collections.singletonList(Double.class));
        List<Double> result = flexibleSearchService.<Double>search(fsq).getResult();
        if (CollectionUtils.isNotEmpty(result)){
            Double total = result.get(0);
            return total != null ? total : 0.0d;
        }
        return 0.0d;
    }
}