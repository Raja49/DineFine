package com.u3b.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.u3b.client.domain.Account;
import com.u3b.client.domain.AcctEntry;
import com.u3b.client.domain.CardEntry;
import com.u3b.client.domain.CreditCard;

public interface PostServiceAsync {


	void saveIntoAcct(AcctEntry entry, AsyncCallback<Account> callback);

	void getUserAcct(AsyncCallback<Account> callback);

	void getUserCard(AsyncCallback<CreditCard> callback);

	void removeFromAcct(AcctEntry entry, AsyncCallback<Account> callback);

	void removeFromCard(CardEntry entry, AsyncCallback<CreditCard> callback);

	void saveIntoCard(CardEntry entry, AsyncCallback<CreditCard> callback);

}
