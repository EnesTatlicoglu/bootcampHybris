package tr.nttdata.bootcamp.core.task;

import de.hybris.platform.task.RetryLaterException;
import de.hybris.platform.task.TaskModel;
import de.hybris.platform.task.TaskRunner;
import de.hybris.platform.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class SampleTaskRunner implements TaskRunner<TaskModel> {

    private static final Logger LOG = LoggerFactory.getLogger(SampleTaskRunner.class);


    @Override
    public void run(TaskService taskService, TaskModel taskModel) throws RetryLaterException {
        LOG.info("Entered in run() method of task with PK {}", taskModel.getPk());
        int dice = ThreadLocalRandom.current().nextInt(0, 7);
        if(dice == 6){
            LOG.info("Dice is 6! Lucky you!");
            return;
        } else if (dice == 0){
            LOG.info("Invalid dice!");
            throw new RuntimeException("Dice is invalid");
        }

        LOG.info("Will throw dice again! Dice was {}", dice);
        RetryLaterException ex = new RetryLaterException("Throwing dice in 1 seconds");
        ex.setDelay(1000);
        ex.setMethod(RetryLaterException.Method.LINEAR);
        throw ex;
    }

    @Override
    public void handleError(TaskService taskService, TaskModel taskModel, Throwable throwable) {
        LOG.info("Handling error for task {} and throwable {}", taskModel.getPk(), throwable.getMessage());
    }

}
