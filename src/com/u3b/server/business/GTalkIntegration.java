package com.u3b.server.business;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.u3b.client.domain.AcctEntry;
import com.u3b.client.domain.CardEntry;
import com.u3b.client.domain.User;

public class GTalkIntegration extends BundleSupport {

	private XMPPService xmpp;
	private JID answerTo;
	private MessageParser parser;

	public GTalkIntegration() {
		xmpp = XMPPServiceFactory.getXMPPService();
	}

	public void parseAndAnswer(Message message) {
		String body = message.getBody();
		answerTo = message.getFromJid();

		// DEBUG porpuses
		// sendAnswer("Your id is: " + extractUserEmail());

		try {
			parser = new MessageParser();
			parser.setBundle(getBundle());
			parser.setMessage(body);
			parser.validateMessageBody();

			if (parser.getWhere() == MessageParser.WhereToPost.Account)
				postIntoAccount();
			else
				postIntoCard();
			
			
			sendAnswer(getBundle().getString("EntryCreated"));
		} catch (InvalidGtalkMessage e) {
			sendAnswer(e.getMessage() + getInstructionMsg());
		} catch (EntityNotFoundException e) {
			sendAnswer(getBundle().getString("InvalidAccount")
					+ getInstructionMsg());
		}
	}

	private void postIntoCard() {
		CardEntry entry = buildCardEntry();
		
		CardBll bll = new CardBll();
		bll.setAcctUser(retrieveUser());
		entry.setCreditCard(bll.getUserCard());
		
		bll.saveIntoCard(entry);
	}

	private void postIntoAccount() throws EntityNotFoundException {
		AcctEntry entry = buildEntry();
		
		AccountBll bll = new AccountBll();

		bll.setAcctUser(retrieveUser());
		entry.setAccount(bll.getUserAcct());

		bll.saveIntoAcct(entry);
	}

	private User retrieveUser(){
		UserSupportXmpp support = new UserSupportXmpp();
		support.setUserEmail(extractUserEmail());
		return support.retrieveUser();
	}
	
	/**
	 * @return
	 */
	private String extractUserEmail() {
		String email = answerTo.getId();
		String tokens[] = email.split("/");

		return tokens[0];
	}

	private AcctEntry buildEntry() {
		AcctEntry entry = new AcctEntry();
		entry.setEntryDate(parser.getDate());
		entry.setEntryDescription(parser.getFinalMessage());
		entry.setEntryValue(parser.getAmount());

		if (parser.getAmount() > 0)
			entry.setEntryPosition(1);
		else
			entry.setEntryPosition(0);

		return entry;
	}
	
	private CardEntry buildCardEntry(){
		CardEntry entry = new CardEntry();
		entry.setEntryDate(parser.getDate());
		entry.setEntryDescription(parser.getFinalMessage());
		entry.setEntryValue(parser.getAmount());

		if (parser.getAmount() > 0)
			entry.setEntryPosition(1);
		else
			entry.setEntryPosition(0);

		return entry;
	}

	private void sendAnswer(String message) {
		String msgBody = message;

		Message msg = new MessageBuilder().withRecipientJids(answerTo)
				.withBody(msgBody).build();

		boolean messageSent = false;
		if (xmpp.getPresence(answerTo).isAvailable()) {
			SendResponse status = xmpp.sendMessage(msg);
			messageSent = (status.getStatusMap().get(answerTo) == SendResponse.Status.SUCCESS);
		}

		if (!messageSent) {
			System.out.printf("\nMessage to user %s wasn't sent!",
					answerTo.getId());
		}
	}

	public String getInstructionMsg() {
		return String.format(getBundle().getString("ValidMessage"));
	}

}