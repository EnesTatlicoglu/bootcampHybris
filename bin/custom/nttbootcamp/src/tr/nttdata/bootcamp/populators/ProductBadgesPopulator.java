package tr.nttdata.bootcamp.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.data.ProductBadgeData;
import tr.nttdata.bootcamp.data.ProductData;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.Collections;
import java.util.Set;

public class ProductBadgesPopulator implements Populator<ProductModel, ProductData> {

    private Converter<ProductBadgeModel, ProductBadgeData> productBadgeConverter;

    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        final Set<ProductBadgeModel> badges = source.getBadges();
        if(CollectionUtils.isNotEmpty(badges)){
            target.setBadges(getProductBadgeConverter().convertAll(badges));
        } else {
            target.setBadges(Collections.emptyList());
        }
    }

    public Converter<ProductBadgeModel, ProductBadgeData> getProductBadgeConverter() {
        return productBadgeConverter;
    }

    public void setProductBadgeConverter(Converter<ProductBadgeModel, ProductBadgeData> productBadgeConverter) {
        this.productBadgeConverter = productBadgeConverter;
    }
}