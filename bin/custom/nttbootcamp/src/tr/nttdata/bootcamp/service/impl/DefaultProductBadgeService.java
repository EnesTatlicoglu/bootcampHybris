package tr.nttdata.bootcamp.service.impl;

import de.hybris.platform.servicelayer.model.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.nttdata.bootcamp.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.enums.BadgeStatus;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.ProductBadgeService;

@Service(value = "productBadgeService")
public class DefaultProductBadgeService implements ProductBadgeService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductBadgeService.class);

    @Autowired
    private ModelService modelService;

    @Autowired
    private ProductBadgeDao productBadgeDao;

    @Override
    public ProductBadgeModel getProductBadge(String code) {
        return productBadgeDao.findBadgeForCode(code);
    }

    @Override
    public ProductBadgeModel createBadgeForCode(String code) {
        ProductBadgeModel badge = getProductBadge(code);
        if(badge != null){
            LOG.info("A badge with code {} already exists", code);
            return badge;
        }

        badge = modelService.create(ProductBadgeModel.class);
        badge.setCode(code);
        badge.setStatus(BadgeStatus.ACTIVE);
        modelService.save(badge);
        LOG.info("Created badge for code {}", code);
        return badge;
    }

    @Override
    public void deleteBadgeForCode(String code) {
        ProductBadgeModel badge = getProductBadge(code);
        if(badge != null){
            LOG.info("Removing badge with code {}", code);
            modelService.remove(badge);
        } else {
            LOG.error("No badge found with code {}, nothing to delete!", code);
        }
    }
}