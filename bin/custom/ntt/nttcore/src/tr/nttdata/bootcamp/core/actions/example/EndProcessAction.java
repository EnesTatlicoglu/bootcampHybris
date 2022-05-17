package tr.nttdata.bootcamp.core.actions.example;

import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.task.RetryLaterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndProcessAction extends AbstractProceduralAction<BusinessProcessModel> {

    private static final Logger LOG = LoggerFactory.getLogger(EndProcessAction.class);

    @Override
    public void executeAction(BusinessProcessModel process) throws RetryLaterException, Exception {
        LOG.info("Process: {} in step {}", process.getCode(), getClass());
        LOG.info("There is nothing to return");
    }

}