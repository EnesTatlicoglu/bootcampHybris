/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.backoffice.actions;

import com.hybris.backoffice.widgets.notificationarea.NotificationService;
import com.hybris.backoffice.widgets.notificationarea.event.NotificationEvent;
import com.hybris.cockpitng.actions.ActionContext;
import com.hybris.cockpitng.actions.ActionResult;
import com.hybris.cockpitng.actions.CockpitAction;
import com.hybris.cockpitng.core.impl.DefaultWidgetModel;
import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import tr.nttdata.bootcamp.core.enums.ProductQuestionStatus;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;

import javax.annotation.Resource;
import java.util.Iterator;


public class PublishProductQuestionAction implements CockpitAction<ProductQuestionModel, Object>
{
	private static final String CONFIRMATION_MESSAGE = "publishproductquestion.confirmation.message";
	private static final String SUCCESS_MESSAGE = "publishproductquestion.success.message";

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "notificationService")
	private NotificationService notificationService;

	@Override
	public boolean canPerform(final ActionContext<ProductQuestionModel> ctx)
	{
		return ctx != null && ctx.getData() != null
				&& !ctx.getData().isOnlyForUser() && ProductQuestionStatus.ANSWERED.equals(ctx.getData().getStatus());
	}

	@Override
	public String getConfirmationMessage(final ActionContext<ProductQuestionModel> ctx)
	{
		return ctx.getLabel(CONFIRMATION_MESSAGE);
	}

	@Override
	public boolean needsConfirmation(final ActionContext<ProductQuestionModel> ctx)
	{
		return true;
	}

	@Override
	public ActionResult<Object> perform(final ActionContext<ProductQuestionModel> ctx)
	{
		ProductQuestionModel question = ctx.getData();
		question.setStatus(ProductQuestionStatus.PUBLISHED);
		modelService.save(question);

		DefaultWidgetModel widget = (DefaultWidgetModel) ctx.getParameter("parentWidgetModel");
		modelService.refresh(ctx.getData());
		widget.setValue("currentObject", ctx.getData());
		widget.changed("currentObject.status");
		widget.setValue("inputObjectIsNew", Boolean.FALSE);
		widget.setValue("inputObjectIsModified", Boolean.FALSE);
		widget.setValue("valueChanged", Boolean.FALSE);

		notificationService.notifyUser("bootcamp", "JustMessage", NotificationEvent.Level.SUCCESS, ctx.getLabel(SUCCESS_MESSAGE));
		return new ActionResult<>(ActionResult.SUCCESS, question);
	}
}
