package tr.nttdata.bootcamp.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.util.XSSFilterUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class CustomerNoteForm implements Serializable
{
    private static final long serialVersionUID = 3734178553292263688L;

    @NotNull(message = "{text.customernote.invalid.error}")
    @Size(min = 1, max = 5000, message = "{text.customernote.invalid.error}")
    private String customerNote;

    public String getCustomerNote()
    {
        return customerNote;
    }

    public void setCustomerNote(final String customerNote)
    {
        this.customerNote = XSSFilterUtil.filter(customerNote);
    }
}