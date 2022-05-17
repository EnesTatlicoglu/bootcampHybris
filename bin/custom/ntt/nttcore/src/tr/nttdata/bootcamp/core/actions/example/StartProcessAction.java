package tr.nttdata.bootcamp.core.actions.example;

import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.task.RetryLaterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class StartProcessAction extends AbstractSimpleDecisionAction<BusinessProcessModel> {

    private static final Logger LOG = LoggerFactory.getLogger(StartProcessAction.class);

    @Override
    public Transition executeAction(BusinessProcessModel process) throws RetryLaterException, Exception {
        LOG.info("Process: {} in step {}", process.getCode(), getClass());
        int tail = ThreadLocalRandom.current().nextInt(0, 2);
        if(tail == 1){
            LOG.info("It is tail, returning OK");
            return Transition.OK;
        } else {
            LOG.info("It is heads, returning NOK");
            return Transition.NOK;
        }
    }
}