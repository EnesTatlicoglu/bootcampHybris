package tr.nttdata.bootcamp.facades.impl;

import de.hybris.platform.core.Registry;
import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.region.impl.EHCacheRegion;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.cache.ProductBadgeCacheKey;
import tr.nttdata.bootcamp.data.ProductBadgeData;
import tr.nttdata.bootcamp.facades.ProductBadgeFacade;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.ProductBadgeService;

public class DefaultProductBadgeFacade implements ProductBadgeFacade {

    private ProductBadgeService productBadgeService;
    private Converter<ProductBadgeModel, ProductBadgeData> productBadgeConverter;

    private EHCacheRegion productBadgeCacheRegion;
    private CacheValueLoader<ProductBadgeModel> productBadgeCacheValueLoader;

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductBadgeFacade.class);

    @Override
    public ProductBadgeData getProductBadgeForCode(String code) {
        ProductBadgeCacheKey cacheKey = new ProductBadgeCacheKey(Registry.getCurrentTenant().getTenantID(), code);
        ProductBadgeModel productBadge = (ProductBadgeModel) getProductBadgeCacheRegion().get(cacheKey);
        if(productBadge == null){
            LOG.info("Product badge with code {} could not be found in cache", code);
            productBadge = (ProductBadgeModel) getProductBadgeCacheRegion().getWithLoader(cacheKey, getProductBadgeCacheValueLoader());
        } else {
            LOG.info("Product badge with code {} found in cache", code);
        }
        return productBadge != null ? getProductBadgeConverter().convert(productBadge) : null;
    }

    @Override
    public ProductBadgeData createProductBadgeForCode(String code) {
        final ProductBadgeModel productBadge = getProductBadgeService().createBadgeForCode(code);
        return getProductBadgeConverter().convert(productBadge);
    }

    @Override
    public void deleteProductBadge(String code) {
        getProductBadgeService().deleteBadgeForCode(code);
    }

    public ProductBadgeService getProductBadgeService() {
        return productBadgeService;
    }

    public void setProductBadgeService(ProductBadgeService productBadgeService) {
        this.productBadgeService = productBadgeService;
    }

    public Converter<ProductBadgeModel, ProductBadgeData> getProductBadgeConverter() {
        return productBadgeConverter;
    }

    public void setProductBadgeConverter(Converter<ProductBadgeModel, ProductBadgeData> productBadgeConverter) {
        this.productBadgeConverter = productBadgeConverter;
    }


    public EHCacheRegion getProductBadgeCacheRegion() {
        return productBadgeCacheRegion;
    }

    public void setProductBadgeCacheRegion(EHCacheRegion productBadgeCacheRegion) {
        this.productBadgeCacheRegion = productBadgeCacheRegion;
    }

    public CacheValueLoader<ProductBadgeModel> getProductBadgeCacheValueLoader() {
        return productBadgeCacheValueLoader;
    }

    public void setProductBadgeCacheValueLoader(CacheValueLoader<ProductBadgeModel> productBadgeCacheValueLoader) {
        this.productBadgeCacheValueLoader = productBadgeCacheValueLoader;
    }

}