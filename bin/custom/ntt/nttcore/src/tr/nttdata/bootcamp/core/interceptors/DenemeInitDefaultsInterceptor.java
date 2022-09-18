package tr.nttdata.bootcamp.core.interceptors;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.interceptor.InitDefaultsInterceptor;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.ProductBadgeModel;

public class DenemeInitDefaultsInterceptor implements InitDefaultsInterceptor {

    private final Logger LOG = LoggerFactory.getLogger(DenemeInitDefaultsInterceptor.class);

    @Override
    public void onInitDefaults(Object o, InterceptorContext interceptorContext) throws InterceptorException {
        if (o instanceof ProductModel){
            LOG.info("ProductModel");
        }else if (o instanceof ProductBadgeModel){
            LOG.info("ProductBadgeModel");
        }else {
            LOG.info("HATA!");
        }
    }
}
