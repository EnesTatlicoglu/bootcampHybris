package tr.nttdata.bootcamp.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

public class ProductBadgeValidateInterceptor implements ValidateInterceptor<ProductBadgeModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeValidateInterceptor.class);

    @Override
    public void onValidate(ProductBadgeModel model, InterceptorContext ctx) throws InterceptorException {
        LOG.info("Entered ValidateInterceptor for PK: {}, isNew: {}", model.getPk(), ctx.isNew(model));
        if(model.getEndDate() != null
                && model.getStartDate() != null
                && model.getEndDate().before(model.getStartDate())){
            throw new InterceptorException("End date must be after from start date!");
        }
    }
}
