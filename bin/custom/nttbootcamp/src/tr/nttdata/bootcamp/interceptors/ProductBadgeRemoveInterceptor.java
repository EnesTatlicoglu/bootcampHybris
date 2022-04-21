package tr.nttdata.bootcamp.interceptors;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.RemoveInterceptor;
import de.hybris.platform.servicelayer.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

public class ProductBadgeRemoveInterceptor implements RemoveInterceptor<ProductBadgeModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeRemoveInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public void onRemove(ProductBadgeModel model, InterceptorContext ctx) throws InterceptorException {
        LOG.info("Entered RemoveInterceptor for PK: {}, isNew: {}", model.getPk(), ctx.isNew(model));
        LOG.info("Removing badge with PK: {} and code: {} by user {}", model.getPk(), model.getCode(), userService.getCurrentUser().getUid());


    }

}