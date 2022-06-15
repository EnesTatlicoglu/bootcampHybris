package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.core.model.CustomerReviewReactionModel;
import tr.nttdata.bootcamp.core.product.service.CustomerReviewReactionService;
import tr.nttdata.bootcamp.facades.product.data.CustomerReviewReactionData;
import tr.nttdata.bootcamp.facades.product.data.CustomerReviewReactionTypeData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CustomerReviewReactionPopulator implements Populator<CustomerReviewModel, ReviewData> {

    private CustomerReviewReactionService customerReviewReactionService;
    private UserService userService;

    private Converter<CustomerReviewReactionType, CustomerReviewReactionTypeData> customerReviewReactionTypeConverter;

    @Override
    public void populate(CustomerReviewModel source, ReviewData target) throws ConversionException {
        Map<CustomerReviewReactionType, Long> reactions = getCustomerReviewReactionService().getReactionCountsForReview(source);
        target.setReactions(reactions);
        List<CustomerReviewReactionData> userReactions = new ArrayList<>(CustomerReviewReactionType.values().length);
        for (CustomerReviewReactionType type : CustomerReviewReactionType.values()){
            CustomerReviewReactionData reviewReactionData = new CustomerReviewReactionData();
            reviewReactionData.setType(getCustomerReviewReactionTypeConverter().convert(type));
            reviewReactionData.setCount(reactions.containsKey(type) ? reactions.get(type) : 0);
            userReactions.add(reviewReactionData);
        }

        userReactions.sort(Comparator.comparing(t -> t.getType().getSequence()));
        target.setUserReactions(userReactions);

        CustomerReviewReactionModel reaction = getCustomerReviewReactionService()
                .getReactionForUserAndReview(getUserService().getCurrentUser(), source);
        if (reaction != null){
            target.setUserReaction(reaction.getReactionType());
        }
    }

    //GETTER && SETTER


    public CustomerReviewReactionService getCustomerReviewReactionService() {
        return customerReviewReactionService;
    }

    public void setCustomerReviewReactionService(CustomerReviewReactionService customerReviewReactionService) {
        this.customerReviewReactionService = customerReviewReactionService;
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
