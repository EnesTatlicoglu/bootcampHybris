package tr.nttdata.bootcamp.core.attributehandler;

import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import tr.nttdata.bootcamp.core.enums.BadgeStatus;
import tr.nttdata.bootcamp.core.model.ProductBadgeModel;

import java.util.Date;

public class ProductBadgeVisibleAttributeHandler implements DynamicAttributeHandler<Boolean, ProductBadgeModel> {

    @Override
    public Boolean get(ProductBadgeModel model) {
        // If the status of the badge is PASSIVE, badge should not be visible
        if(BadgeStatus.PASSIVE.equals(model.getStatus())){
            return false;
        }

        // If the start date is after now, badge should not be visible
        final Date startDate = model.getStartDate();
        if(startDate != null && startDate.after(new Date())){
            return false;
        }

        // If the end date is before now, badge should not be visible
        final Date endDate = model.getEndDate();
        if(endDate != null && endDate.before(new Date())){
            return false;
        }

        // Otherwise, display the badge
        return true;
    }

    @Override
    public void set(ProductBadgeModel model, Boolean val) {
        throw new UnsupportedOperationException();
    }
}