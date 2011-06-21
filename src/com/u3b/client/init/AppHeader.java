package com.u3b.client.init;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.u3b.client.constants.U3BConstants;
import com.u3b.client.rpc.SettingsService;
import com.u3b.client.rpc.SettingsServiceAsync;

public class AppHeader extends Composite {

	private U3BConstants constants = GWT.create(U3BConstants.class);
	private final SettingsServiceAsync settingsService = GWT
			.create(SettingsService.class);

	private HTML logout;
	
	public AppHeader() {

		Grid grid = new Grid(2, 2);
		grid.setStyleName("headerGrid");
		grid.setSize("100%", "44px");
		initWidget(grid);
		grid.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);

		// Necessário criar a URL de logout no App Engine e enviar no terceiro
		// parâmetro.
		logout = new HTML("");
		retrieveLogoutURL();

		// hyperlink.setHTML("Logout");
		grid.setWidget(0, 1, logout);

		VerticalPanel verticalPanel = new VerticalPanel();
		grid.setWidget(1, 0, verticalPanel);

		Label lblLogo = new Label(constants.lblLogo_text());
		lblLogo.setStyleName("app-Logo");
		verticalPanel.add(lblLogo);

		Label lblAppName = new Label(constants.lblAppName_text());
		lblAppName.setStyleName("app-Name");
		verticalPanel.add(lblAppName);

		grid.getCellFormatter().setStyleName(0, 0, "cellBackGround");
		grid.getCellFormatter().setStyleName(0, 1, "cellBackGround");
		grid.setCellPadding(0);
		grid.setCellSpacing(0);

	}

	private void retrieveLogoutURL() {
		settingsService.getLogoutURL(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String url) {
				logout.setHTML("<a href='" + url + "'>Logout</a>");
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
}
