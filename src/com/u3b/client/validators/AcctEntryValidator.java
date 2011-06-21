package com.u3b.client.validators;

import java.util.Date;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.u3b.client.constants.U3BConstants;
import com.u3b.client.exceptions.UserValidationException;

public class AcctEntryValidator implements ClientValidable {

	private TextBox description;
	private TextBox value;
	private DateBox date;
	private U3BConstants constants = GWT.create(U3BConstants.class);

	@Override
	public void performValidation(Map<String, ? extends Widget> controls)
			throws UserValidationException {

		description = (TextBox) controls.get("description");
		value = (TextBox) controls.get("value");
		date = (DateBox) controls.get("date");

		this.validationRule();
	}

	private void validationRule() throws UserValidationException {

		if (description.getText().length() < 3)
			throw new UserValidationException(constants.cwDescription());

		try {
			Double.valueOf(value.getText());
		} catch (NumberFormatException e) {
			throw new UserValidationException(constants.cwInvalidValue());
		}
		
		 if (!(date.getValue() instanceof Date))
			 throw new UserValidationException(constants.cwInvalidDate());

	}
}
