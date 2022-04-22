package tr.nttdata.bootcamp.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.UUID;

public class ProductBadgePrepareInterceptor implements PrepareInterceptor<ProductBadgeModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgePrepareInterceptor.class);

    @Override
    public void onPrepare(ProductBadgeModel model, InterceptorContext ctx) throws InterceptorException {
        if(ctx.isModified(model, ProductBadgeModel.CODE)){
            String code = model.getCode();
            if(StringUtils.isNotEmpty(code)){
                code = code.replaceAll("[^a-zA-Z0-9]", "");
                model.setCode(code);
            }
        }
    }
}
