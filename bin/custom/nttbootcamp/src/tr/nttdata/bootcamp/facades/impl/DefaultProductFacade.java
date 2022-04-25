package tr.nttdata.bootcamp.facades.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.converters.ConfigurablePopulator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import tr.nttdata.bootcamp.data.ProductData;
import tr.nttdata.bootcamp.data.ProductOption;
import tr.nttdata.bootcamp.facades.ProductFacade;
import tr.nttdata.bootcamp.service.BrandService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultProductFacade implements ProductFacade {

    private BrandService brandService;
    private CatalogVersionService catalogVersionService;
    private ProductService productService;
    private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfigurablePopulator;

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductFacade.class);

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

        LOG.info("Called getProducts() method");

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

        LOG.info("Called getProductForCode() method with code {}", code);

        return productData;
    }

    @Override
    public List<ProductData> getProductsForBrand(String code) {
        final String catalogId = Config.getString("bootcamp.catalog.id", "Default");
        final String catalogVersion = Config.getString("bootcamp.catalog.version", "Staged");
        CatalogVersionModel catalogVersionModel = getCatalogVersionService().getCatalogVersion(catalogId, catalogVersion);
        getCatalogVersionService().setSessionCatalogVersion(catalogId, catalogVersion);
        List<ProductModel> products = getBrandService().getProductsForBrand(code);
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

    public BrandService getBrandService() {
        return brandService;
    }

    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }
}