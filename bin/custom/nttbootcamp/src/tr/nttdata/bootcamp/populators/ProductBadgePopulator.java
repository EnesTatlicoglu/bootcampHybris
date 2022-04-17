package tr.nttdata.bootcamp.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import tr.nttdata.bootcamp.data.ProductBadgeData;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

public class ProductBadgePopulator implements Populator<ProductBadgeModel, ProductBadgeData> {

    @Override
    public void populate(ProductBadgeModel source, ProductBadgeData target) throws ConversionException {
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setLogo(source.getLogo());
    }

}