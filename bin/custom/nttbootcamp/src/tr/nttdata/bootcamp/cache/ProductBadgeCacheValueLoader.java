package tr.nttdata.bootcamp.cache;

import de.hybris.platform.regioncache.CacheValueLoadException;
import de.hybris.platform.regioncache.CacheValueLoader;
import de.hybris.platform.regioncache.key.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.ProductBadgeService;

public class ProductBadgeCacheValueLoader implements CacheValueLoader<ProductBadgeModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductBadgeCacheValueLoader.class);

    private ProductBadgeService productBadgeService;

    @Override
    public ProductBadgeModel load(CacheKey cacheKey) throws CacheValueLoadException {
        if(!(cacheKey instanceof ProductBadgeCacheKey)){
            return null;
        }

        final String code = ((ProductBadgeCacheKey) cacheKey).getCode();

        LOG.info("Called loader for code {}", code);

        ProductBadgeModel badge = getProductBadgeService().getProductBadge(code);
        if(badge == null){
            badge = getProductBadgeService().createBadgeForCode(code);
        }
        return badge;
    }

    public ProductBadgeService getProductBadgeService() {
        return productBadgeService;
    }

    public void setProductBadgeService(ProductBadgeService productBadgeService) {
        this.productBadgeService = productBadgeService;
    }

}