package tr.nttdata.bootcamp.dao;

import de.hybris.platform.core.model.product.ProductModel;
import tr.nttdata.bootcamp.model.BrandModel;

import java.util.List;

public interface BrandDao {
    List<BrandModel> getAllBrands();
    BrandModel getBrandForCode(String code);

    List<ProductModel> getProductsForBrand(String code);
}
