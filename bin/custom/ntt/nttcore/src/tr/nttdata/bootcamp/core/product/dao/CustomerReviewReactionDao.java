package tr.nttdata.bootcamp.core.product.dao;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.core.model.CustomerReviewReactionModel;

import java.util.Map;

public interface CustomerReviewReactionDao {

    Map<CustomerReviewReactionType, Long> getReactionCountsForReview(CustomerReviewModel review);
    CustomerReviewReactionModel getReactionForUserAndReview(UserModel user, CustomerReviewModel review);
    CustomerReviewReactionModel getReactionForUserIpAndReview(String userIp, CustomerReviewModel review);
}
