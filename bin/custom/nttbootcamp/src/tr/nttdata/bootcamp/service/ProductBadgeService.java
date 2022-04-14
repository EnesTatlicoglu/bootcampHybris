package tr.nttdata.bootcamp.service;

import tr.nttdata.bootcamp.model.ProductBadgeModel;

public interface ProductBadgeService {

    ProductBadgeModel getProductBadge(String code);

    ProductBadgeModel createBadgeForCode(String code);

    void deleteBadgeForCode(String code);

}