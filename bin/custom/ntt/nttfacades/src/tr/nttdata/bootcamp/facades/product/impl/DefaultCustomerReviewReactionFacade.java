package tr.nttdata.bootcamp.facades.product.impl;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.enumeration.EnumerationValueModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.core.model.CustomerReviewReactionModel;
import tr.nttdata.bootcamp.core.product.service.CustomerReviewReactionService;
import tr.nttdata.bootcamp.facades.product.CustomerReviewReactionFacade;
import tr.nttdata.bootcamp.facades.product.data.CustomerReviewReactionTypeData;

import java.util.*;

public class DefaultCustomerReviewReactionFacade implements CustomerReviewReactionFacade {

    private CustomerReviewReactionService customerReviewReactionService;
    private ModelService modelService;
    private UserService userService;
    private Converter<CustomerReviewReactionType, CustomerReviewReactionTypeData> customerReviewReactionTypeConverter;

    @Override
    public List<CustomerReviewReactionTypeData> getReactionTypes() {
        List<CustomerReviewReactionTypeData> list = getCustomerReviewReactionTypeConverter().convertAll(Arrays.asList(CustomerReviewReactionType.values()));
        list.sort(Comparator.comparing(CustomerReviewReactionTypeData :: getSequence));
        return list;
    }

    @Override
    public Map<CustomerReviewReactionType, Long> getReactionCountsForReview(String reviewId) {
        return getCustomerReviewReactionService().getReactionCountsForReview(getCustomerReviewForId(reviewId));
    }

    @Override
    public CustomerReviewReactionType getReactionForCurrentCustomer(String reviewId) {
        CustomerReviewReactionModel reaction = getCustomerReviewReactionService().
                getReactionForUserAndReview(getUserService().getCurrentUser(), getCustomerReviewForId(reviewId));

        return reaction != null ? reaction.getReactionType() : null;
    }

    @Override
    public void storeReactionForCurrentUser(String reviewId, CustomerReviewReactionType reactionType) {
        getCustomerReviewReactionService().
                storeReactionForUser(getCustomerReviewForId(reviewId), reactionType, getUserService().getCurrentUser());
    }

    @Override
    public void removeReactionForCurrentUser(String reviewId) {
        getCustomerReviewReactionService().
                removeReactionForUser(getCustomerReviewForId(reviewId), getUserService().getCurrentUser());

    }

    private CustomerReviewModel getCustomerReviewForId(String reviewId){
        try {
            ItemModel item = getModelService().get(PK.parse(reviewId));
            if (item instanceof CustomerReviewModel){
                return (CustomerReviewModel) item;
            }
        }catch (Exception e){

        }
        return null;
    }

    public CustomerReviewReactionService getCustomerReviewReactionService() {
        return customerReviewReactionService;
    }

    public void setCustomerReviewReactionService(CustomerReviewReactionService customerReviewReactionService) {
        this.customerReviewReactionService = customerReviewReactionService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Converter<CustomerReviewReactionType, CustomerReviewReactionTypeData> getCustomerReviewReactionTypeConverter() {
        return customerReviewReactionTypeConverter;
    }

    public void setCustomerReviewReactionTypeConverter(Converter<CustomerReviewReactionType, CustomerReviewReactionTypeData> customerReviewReactionTypeConverter) {
        this.customerReviewReactionTypeConverter = customerReviewReactionTypeConverter;
    }
}
