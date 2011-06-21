package com.u3b.client.init;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.u3b.client.constants.U3BConstants;

public class AppFooter extends Composite {

	private U3BConstants constants = GWT.create(U3BConstants.class);
	private Label lblAcctAmount;
	private Label lblCardAmount;
	private NumberFormat nmbFormat;
	
	public AppFooter() {

		VerticalPanel vp = new VerticalPanel();

		initWidget(vp);
		vp.setSize("100%", "100%");

		Label lblMyBalances = new Label(constants.cwMyBalances());
		lblMyBalances.setStyleName("footer-balances");
		lblMyBalances.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Label lblMyAccount = new Label(constants.lblMyAccount_text());
		lblMyAccount.setStyleName("footer-acct");
		
		Label lblMyCreditCard = new Label(constants.lblMyCreditCard_text());
		lblMyCreditCard.setStyleName("footer-card");
		
		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, lblMyAccount);
		grid.setWidget(0, 1, lblMyCreditCard);
		vp.add(lblMyBalances);
		vp.setCellHorizontalAlignment(lblMyBalances, HasHorizontalAlignment.ALIGN_CENTER);
		vp.add(grid);

		lblAcctAmount = new Label(constants.lblAcctAmount_text());
		grid.setWidget(1, 0, lblAcctAmount);

		lblCardAmount = new Label(constants.lblCardAmount_text());
		grid.setWidget(1, 1, lblCardAmount);
		
		lblAcctAmount.setStyleName("footer-amount");
		lblCardAmount.setStyleName("footer-amount");
		
		nmbFormat = NumberFormat.getCurrencyFormat();
	}

	public void updateAcctBalance(double acctBalance) {
		lblAcctAmount.setText(nmbFormat.format(acctBalance));
	}

	public void updateCardBalance(double cardBalance) {
		lblCardAmount.setText(nmbFormat.format(cardBalance));
	}

}