package tr.nttdata.bootcamp.facades.product;

import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.facades.product.data.CustomerReviewReactionTypeData;

import java.util.List;
import java.util.Map;

public interface CustomerReviewReactionFacade {

    List<CustomerReviewReactionTypeData> getReactionTypes();

    Map<CustomerReviewReactionType, Long> getReactionCountsForReview(String reviewId);

    CustomerReviewReactionType getReactionForCurrentCustomer(String reviewId);

    void storeReactionForCurrentUser(String reviewId, CustomerReviewReactionType reactionType);

    void removeReactionForCurrentUser(String reviewId);
}
