package tr.nttdata.bootcamp.core.attributehandler;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;
import org.apache.commons.collections.CollectionUtils;

public class ProductBadgeCountAttributeHandler implements DynamicAttributeHandler<Integer, ProductModel> {

    @Override
    public Integer get(ProductModel model) {
        return CollectionUtils.size(model.getBadges());
    }

    @Override
    public void set(ProductModel model, Integer integer) {
        throw new UnsupportedOperationException();
    }

}