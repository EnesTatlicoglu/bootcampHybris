package tr.nttdata.bootcamp.facades.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.util.Config;
import org.springframework.util.CollectionUtils;
import tr.nttdata.bootcamp.data.ProductData;
import tr.nttdata.bootcamp.data.ProductOption;
import tr.nttdata.bootcamp.facades.ProductFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultProductFacade implements ProductFacade {

    private CatalogVersionService catalogVersionService;
    private ProductService productService;
    private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfigurablePopulator;

    @Override
    public List<ProductData> getProducts() {
        final String catalogId = Config.getString("bootcamp.catalog.id", "Default");
        final String catalogVersion = Config.getString("bootcamp.catalog.version", "Staged");
        CatalogVersionModel catalogVersionModel = getCatalogVersionService().getCatalogVersion(catalogId, catalogVersion);
        getCatalogVersionService().setSessionCatalogVersion(catalogId, catalogVersion);
        List<ProductModel> products = getProductService().getAllProductsForCatalogVersion(catalogVersionModel);
        if(CollectionUtils.isEmpty(products)){
            return Collections.emptyList();
        }
        final List<ProductData> productDataList = new ArrayList<>(products.size());
        products.forEach(productModel -> {
            final ProductData productData = new ProductData();
            getProductConfigurablePopulator().populate(productModel, productData, Collections.singletonList(ProductOption.BASIC));
            productDataList.add(productData);
        });
        return productDataList;
    }

    @Override
    public ProductData getProductForCode(String code) {
        final String catalogId = Config.getString("bootcamp.catalog.id", "Default");
        final String catalogVersion = Config.getString("bootcamp.catalog.version", "Staged");
        CatalogVersionModel catalogVersionModel = getCatalogVersionService().getCatalogVersion(catalogId, catalogVersion);
        getCatalogVersionService().setSessionCatalogVersion(catalogId, catalogVersion);
        final ProductModel productModel = getProductService().getProductForCode(catalogVersionModel, code);
        final ProductData productData = new ProductData();
        getProductConfigurablePopulator().populate(productModel, productData, Arrays.asList(ProductOption.values()));
        return productData;
    }

    public CatalogVersionService getCatalogVersionService() {
        return catalogVersionService;
    }

    public void setCatalogVersionService(CatalogVersionService catalogVersionService) {
        this.catalogVersionService = catalogVersionService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ConfigurablePopulator<ProductModel, ProductData, ProductOption> getProductConfigurablePopulator() {
        return productConfigurablePopulator;
    }

    public void setProductConfigurablePopulator(ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfigurablePopulator) {
        this.productConfigurablePopulator = productConfigurablePopulator;
    }
}