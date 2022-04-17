package tr.nttdata.bootcamp.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import tr.nttdata.bootcamp.data.ProductData;

public class ProductBasicPopulator implements Populator<ProductModel, ProductData> {

    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        target.setCode(source.getCode());
        target.setName(source.getName());
    }

}