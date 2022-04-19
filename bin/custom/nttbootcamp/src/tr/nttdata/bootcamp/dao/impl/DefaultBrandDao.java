package tr.nttdata.bootcamp.dao.impl;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.dao.BrandDao;
import tr.nttdata.bootcamp.model.BrandModel;

import java.util.Collections;
import java.util.List;

public class DefaultBrandDao extends DefaultGenericDao<BrandModel> implements BrandDao {

    private static final String PRODUCTS_FOR_BRAND_QUERY = "SELECT {p:" + ItemModel.PK + "} FROM {" + ProductModel._TYPECODE + " AS p "+
            "JOIN " + BrandModel._TYPECODE + " AS b ON {b:" + ItemModel.PK + "} = {p:" + ProductModel.BRAND + "}}" +
            "WHERE {b:" + BrandModel.CODE + "} = ?" + BrandModel.CODE;

    public DefaultBrandDao() {
        super(BrandModel._TYPECODE);
    }

    @Override
    public List<BrandModel> getAllBrands() {
        return find();
    }

    @Override
    public BrandModel getBrandForCode(String code) {
        List<BrandModel> result = find(Collections.singletonMap(BrandModel.CODE, code));
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }

    @Override
    public List<ProductModel> getProductsForBrand(String code) {
        final FlexibleSearchQuery fsq = new FlexibleSearchQuery(PRODUCTS_FOR_BRAND_QUERY);
        fsq.addQueryParameter(BrandModel.CODE, code);
        fsq.setResultClassList(Collections.singletonList(ProductModel.class));
        return getFlexibleSearchService().<ProductModel>search(fsq).getResult();
    }
}
