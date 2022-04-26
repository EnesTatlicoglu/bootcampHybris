package tr.nttdata.bootcamp.listeners;

import de.hybris.platform.core.PK;
import de.hybris.platform.tx.AfterSaveEvent;
import de.hybris.platform.tx.AfterSaveListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ProductBadgeAfterSaveEventListener implements AfterSaveListener {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeAfterSaveEventListener.class);


    @Override
    public void afterSave(Collection<AfterSaveEvent> events) {
        for (final AfterSaveEvent event : events)
        {
            final PK pk = event.getPk();
            //The ProductBadge deployment code is "20500"
            if (20500 == pk.getTypeCode())
            {
                LOG.info("Received AfterSaveEvent for badge with PK {} and type {}", pk, getType(event.getType()));
            }
        }
    }

    protected String getType(int type){
        switch (type){
            case AfterSaveEvent.UPDATE:
                return "UPDATE";
            case AfterSaveEvent.REMOVE:
                return "REMOVE";
            case AfterSaveEvent.CREATE:
                return "CREATE";
            default:
                return "INVALID_TYPE";
        }
    }

}