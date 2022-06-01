package tr.nttdata.bootcamp.core.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.BestSellerConfigModel;
import tr.nttdata.bootcamp.core.product.service.BestSellerProductService;

import java.util.Map;

public class BestSellerProductJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = LoggerFactory.getLogger(BestSellerProductJob.class);
    private BestSellerProductService bestSellerProductService;

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        getBestSellerProductService().removeAllBestSellerProducts();
        BestSellerConfigModel config = getBestSellerProductService().getBestSellerConfig();
        if (config == null){
            LOG.error("Could not find best seller config!");
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.FINISHED);
        }

        Map<String, Long> soldCounts = getBestSellerProductService().getSoldCounts(config.getDays(), config.getTotalCount());
        if (MapUtils.isEmpty(soldCounts)){
            LOG.info("No product is found withing {} days", config.getDays());
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }

        soldCounts.forEach((p, c) -> getBestSellerProductService().createBestSellerProduct(p, c));
        LOG.info("Created {} best seller product record", soldCounts.size());
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    public BestSellerProductService getBestSellerProductService() {
        return bestSellerProductService;
    }

    public void setBestSellerProductService(BestSellerProductService bestSellerProductService) {
        this.bestSellerProductService = bestSellerProductService;
    }
}
