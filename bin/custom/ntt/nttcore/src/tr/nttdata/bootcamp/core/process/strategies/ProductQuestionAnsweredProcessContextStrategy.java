package tr.nttdata.bootcamp.core.process.strategies;

import de.hybris.platform.acceleratorservices.process.strategies.impl.AbstractProcessContextStrategy;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontProcessModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import tr.nttdata.bootcamp.core.model.ProductQuestionAnsweredProcessModel;

import java.util.Optional;

public class ProductQuestionAnsweredProcessContextStrategy extends AbstractProcessContextStrategy {

    @Override
    public BaseSiteModel getCmsSite(final BusinessProcessModel businessProcessModel)
    {
        ServicesUtil.validateParameterNotNull(businessProcessModel, BUSINESS_PROCESS_MUST_NOT_BE_NULL_MSG);

        return Optional.of(businessProcessModel)
                .filter(businessProcess -> businessProcess instanceof ProductQuestionAnsweredProcessModel)
                .map(businessProcess -> ((ProductQuestionAnsweredProcessModel) businessProcess).getQuestion().getSite())
                .orElse(null);
    }

    @Override
    protected CustomerModel getCustomer(final BusinessProcessModel businessProcess)
    {
        return Optional.of(businessProcess)
                .filter(bp -> bp instanceof ProductQuestionAnsweredProcessModel)
                .map(bp -> (CustomerModel)((ProductQuestionAnsweredProcessModel) businessProcess).getUser())
                .orElse(null);
    }
}
