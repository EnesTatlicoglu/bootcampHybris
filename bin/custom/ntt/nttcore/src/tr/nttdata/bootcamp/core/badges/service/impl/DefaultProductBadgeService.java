package tr.nttdata.bootcamp.core.badges.service.impl;

import tr.nttdata.bootcamp.core.badges.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.core.badges.dao.impl.DefaultProductBadgeDao;
import tr.nttdata.bootcamp.core.badges.service.ProductBadgeService;
import tr.nttdata.bootcamp.core.model.ProductBadgeModel;

public class DefaultProductBadgeService implements ProductBadgeService {

    private ProductBadgeDao productBadgeDao;

    @Override
    public ProductBadgeModel getProductBadgeForCode(String code) {
        return getProductBadgeDao().getProductBadgeForCode(code);
    }

    public ProductBadgeDao getProductBadgeDao() {
        return productBadgeDao;
    }

    public void setProductBadgeDao(ProductBadgeDao productBadgeDao) {
        this.productBadgeDao = productBadgeDao;
    }
}
