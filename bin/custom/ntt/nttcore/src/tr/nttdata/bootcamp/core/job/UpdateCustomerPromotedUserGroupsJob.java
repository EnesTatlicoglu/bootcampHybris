package tr.nttdata.bootcamp.core.job;

import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import org.apache.commons.collections4.CollectionUtils;
import org.hsqldb.persist.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.PromotedUserGroupConfigurationModel;
import tr.nttdata.bootcamp.core.user.service.PromotedUserGroupConfigurationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpdateCustomerPromotedUserGroupsJob extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateCustomerPromotedUserGroupsJob.class);
    private PromotedUserGroupConfigurationService promotedUserGroupConfigurationService;

    @Override
    public PerformResult perform(CronJobModel cronJob) {
        final List<PromotedUserGroupConfigurationModel> passiveConfigurations = getPromotedUserGroupConfigurationService().getPassiveConfigurations();
        if (CollectionUtils.isNotEmpty(passiveConfigurations)) {
            LOG.info("Found {} passive configurations, will remove members of user groups", passiveConfigurations.size());
            for (PromotedUserGroupConfigurationModel config : passiveConfigurations) {
                if (clearAbortRequestedIfNeeded(cronJob)) {
                    LOG.error("CronJob is aborted!");
                    return new PerformResult(CronJobResult.UNKNOWN, CronJobStatus.ABORTED);
                }
                final UserGroupModel userGroup = config.getUserGroup();
                if (userGroup != null) {
                    LOG.info("Members of group {} will be removed!", userGroup.getUid());
                    List<CustomerModel> customers = getPromotedUserGroupConfigurationService().getCustomersForPromotedGroup(userGroup);
                    if (CollectionUtils.isNotEmpty(customers)){
                        LOG.info("Found {} customers to remove {} from their promoted user group field", customers.size(), userGroup.getUid());
                        customers.forEach(c -> {
                            LOG.debug("Removing {} from customer {}", userGroup.getUid(), c.getUid());
                            c.setPromotedUserGroup(null);
                            modelService.save(c);
                        });
                    }
                }
            }
        }

        final List<PromotedUserGroupConfigurationModel> activeConfigurations = getPromotedUserGroupConfigurationService().getActiveConfigurations();
        if (CollectionUtils.isNotEmpty(activeConfigurations)) {
            LOG.info("Found {} active configurations, will update user groups of customers", activeConfigurations.size());
            for (PromotedUserGroupConfigurationModel config : activeConfigurations) {
                if (clearAbortRequestedIfNeeded(cronJob)) {
                    LOG.error("CronJob is aborted!");
                    return new PerformResult(CronJobResult.UNKNOWN, CronJobStatus.ABORTED);
                }
                final UserGroupModel userGroup = config.getUserGroup();
                if (userGroup != null) {
                    final List<CustomerModel> customersToBeRemoved = getPromotedUserGroupConfigurationService().getCustomersShouldBeRemovedFromGroup(config);
                    if (CollectionUtils.isNotEmpty(customersToBeRemoved)) {
                        LOG.info("Found {} customers to remove {} from their groups", customersToBeRemoved.size(), userGroup.getUid());
                        for (CustomerModel customer : customersToBeRemoved) {
                            if (clearAbortRequestedIfNeeded(cronJob)) {
                                LOG.error("CronJob is aborted!");
                                return new PerformResult(CronJobResult.UNKNOWN, CronJobStatus.ABORTED);
                            }
                            LOG.debug("Removing {} from customer {}", userGroup.getUid(), customer.getUid());

                            customer.setPromotedUserGroup(null);
                            modelService.save(customer);
                        }
                    }

                    final List<CustomerModel> customersToBeAdded = getPromotedUserGroupConfigurationService().getCustomersShouldBeAddedToGroup(config);
                    if (CollectionUtils.isNotEmpty(customersToBeAdded)) {
                        LOG.info("Found {} customers to add {} to their groups", customersToBeAdded.size(), userGroup.getUid());
                        for (CustomerModel customer : customersToBeAdded) {
                            if (clearAbortRequestedIfNeeded(cronJob)) {
                                LOG.error("CronJob is aborted!");
                                return new PerformResult(CronJobResult.UNKNOWN, CronJobStatus.ABORTED);
                            }
                            LOG.debug("Adding {} to customer {}", userGroup.getUid(), customer.getUid());

                            customer.setPromotedUserGroup(userGroup);
                            modelService.save(customer);
                        }
                    }
                }
            }
        }
        LOG.info("CronJob finished!");
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    @Override
    public boolean isAbortable() {
        return true;
    }

    public PromotedUserGroupConfigurationService getPromotedUserGroupConfigurationService() {
        return promotedUserGroupConfigurationService;
    }

    public void setPromotedUserGroupConfigurationService(PromotedUserGroupConfigurationService promotedUserGroupConfigurationService) {
        this.promotedUserGroupConfigurationService = promotedUserGroupConfigurationService;
    }
}
