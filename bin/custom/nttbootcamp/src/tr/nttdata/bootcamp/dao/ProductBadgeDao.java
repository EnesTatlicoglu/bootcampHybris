package tr.nttdata.bootcamp.dao;

import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.List;

public interface ProductBadgeDao {

    ProductBadgeModel findBadgeForCode(String code);

    List<ProductBadgeModel> findActiveBadges();

}