package tr.nttdata.bootcamp.facades;

import tr.nttdata.bootcamp.data.ProductBadgeData;

public interface ProductBadgeFacade {

    ProductBadgeData getProductBadgeForCode(String code);

    ProductBadgeData createProductBadgeForCode(String code);

    void deleteProductBadge(String code);

}