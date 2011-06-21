package com.u3b.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.u3b.client.components.MessagePanel;
import com.u3b.client.components.MessagePanel.MessageType;
import com.u3b.client.constants.U3BConstants;
import com.u3b.client.domain.Account;
import com.u3b.client.domain.CreditCard;
import com.u3b.client.domain.User;
import com.u3b.client.exceptions.UserValidationException;
import com.u3b.client.rpc.SettingsService;
import com.u3b.client.rpc.SettingsServiceAsync;
import com.u3b.client.validators.ClientValidable;
import com.u3b.client.validators.SettingsValidador;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class SettingsWidget extends Composite {

	private U3BConstants constants = GWT.create(U3BConstants.class);
	private Map<String, Widget> widgets = new HashMap<String, Widget>();
	private ClientValidable validator = new SettingsValidador();
	private MessagePanel messagePanel = new MessagePanel();
	private Label lblWelcome;

	private final SettingsServiceAsync settingsService = GWT
			.create(SettingsService.class);

	final TextBox txtAcct = new TextBox();
	final TextBox txtCard = new TextBox();
	final ListBox cboLanguage;

	public SettingsWidget(boolean showWelcome) {
		this();

		if (showWelcome)
			this.showWelcomeMsg();
		else
			retrieveUserInfo();
	}

	private void retrieveUserInfo() {
		settingsService.retrieveUser(new AsyncCallback<User>() {

			@Override
			public void onSuccess(User user) {
				txtAcct.setText(user.getAccount().getAccountName());
				txtCard.setText(user.getCreditCard().getCardName());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * @wbp.parser.constructor
	 */
	private SettingsWidget() {
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setSize("100%", "100%");

		lblWelcome = new Label(constants.cwWelcomeMessage());
		lblWelcome.setVisible(false);
		verticalPanel.add(lblWelcome);
		lblWelcome.setWidth("500px");
		lblWelcome.setStyleName("p13n_box2");

		DecoratorPanel decoratorPanel = new DecoratorPanel();
		verticalPanel.add(decoratorPanel);
		decoratorPanel.setSize("535px", "100%");

		Grid grid = new Grid(9, 2);
		grid.setCellSpacing(5);
		grid.setCellPadding(3);
		decoratorPanel.setWidget(grid);

		Label label_2 = new Label(constants.cwAcctName());
		grid.setWidget(0, 0, label_2);
		txtAcct.setMaxLength(50);
		txtAcct.setVisibleLength(60);

		grid.setWidget(0, 1, txtAcct);
		
		Label lblSettingsAcct = new Label(constants.lblSettingsAcct_text());
		grid.setWidget(1, 1, lblSettingsAcct);

		Label label_3 = new Label(constants.cwCreditCard());
		grid.setWidget(2, 0, label_3);
		txtCard.setMaxLength(50);
		txtCard.setVisibleLength(60);

		grid.setWidget(2, 1, txtCard);
		
		Label lblSettingsCard = new Label(constants.lblSettingsCard_text());
		grid.setWidget(3, 1, lblSettingsCard);

		Label lblLanguage = new Label(constants.lblLanguage_text());
		grid.setWidget(4, 0, lblLanguage);

		cboLanguage = new ListBox();
		cboLanguage.addItem("English");
		cboLanguage.addItem("Portugu\u00EAs");
		cboLanguage.setSelectedIndex(0);
		grid.setWidget(4, 1, cboLanguage);
		grid.getCellFormatter().setHorizontalAlignment(8, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);

		widgets.put("acctName", txtAcct);
		widgets.put("cardName", txtCard);

		Button button = new Button(constants.cwSend());
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				try {
					messagePanel.hide();
					validator.performValidation(widgets);

					User user = new User();
					Account acct = new Account();

					CreditCard card = new CreditCard();
					card.setCardName(txtCard.getText());

					acct.setAccountName(txtAcct.getText());

					user.setLanguage(cboLanguage.getItemText(cboLanguage
							.getSelectedIndex()));
					user.setAccount(acct);
					user.setCreditCard(card);

					// Calls Server
					settingsService.saveUserInfo(user,
							new AsyncCallback<User>() {

								@Override
								public void onSuccess(User result) {
									messagePanel.show(
											constants.cwSettingsSucess(),
											MessageType.Warning);
								}

								@Override
								public void onFailure(Throwable caught) {
									messagePanel.show(caught.getMessage(),
											MessagePanel.MessageType.Error);
								}
							});

				} catch (UserValidationException e) {
					messagePanel.show(e.getMessage(),
							MessagePanel.MessageType.Error);
					e.printStackTrace();
				}
			}
		});
		
		Label lblSettingsLanguage = new Label(constants.lblSettingsLanguage_text());
		grid.setWidget(5, 1, lblSettingsLanguage);
		
		lblSettingsAcct.setStyleName("settings-helpers");
		lblSettingsCard.setStyleName("settings-helpers");
		lblSettingsLanguage.setStyleName("settings-helpers");
		
		grid.setWidget(7, 1, button);
		grid.setWidget(8, 1, messagePanel);
		grid.getCellFormatter().setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		grid.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);

		initWidget(verticalPanel);
	}

	public void showWelcomeMsg() {
		lblWelcome.setVisible(true);
	}

}
