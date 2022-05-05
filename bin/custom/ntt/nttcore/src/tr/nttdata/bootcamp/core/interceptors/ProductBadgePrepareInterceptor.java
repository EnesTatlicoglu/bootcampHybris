package tr.nttdata.bootcamp.core.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.ProductBadgeModel;


public class ProductBadgePrepareInterceptor implements PrepareInterceptor<ProductBadgeModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgePrepareInterceptor.class);

    @Override
    public void onPrepare(ProductBadgeModel model, InterceptorContext ctx) throws InterceptorException {
        if(ctx.isModified(model, ProductBadgeModel.CODE)){
            String code = model.getCode();
            if (StringUtils.isNotEmpty(code)){
                code = code.replaceAll("[^a-zA-Z0-9-_]","");
            }
            model.setCode(code);
        }
    }
}
