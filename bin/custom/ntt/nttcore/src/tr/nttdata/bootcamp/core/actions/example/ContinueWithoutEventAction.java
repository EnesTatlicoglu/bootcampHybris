package tr.nttdata.bootcamp.core.actions.example;

import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.task.RetryLaterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContinueWithoutEventAction extends AbstractProceduralAction<BusinessProcessModel> {

    private static final Logger LOG = LoggerFactory.getLogger(ContinueWithoutEventAction.class);

    @Override
    public void executeAction(BusinessProcessModel process) throws RetryLaterException, Exception {
        LOG.info("Process: {} in step {}", process.getCode(), getClass());
        LOG.info("I'm eventless!");
    }

}