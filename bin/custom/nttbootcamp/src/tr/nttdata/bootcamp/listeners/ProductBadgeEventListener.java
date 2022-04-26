package tr.nttdata.bootcamp.listeners;

import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

public class ProductBadgeEventListener extends AbstractEventListener<AfterItemCreationEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeEventListener.class);

    private ModelService modelService;

    @Override
    protected void onEvent(AfterItemCreationEvent event) {
        if(event == null || event.getSource() == null){
            return;
        }

        LOG.debug("Listener caught an event with ID: {}, Source: {}, Scope: {}, Type Code: {}, Timestamp: {}",
                event.getId(), event.getSource(), event.getScope(), event.getTypeCode(), event.getTimestamp());

        final Object object = modelService.get(event.getSource());
        if(object instanceof ProductBadgeModel){
            ProductBadgeModel badge = (ProductBadgeModel) object;
            LOG.info("Badge {} (PK: {}) is created!", badge.getCode(), badge.getPk());
        }
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}