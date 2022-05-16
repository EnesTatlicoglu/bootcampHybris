/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateQuoteCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.order.data.CartData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.nttdata.bootcamp.facades.order.CartCustomerNoteFacade;
import tr.nttdata.bootcamp.storefront.controllers.ControllerConstants;
import tr.nttdata.bootcamp.storefront.forms.CustomerNoteForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/checkout/multi/customer-note")
public class CustomerNoteCheckoutStepController extends AbstractCheckoutStepController
{
    private static final String CUSTOMER_NOTE = "customer-note";
    public static final String CUSTOMER_NOTE_FORM = "customerNoteForm";

    @Autowired
    @Qualifier(value = "cartCustomerNoteFacade")
    private CartCustomerNoteFacade cartCustomerNoteFacade;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @RequireHardLogIn
    @Override
    @PreValidateQuoteCheckoutStep
    @PreValidateCheckoutStep(checkoutStep = CUSTOMER_NOTE)
    public String enterStep(final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
    {

        final CartData cartData = getCheckoutFacade().getCheckoutCart();
        model.addAttribute("cartData", cartData);
        this.prepareDataForPage(model);
        if (!model.containsAttribute(CUSTOMER_NOTE_FORM))
        {
            model.addAttribute(CUSTOMER_NOTE_FORM, new CustomerNoteForm());
        }
        final ContentPageModel multiCheckoutSummaryPage = getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL);
        storeCmsPageInModel(model, multiCheckoutSummaryPage);
        setUpMetaDataForContentPage(model, multiCheckoutSummaryPage);
        model.addAttribute(WebConstants.BREADCRUMBS_KEY,
                getResourceBreadcrumbBuilder().getBreadcrumbs("checkout.multi.customerNote.breadcrumb"));
        model.addAttribute("metaRobots", "noindex,nofollow");
        setCheckoutStepLinksForModel(model, getCheckoutStep());

        return ControllerConstants.Views.Pages.MultiStepCheckout.AddEditCustomerNotePage;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String updateCustomerNote(@Valid final CustomerNoteForm form, final BindingResult bindingResult,
                                     final HttpServletRequest request, final RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors())
        {
            redirectAttributes.addFlashAttribute("cnErrorMsg",
                    getMessageSource().getMessage("text.customernote.invalid.error", null, getI18nService().getCurrentLocale()));
            return getCheckoutStep().currentStep();
        }

        if(cartCustomerNoteFacade.updateCustomerNote(form.getCustomerNote())){
            redirectAttributes.addFlashAttribute("cnSuccessMsg",
                    getMessageSource().getMessage("text.customernote.success", null, getI18nService().getCurrentLocale()));
        } else {
            redirectAttributes.addFlashAttribute("cnErrorMsg",
                    getMessageSource().getMessage("text.customernote.update.error", null, getI18nService().getCurrentLocale()));
        }

        return getCheckoutStep().currentStep();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeCustomerNote(final HttpServletRequest request, final RedirectAttributes redirectAttributes)
    {
        if(cartCustomerNoteFacade.removeCustomerNote()){
            redirectAttributes.addFlashAttribute("cnSuccessMsg",
                    getMessageSource().getMessage("text.customernote.success", null, getI18nService().getCurrentLocale()));
        } else {
            redirectAttributes.addFlashAttribute("cnErrorMsg",
                    getMessageSource().getMessage("text.customernote.update.error", null, getI18nService().getCurrentLocale()));
        }

        return getCheckoutStep().currentStep();
    }


    @RequestMapping(value = "/back", method = RequestMethod.GET)
    @RequireHardLogIn
    @Override
    public String back(final RedirectAttributes redirectAttributes)
    {
        return getCheckoutStep().previousStep();
    }

    @RequestMapping(value = "/next", method = RequestMethod.GET)
    @RequireHardLogIn
    @Override
    public String next(final RedirectAttributes redirectAttributes)
    {
        return getCheckoutStep().nextStep();
    }

    protected CheckoutStep getCheckoutStep()
    {
        return getCheckoutStep(CUSTOMER_NOTE);
    }
}