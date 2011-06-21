package com.u3b.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.u3b.client.components.ComponentExecutable;
import com.u3b.client.components.InfoInput;
import com.u3b.client.components.MessagePanel;
import com.u3b.client.components.MessagePanel.MessageType;
import com.u3b.client.components.TableViewer;
import com.u3b.client.constants.U3BConstants;
import com.u3b.client.domain.Account;
import com.u3b.client.domain.AcctEntry;
import com.u3b.client.domain.CardEntry;
import com.u3b.client.domain.CreditCard;
import com.u3b.client.init.MainPanel;
import com.u3b.client.rpc.PostService;
import com.u3b.client.rpc.PostServiceAsync;

public class AcctWidget extends Composite {

	private final PostServiceAsync postService = GWT.create(PostService.class);

	private MessagePanel messagePanel;
	private U3BConstants constants = GWT.create(U3BConstants.class);
	private InfoInput infoInput;
	private TableViewer viewer;

	ComponentExecutable<AcctEntry> acctAction = new ComponentExecutable<AcctEntry>() {

		@Override
		public void doUIAction(final AcctEntry dto) {
			postService.saveIntoAcct(dto, new AsyncCallback<Account>() {

				@Override
				public void onFailure(Throwable caught) {
					messagePanel.show(constants.cwInputError(),
							MessageType.Error);
				}

				@Override
				public void onSuccess(Account result) {
					messagePanel.show(constants.cwSucess(), MessageType.Warning);
					infoInput.setAcct(result); // updates the instance in
												// InfoInput
					updateAcctBalance(result);
					viewer.clearAcctEntries();
					viewer.setAcctEntries(result.getEntries());
					viewer.switchTab(TableViewer.Tabs.Account);
				}

			});
		}
	};

	private void updateAcctBalance(Account result) {
		MainPanel.getInstance().getAppFooter()
				.updateAcctBalance(result.getAccountBalance());
	}

	ComponentExecutable<CardEntry> cardAction = new ComponentExecutable<CardEntry>() {

		@Override
		public void doUIAction(CardEntry dto) {
			postService.saveIntoCard(dto, new AsyncCallback<CreditCard>() {

				@Override
				public void onFailure(Throwable caught) {
					messagePanel.show(constants.cwInputError(),
							MessageType.Error);

				}

				@Override
				public void onSuccess(CreditCard result) {
					messagePanel.show(constants.cwSucess(), MessageType.Warning);
					infoInput.setCard(result); // updates the instance in
					// InfoInput
					updateCardBalance(result);
					viewer.clearCardEntries();
					viewer.setCardEntries(result.getEntries());
					viewer.switchTab(TableViewer.Tabs.CreditCard);
				}
			});
		}
	};

	public AcctWidget() {
		VerticalPanel verticalPanel = new VerticalPanel();

		Label lblInstructions = new Label(constants.cwInstructions());
		lblInstructions.setStyleName("p13n_box2");
		lblInstructions.setSize("500px", "100%");

		verticalPanel.add(lblInstructions);

		infoInput = new InfoInput();
		verticalPanel.add(infoInput);

		messagePanel = new MessagePanel();
		verticalPanel.add(messagePanel);

		viewer = new TableViewer();
		verticalPanel.add(viewer);

		this.defineInputProperties();

		initWidget(verticalPanel);
	}

	private void defineInputProperties() {

		infoInput.setAcctAction(acctAction);

		infoInput.setCardAction(cardAction);

		postService.getUserAcct(new AsyncCallback<Account>() {

			@Override
			public void onSuccess(Account result) {
				infoInput.setAcct(result);
				updateAcctBalance(result);
				viewer.setAcctEntries(result.getEntries());
			}

			@Override
			public void onFailure(Throwable caught) {
				messagePanel.show(constants.cwAcctRetrieveError(),
						MessageType.Error);
			}
		});

		postService.getUserCard(new AsyncCallback<CreditCard>() {

			@Override
			public void onSuccess(CreditCard result) {
				infoInput.setCard(result);
				updateCardBalance(result);
				viewer.setCardEntries(result.getEntries());
			}

			@Override
			public void onFailure(Throwable caught) {
				messagePanel.show(constants.cwCardRetrieveError(),
						MessageType.Error);

			}
		});
	}

	private void updateCardBalance(CreditCard result) {
		MainPanel.getInstance().getAppFooter()
				.updateCardBalance(result.getCardBalance());
	}
}
