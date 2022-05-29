package tr.nttdata.bootcamp.core.user.dao.impl;

import de.hybris.platform.commerceservices.enums.CustomerType;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.link.LinkModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.internal.dao.SortParameters;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import tr.nttdata.bootcamp.core.model.PromotedUserGroupConfigurationModel;
import tr.nttdata.bootcamp.core.user.dao.PromotedUserGroupConfigurationDao;

import java.util.*;

public class DefaultPromotedUserGroupConfigurationDao extends DefaultGenericDao<PromotedUserGroupConfigurationModel>
        implements PromotedUserGroupConfigurationDao {

    private static final String CUSTOMERS_WITH_PROMOTED_USER_GROUP = "SELECT {c:" + ItemModel.PK + "} " +
            "FROM {" + CustomerModel._TYPECODE + " AS c} WHERE {c:" + CustomerModel.PROMOTEDUSERGROUP + "} = ?userGroup";

    private static final String CUSTOMERS_SHOULD_BE_ADDED_TO_GROUP = "SELECT {c:" + ItemModel.PK + "}"+
            " FROM {" + OrderModel._TYPECODE + " AS o " +
            " JOIN " + CustomerModel._TYPECODE + " AS c ON {c:" + ItemModel.PK + "}={o:" + AbstractOrderModel.USER + "}" +
            "}" +
            " WHERE {o:" + OrderModel.VERSIONID + "} IS NULL " +
            " AND {c:" + CustomerModel.TYPE + "}=?type" +
            " AND {o:" + AbstractOrderModel.DATE + "} > ?date" +
            " AND {c:" + CustomerModel.PROMOTEDUSERGROUP + "} IS NULL " + //TODO check for lowest priority
            " GROUP BY {c:" + ItemModel.PK + "}" +
            " HAVING SUM({o:" + AbstractOrderModel.TOTALPRICE + "}) > ?threshold";

    private static final String CUSTOMERS_SHOULD_BE_REMOVED_FROM_GROUP = "SELECT {c:" + ItemModel.PK + "} "+
            " FROM {" + CustomerModel._TYPECODE + " AS c" +
            "  LEFT JOIN " + OrderModel._TYPECODE + " AS o ON {c:" + ItemModel.PK + "}={o:" + AbstractOrderModel.USER + "}" +
            "}" +
            " WHERE {o:" + OrderModel.VERSIONID + "} IS NULL" +
            " AND {c:" + CustomerModel.TYPE + "}=?type" +
            " AND {o:" + AbstractOrderModel.DATE + "} > ?date" +
            " AND {c:" + CustomerModel.PROMOTEDUSERGROUP + "} = ?userGroup" +
            " GROUP BY {c:" + ItemModel.PK + "} " +
            " HAVING SUM({o:" + AbstractOrderModel.TOTALPRICE + "}) < ?threshold "+
            " OR SUM(({o:" + AbstractOrderModel.TOTALPRICE + "})) IS NULL";

    public DefaultPromotedUserGroupConfigurationDao() {
        super(PromotedUserGroupConfigurationModel._TYPECODE);
    }

    @Override
    public List<PromotedUserGroupConfigurationModel> getActiveConfigurations() {
        return find(Collections.singletonMap(PromotedUserGroupConfigurationModel.ACTIVE, Boolean.TRUE),
                SortParameters.singletonDescending(PromotedUserGroupConfigurationModel.PRIORITY));
    }

    @Override
    public List<PromotedUserGroupConfigurationModel> getPassiveConfigurations() {
        return find(Collections.singletonMap(PromotedUserGroupConfigurationModel.ACTIVE, Boolean.FALSE));
    }

    @Override
    public PromotedUserGroupConfigurationModel getConfigurationForUserGroup(UserGroupModel userGroup) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put(PromotedUserGroupConfigurationModel.USERGROUP, userGroup);
        params.put(PromotedUserGroupConfigurationModel.ACTIVE, Boolean.TRUE);
        List<PromotedUserGroupConfigurationModel> result = find(params);
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    @Override
    public List<CustomerModel> getCustomersForPromotedGroup(UserGroupModel userGroup) {
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(CUSTOMERS_WITH_PROMOTED_USER_GROUP);
        fsq.setResultClassList(Collections.singletonList(CustomerModel.class));
        fsq.addQueryParameter("userGroup", userGroup);
        return getFlexibleSearchService().<CustomerModel>search(fsq).getResult();
    }

    @Override
    public List<CustomerModel> getCustomersShouldBeAddedToGroup(PromotedUserGroupConfigurationModel config) {
        return query(config, CUSTOMERS_SHOULD_BE_ADDED_TO_GROUP);
    }

    @Override
    public List<CustomerModel> getCustomersShouldBeRemovedFromGroup(PromotedUserGroupConfigurationModel config) {
        return query(config, CUSTOMERS_SHOULD_BE_REMOVED_FROM_GROUP);
    }

    private List<CustomerModel> query(PromotedUserGroupConfigurationModel config, String queryString){
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(queryString);
        fsq.setResultClassList(Collections.singletonList(CustomerModel.class));
        fsq.addQueryParameter("type", CustomerType.REGISTERED);
        fsq.addQueryParameter("userGroup", config.getUserGroup());
        fsq.addQueryParameter("threshold", config.getOrderThreshold());
        fsq.addQueryParameter("date", DateUtils.truncate(DateUtils.addDays(new Date(), -config.getTimePeriod()), Calendar.MINUTE));
        return getFlexibleSearchService().<CustomerModel>search(fsq).getResult();
    }
}



