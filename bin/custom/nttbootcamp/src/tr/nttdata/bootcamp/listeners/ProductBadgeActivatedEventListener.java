package tr.nttdata.bootcamp.listeners;

import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.event.ProductBadgeActivatedEvent;

public class ProductBadgeActivatedEventListener extends AbstractEventListener<ProductBadgeActivatedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeActivatedEventListener.class);

    @Override
    protected void onEvent(ProductBadgeActivatedEvent productBadgeActivatedEvent) {
        LOG.info("{} is caught", productBadgeActivatedEvent);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex){
            LOG.error("Exception!", ex);
        }
        LOG.info("Wait ended for event {}", productBadgeActivatedEvent);
    }
}