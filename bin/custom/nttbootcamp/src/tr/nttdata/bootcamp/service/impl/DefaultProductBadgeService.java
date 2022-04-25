package tr.nttdata.bootcamp.service.impl;

import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.tx.Transaction;
import de.hybris.platform.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tr.nttdata.bootcamp.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.enums.BadgeStatus;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.ProductBadgeService;

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

        Transaction tx = Transaction.current();
        boolean success = false;
        try {
            tx.begin();
            badge = modelService.create(ProductBadgeModel.class);
            badge.setStatus(BadgeStatus.ACTIVE);
            badge.setCode(code);
            modelService.save(badge);
            LOG.info("Created badge for code {}", code);
            success = Config.getBoolean("create.badge.transaction.success", true);
            return badge;
        } catch (Exception ex){
            LOG.error("An exception occurred while trying to create badge for code {}", code, ex);
            return null;
        } finally {
            if(!success || tx.isRollbackOnly()){
                LOG.info("Rolling back for badge creation of {}", code);
                tx.rollback();
            } else {
                LOG.info("Committing for badge creation of {}", code);
                tx.commit();
            }
        }
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