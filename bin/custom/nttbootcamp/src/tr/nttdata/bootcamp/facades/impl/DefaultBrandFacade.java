package tr.nttdata.bootcamp.facades.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.data.BrandData;
import tr.nttdata.bootcamp.data.ProductData;
import tr.nttdata.bootcamp.facades.BrandFacade;
import tr.nttdata.bootcamp.model.BrandModel;
import tr.nttdata.bootcamp.service.BrandService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultBrandFacade implements BrandFacade {

    private BrandService brandService;
    private Converter<BrandModel, BrandData> brandConverter;

    @Override
    public List<BrandData> getAllBrands() {
        final List<BrandModel> brands = getBrandService().getAllBrands();
        return getBrandConverter().convertAll(brands);
    }

    @Override
    public BrandData getBrandForCode(String code) {
        final BrandModel brand = getBrandService().getBrandForCode(code);
        return brand == null ? null : getBrandConverter().convert(brand);
    }

    public BrandService getBrandService() {
        return brandService;
    }

    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    public Converter<BrandModel, BrandData> getBrandConverter() {
        return brandConverter;
    }

    public void setBrandConverter(Converter<BrandModel, BrandData> brandConverter) {
        this.brandConverter = brandConverter;
    }
}
