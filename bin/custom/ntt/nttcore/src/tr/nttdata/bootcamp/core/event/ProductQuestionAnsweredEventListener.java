package tr.nttdata.bootcamp.core.event;

import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import tr.nttdata.bootcamp.core.model.ProductQuestionAnsweredProcessModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;

public class ProductQuestionAnsweredEventListener extends AbstractEventListener<ProductQuestionAnsweredEvent> {

    private ModelService modelService;
    private BusinessProcessService businessProcessService;

    @Override
    protected void onEvent(ProductQuestionAnsweredEvent event) {
        ProductQuestionModel question = event.getQuestion();
        String processName = "productQuestionAnsweredEmailProcess";
        ProductQuestionAnsweredProcessModel process = getBusinessProcessService().createProcess(processName.concat("-")
                .concat(question.getPk().toString())
                .concat(System.currentTimeMillis() + ""), processName);
        process.setQuestion(question);
        modelService.save(process);
        getBusinessProcessService().startProcess(process);
    }

    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
