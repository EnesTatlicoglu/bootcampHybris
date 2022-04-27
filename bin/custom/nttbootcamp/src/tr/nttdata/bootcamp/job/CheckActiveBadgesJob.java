package tr.nttdata.bootcamp.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.List;

public class CheckActiveBadgesJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = LoggerFactory.getLogger(CheckActiveBadgesJob.class);

    private ProductBadgeDao productBadgeDao;

    @Override
    public PerformResult perform(CronJobModel cronJob) {
        final List<ProductBadgeModel> activeBadges = getProductBadgeDao().findActiveBadges();
        LOG.info("Found {} active badges!", CollectionUtils.size(activeBadges));
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    public ProductBadgeDao getProductBadgeDao() {
        return productBadgeDao;
    }

    public void setProductBadgeDao(ProductBadgeDao productBadgeDao) {
        this.productBadgeDao = productBadgeDao;
    }
}