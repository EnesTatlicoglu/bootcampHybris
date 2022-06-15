package tr.nttdata.bootcamp.core.interceptors;

import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import org.apache.commons.lang3.StringUtils;
import tr.nttdata.bootcamp.core.enums.ProductQuestionStatus;
import tr.nttdata.bootcamp.core.event.ProductQuestionAnsweredEvent;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;

public class ProductQuestionPrepareInterceptor implements PrepareInterceptor<ProductQuestionModel> {

    private EventService eventService;

    @Override
    public void onPrepare(ProductQuestionModel model, InterceptorContext ctx) throws InterceptorException {
        if (!ctx.isNew(model) && ProductQuestionStatus.PENDING.equals(model.getStatus()) && StringUtils.isNotEmpty(model.getAnswer())){
            model.setStatus(ProductQuestionStatus.ANSWERED);
            getEventService().publishEvent(new ProductQuestionAnsweredEvent(model));
        }
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
