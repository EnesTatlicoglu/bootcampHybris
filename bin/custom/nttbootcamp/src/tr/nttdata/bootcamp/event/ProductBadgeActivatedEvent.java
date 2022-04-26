package tr.nttdata.bootcamp.event;

import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.servicelayer.event.PublishEventContext;
import de.hybris.platform.servicelayer.event.TransactionAwareEvent;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

public class ProductBadgeActivatedEvent extends AbstractEvent implements ClusterAwareEvent, TransactionAwareEvent {

    private final String code;
    private final Long pk;
    private final String userId;

    public ProductBadgeActivatedEvent(String code, Long pk, String userId) {
        super();
        this.code = code;
        this.pk = pk;
        this.userId = userId;
    }

    @Override
    public boolean canPublish(PublishEventContext publishEventContext) {
        return publishEventContext.getSourceNodeId() == publishEventContext.getTargetNodeId();
    }

    public String getCode() {
        return code;
    }

    public Long getPk() {
        return pk;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "ProductBadgeActivatedEvent{" +
                "code='" + code + '\'' +
                ", pk=" + pk +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean publishOnCommitOnly() {
        return true;
    }

    @Override
    public Object getId() {
        return getCode();
    }
}