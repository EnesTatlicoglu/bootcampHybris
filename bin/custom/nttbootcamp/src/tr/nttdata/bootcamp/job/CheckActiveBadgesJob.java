package tr.nttdata.bootcamp.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.jalo.CronJobProgressTracker;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CheckActiveBadgesJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = LoggerFactory.getLogger(CheckActiveBadgesJob.class);

    private ProductBadgeDao productBadgeDao;

    @Override
    public PerformResult perform(CronJobModel cronJob) {
        final CronJobProgressTracker tracker = new CronJobProgressTracker(modelService.getSource(cronJob));
        final List<ProductBadgeModel> activeBadges = getProductBadgeDao().findActiveBadges();
        LOG.info("Found {} active badges!", CollectionUtils.size(activeBadges));
        tracker.setProgress(1.0d); // Set initial process as %1
        for (int i = 2; i < 100; i++)
        {
            if (clearAbortRequestedIfNeeded(cronJob))
            {
                LOG.error("The job is aborted at step {}", i);
                return new PerformResult(CronJobResult.ERROR, CronJobStatus.ABORTED);
            }
            try
            {
                tracker.setProgress((double) i); // Set progress
                int waitTime = ThreadLocalRandom.current().nextInt(100, 1000);
                LOG.info("Will wait {} ms at step {}", waitTime, i);
                Thread.sleep(waitTime);
            }
            catch (final InterruptedException e)
            {
                return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
            }
        }
        tracker.setProgress(100.0d); // Set progress as %100
        tracker.close(); // Save last progress to the database
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    @Override
    public boolean isAbortable() {
        return true;
    }

    public ProductBadgeDao getProductBadgeDao() {
        return productBadgeDao;
    }

    public void setProductBadgeDao(ProductBadgeDao productBadgeDao) {
        this.productBadgeDao = productBadgeDao;
    }
}