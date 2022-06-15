package tr.nttdata.bootcamp.core.product.dao.impl;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.core.model.CustomerReviewReactionModel;
import tr.nttdata.bootcamp.core.product.dao.CustomerReviewReactionDao;

import java.util.*;

public class DefaultCustomerReviewReactionDao extends DefaultGenericDao<CustomerReviewReactionModel>
        implements CustomerReviewReactionDao {

    private String REACTION_COUNT_FOR_REVIEW_QUERY = "SELECT {r:" + CustomerReviewReactionModel.REACTIONTYPE + "}," +
            " COUNT({r:" + ItemModel.PK + "}) " +
            " FROM {" + CustomerReviewReactionModel._TYPECODE + " AS r}" +
            " WHERE {r:" + CustomerReviewReactionModel.REVIEW + "} = ?" + CustomerReviewReactionModel.REVIEW +
            " GROUP BY {r:" + CustomerReviewReactionModel.REACTIONTYPE + "}";

    public DefaultCustomerReviewReactionDao() {
        super(CustomerReviewReactionModel._TYPECODE);
    }

    @Override
    public Map<CustomerReviewReactionType, Long> getReactionCountsForReview(CustomerReviewModel review) {
        Map<CustomerReviewReactionType, Long> resultMap = new EnumMap<>(CustomerReviewReactionType.class);
        if (review == null){
            return resultMap;
        }
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(REACTION_COUNT_FOR_REVIEW_QUERY);
        fsq.addQueryParameter(CustomerReviewReactionModel.REVIEW, review);
        fsq.setResultClassList(Arrays.asList(CustomerReviewReactionType.class, Long.class));
        List<List<Object>> result = getFlexibleSearchService().<List<Object>>search(fsq).getResult();
        if (CollectionUtils.isNotEmpty(result)){
            result.forEach(r -> resultMap.put((CustomerReviewReactionType)r.get(0), (Long) r.get(1)));
        }
        return resultMap;
    }

    @Override
    public CustomerReviewReactionModel getReactionForUserAndReview(UserModel user, CustomerReviewModel review) {
        if (user == null ||review == null){
            return null;
        }
        Map<String, Object> params = new HashMap<>(2);
        params.put(CustomerReviewReactionModel.USER, user);
        params.put(CustomerReviewReactionModel.REVIEW, review);
        List<CustomerReviewReactionModel> result = find(params);
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    @Override
    public CustomerReviewReactionModel getReactionForUserIpAndReview(String userIp, CustomerReviewModel review) {
        if (userIp == null || review == null){
            return null;
        }
        Map<String, Object> params = new HashMap<>(2);
        params.put(CustomerReviewReactionModel.USERIP, userIp);
        params.put(CustomerReviewReactionModel.REVIEW, review);
        List<CustomerReviewReactionModel> result = find(params);
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }
}
