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
        LOG.info("Entered PrepareInterceptor for PK: {}, isNew: {}", model.getPk(), ctx.isNew(model));
        if(ctx.isModified(model, ProductBadgeModel.CODE)){
            String code = model.getCode();
            LOG.info("Code value is modified {}", code);
            if(StringUtils.isNotEmpty(code)){
                code = code.replaceAll("[^a-zA-Z0-9]", "");
            }

            if(StringUtils.isEmpty(code)){
                code = UUID.randomUUID().toString().replace("-", "");
            }
            LOG.info("Final value is {}", code);
            model.setCode(code);
        }
    }
}
