package tr.nttdata.bootcamp.interceptors;

import de.hybris.platform.servicelayer.interceptor.InitDefaultsInterceptor;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.UUID;

public class ProductBadgeInitDefaultsInterceptor implements InitDefaultsInterceptor<ProductBadgeModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeInitDefaultsInterceptor.class);

    @Override
    public void onInitDefaults(ProductBadgeModel model, InterceptorContext ctx) throws InterceptorException {
        LOG.debug("Entered InitDefaultsInterceptor for PK: {}, isNew: {}", model.getPk(), ctx.isNew(model));
        model.setCode(UUID.randomUUID().toString());
    }

}