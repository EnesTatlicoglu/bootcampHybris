package tr.nttdata.bootcamp.service.impl;

import de.hybris.platform.core.model.product.ProductModel;
import tr.nttdata.bootcamp.dao.BrandDao;
import tr.nttdata.bootcamp.model.BrandModel;
import tr.nttdata.bootcamp.service.BrandService;

import java.util.List;

public class DefaultBrandService implements BrandService {

    private BrandDao brandDao;

    @Override
    public List<BrandModel> getAllBrands() {
        return getBrandDao().getAllBrands();
    }

    @Override
    public BrandModel getBrandForCode(String code) {
        return getBrandDao().getBrandForCode(code);
    }

    @Override
    public List<ProductModel> getProductsForBrand(String code) {
        return getBrandDao().getProductsForBrand(code);
    }

    public BrandDao getBrandDao() {
        return brandDao;
    }

    public void setBrandDao(BrandDao brandDao) {
        this.brandDao = brandDao;
    }
}
