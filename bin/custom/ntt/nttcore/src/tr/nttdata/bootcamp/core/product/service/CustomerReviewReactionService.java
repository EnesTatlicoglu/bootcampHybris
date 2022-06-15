package tr.nttdata.bootcamp.core.product.service;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.core.model.CustomerReviewReactionModel;

import java.util.Map;

public interface CustomerReviewReactionService {

    Map<CustomerReviewReactionType, Long> getReactionCountsForReview(CustomerReviewModel review);

    CustomerReviewReactionModel getReactionForUserAndReview(UserModel user, CustomerReviewModel review);

    void storeReactionForUser(CustomerReviewModel review, CustomerReviewReactionType reactionType, UserModel user);

    void removeReactionForUser(CustomerReviewModel review, UserModel user);


}
