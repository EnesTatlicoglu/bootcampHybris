package tr.nttdata.bootcamp.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.LoadInterceptor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

public class ProductBadgeLoadInterceptor implements LoadInterceptor<ProductBadgeModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeLoadInterceptor.class);

    @Override
    public void onLoad(ProductBadgeModel model, InterceptorContext ctx) throws InterceptorException {
        LOG.info("Entered LoadInterceptor for PK: {}, isNew: {}", model.getPk(), ctx.isNew(model));
        if(StringUtils.isEmpty(model.getLogo())){
            model.setLogo("https://enter-logo-url/here.png");
        }
    }

}