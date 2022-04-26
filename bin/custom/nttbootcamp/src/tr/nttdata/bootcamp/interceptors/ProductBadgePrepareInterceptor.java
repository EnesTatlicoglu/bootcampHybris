package tr.nttdata.bootcamp.interceptors;

import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.enums.BadgeStatus;
import tr.nttdata.bootcamp.event.ProductBadgeActivatedEvent;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.UUID;

public class ProductBadgePrepareInterceptor implements PrepareInterceptor<ProductBadgeModel> {

    private EventService eventService;
    private UserService userService;

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
        triggerBadgeActivatedEvent(model, ctx);
    }

    protected void triggerBadgeActivatedEvent(ProductBadgeModel model, InterceptorContext ctx){
        if(!BadgeStatus.ACTIVE.equals(model.getStatus())){
            return; // If the current status is not active, do not check
        }

        final boolean isNew = ctx.isNew(model);
        final boolean activated = !isNew && !BadgeStatus.ACTIVE.equals(model.getItemModelContext().getOriginalValue(ProductBadgeModel.STATUS));
        if(isNew || activated){
            eventService.publishEvent(new ProductBadgeActivatedEvent(model.getCode(),
                    isNew ? null : model.getPk().getLong(), userService.getCurrentUser().getUid()));
        }
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
