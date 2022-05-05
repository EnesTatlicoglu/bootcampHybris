package tr.nttdata.bootcamp.core.badges.service;

import tr.nttdata.bootcamp.core.model.ProductBadgeModel;

public interface ProductBadgeService {

    ProductBadgeModel getProductBadgeForCode(String code);

}
