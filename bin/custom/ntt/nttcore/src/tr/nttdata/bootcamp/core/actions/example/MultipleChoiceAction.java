package tr.nttdata.bootcamp.core.actions.example;

import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.task.RetryLaterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MultipleChoiceAction extends AbstractAction<BusinessProcessModel> {

    private static final Logger LOG = LoggerFactory.getLogger(MultipleChoiceAction.class);

    @Override
    public String execute(BusinessProcessModel process) throws RetryLaterException, Exception {
        LOG.info("Process: {} in step {}", process.getCode(), getClass());
        int rnd = ThreadLocalRandom.current().nextInt(0, 3);
        if(rnd == 0){
            LOG.info("It is 0, returning GOOD");
            return Transition.GOOD.toString();
        } else if(rnd == 1) {
            LOG.info("It is 1, returning BAD");
            return Transition.BAD.toString();
        } else{
            LOG.info("It is 2, returning UGLY");
            return Transition.UGLY.toString();
        }
    }

    @Override
    public Set<String> getTransitions() {
        return Transition.getStringValues();
    }

    public enum Transition
    {
        GOOD, BAD, UGLY;

        public static Set<String> getStringValues()
        {
            final Set<String> res = new HashSet<String>();
            for (final Transition transitions : Transition.values())
            {
                res.add(transitions.toString());
            }
            return res;
        }
    }
}