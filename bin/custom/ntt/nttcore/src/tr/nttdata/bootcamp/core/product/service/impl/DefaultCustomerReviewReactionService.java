package tr.nttdata.bootcamp.core.product.service.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.core.model.CustomerReviewReactionModel;
import tr.nttdata.bootcamp.core.product.dao.CustomerReviewReactionDao;
import tr.nttdata.bootcamp.core.product.service.CustomerReviewReactionService;
import tr.nttdata.bootcamp.core.user.service.UserIPService;

import java.util.Map;

public class DefaultCustomerReviewReactionService implements CustomerReviewReactionService {

    private CustomerReviewReactionDao customerReviewReactionDao;
    private UserService userService;
    private UserIPService userIPService;
    private ModelService modelService;

    @Override
    public Map<CustomerReviewReactionType, Long> getReactionCountsForReview(CustomerReviewModel review) {
        return getCustomerReviewReactionDao().getReactionCountsForReview(review);
    }

    @Override
    public CustomerReviewReactionModel getReactionForUserAndReview(UserModel user, CustomerReviewModel review) {
        if (getUserService().isAnonymousUser(user)){
            return getCustomerReviewReactionDao().getReactionForUserIpAndReview(getUserIPService().getUserIP(), review);
        }
        return getCustomerReviewReactionDao().getReactionForUserAndReview(user, review);
    }

    @Override
    public void storeReactionForUser(CustomerReviewModel review, CustomerReviewReactionType reactionType, UserModel user) {
        CustomerReviewReactionModel reaction = getReactionForUserAndReview(user, review);
        if (reaction == null){
            reaction = getModelService().create(CustomerReviewReactionModel.class);
            reaction.setUser(user);
            reaction.setReview(review);
            reaction.setUserIp(getUserIPService().getUserIP());
        }
        reaction.setReactionType(reactionType);
        getModelService().save(reaction);
    }

    @Override
    public void removeReactionForUser(CustomerReviewModel review, UserModel user) {
        CustomerReviewReactionModel reaction = getReactionForUserAndReview(user, review);
        if (reaction != null){
            getModelService().remove(reaction);
        }
    }

    public CustomerReviewReactionDao getCustomerReviewReactionDao() {
        return customerReviewReactionDao;
    }

    public void setCustomerReviewReactionDao(CustomerReviewReactionDao customerReviewReactionDao) {
        this.customerReviewReactionDao = customerReviewReactionDao;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserIPService getUserIPService() {
        return userIPService;
    }

    public void setUserIPService(UserIPService userIPService) {
        this.userIPService = userIPService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
