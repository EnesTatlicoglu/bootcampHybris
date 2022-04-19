package tr.nttdata.bootcamp.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import tr.nttdata.bootcamp.data.BrandData;
import tr.nttdata.bootcamp.data.ProductData;
import tr.nttdata.bootcamp.model.BrandModel;

public class ProductBrandPopulator implements Populator<ProductModel, ProductData> {

    private Converter<BrandModel, BrandData> brandConverter;

    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        final BrandModel brand = source.getBrand();
        if(brand != null){
            target.setBrand(getBrandConverter().convert(brand));
        }

    }

    public Converter<BrandModel, BrandData> getBrandConverter() {
        return brandConverter;
    }

    public void setBrandConverter(Converter<BrandModel, BrandData> brandConverter) {
        this.brandConverter = brandConverter;
    }
}
