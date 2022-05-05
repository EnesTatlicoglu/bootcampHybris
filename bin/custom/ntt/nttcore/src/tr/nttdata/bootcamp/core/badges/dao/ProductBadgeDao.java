package tr.nttdata.bootcamp.core.badges.dao;

import tr.nttdata.bootcamp.core.model.ProductBadgeModel;

public interface ProductBadgeDao {

    ProductBadgeModel getProductBadgeForCode(String code);

}
