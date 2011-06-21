package com.u3b.client.components;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.u3b.client.constants.U3BConstants;
import com.u3b.client.images.InputImages;

public class EntryTable extends Composite {

	static interface DelAction {
		void removeEntry();
	}

	private U3BConstants constants = GWT.create(U3BConstants.class);
	private FlexTable flex;
	private NumberFormat nmbFormat;
	private DateTimeFormat dateFormat;
	
	public EntryTable() {
		
		ScrollPanel scroll = new ScrollPanel();
		scroll.setAlwaysShowScrollBars(true);
		
		flex = new FlexTable();
		Label lblEntryDate = new Label(constants.lblEntryDate_text());
		flex.setWidget(0, 0, lblEntryDate);

		Label lblEntryDescription = new Label(
				constants.lblEntryDescription_text());
		flex.setWidget(0, 1, lblEntryDescription);

		Label lblEntryPosition = new Label(constants.lblEntryPosition_text());
		flex.setWidget(0, 2, lblEntryPosition);

		Label lblAmount = new Label(constants.lblAmount_text_1());
		flex.setWidget(0, 3, lblAmount);

		Label lblDelete = new Label(constants.lblDelete_text());
		flex.setWidget(0, 4, lblDelete);

		flex.setSize("100%", "100%");
		
		nmbFormat = NumberFormat.getCurrencyFormat();
		dateFormat = DateTimeFormat.getShortDateFormat();
		
		scroll.setWidget(flex);
		scroll.setSize("100%", "300px");
		
		SimplePanel simple = new SimplePanel();
		simple.setWidget(scroll);
		
		//binding Style
		flex.getRowFormatter().setStyleName(0, "grid-labels");
		
		initWidget(simple);
	}

	public void addEntry(Date entryDate, String entryDescription,
			int entryPosition, double entryValue, final DelAction action) {
		final int numRows = flex.getRowCount();

		InputImages images = GWT.create(InputImages.class);
		
		Image imgPos = null;
		if (entryPosition == 1) {
			imgPos = new Image(images.plus());
			entryValue = Math.abs(entryValue);
		} else {
			imgPos = new Image(images.minus());
			entryValue = Math.abs(entryValue) * -1;
		}

		Anchor delLink = new Anchor();
		delLink.setStyleName("delLink");
		delLink.setText(constants.lblDelete_text());
		delLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				action.removeEntry();
			}
		});

		flex.setWidget(numRows, 0, new Label(dateFormat.format(entryDate)));
		flex.setWidget(numRows, 1, new Label(entryDescription));
		flex.setWidget(numRows, 2, imgPos);
		flex.setWidget(numRows, 3, new Label(nmbFormat.format(entryValue)));
		flex.setWidget(numRows, 4, delLink);
		
		String style = numRows%2==0 ? "grid-alternate-a" : "grid-alternate-b";
		
		RowFormatter formatter = flex.getRowFormatter();
		formatter.setStyleName(numRows, style);
	}
	
	/**
	 * 
	 */
	public void clearData(){
		
		while (flex.getRowCount() > 1){
			flex.removeRow(1);
		}
		
	}

}