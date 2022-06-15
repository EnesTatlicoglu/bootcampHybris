package tr.nttdata.bootcamp.core.event;

import de.hybris.platform.servicelayer.event.events.AbstractEvent;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;

import java.io.Serializable;

public class ProductQuestionAnsweredEvent extends AbstractEvent {

    private ProductQuestionModel question;

    public ProductQuestionAnsweredEvent(ProductQuestionModel question) {
        super();
        this.question = question;
    }

    public ProductQuestionModel getQuestion() {
        return question;
    }
}
