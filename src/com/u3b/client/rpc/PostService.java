package com.u3b.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.u3b.client.domain.Account;
import com.u3b.client.domain.AcctEntry;
import com.u3b.client.domain.CardEntry;
import com.u3b.client.domain.CreditCard;

@RemoteServiceRelativePath("account")
public interface PostService extends RemoteService {

	public Account saveIntoAcct(AcctEntry entry);
	
	public Account removeFromAcct(AcctEntry entry);
	
	public Account getUserAcct();
	
	public CreditCard saveIntoCard(CardEntry entry);
	
	public CreditCard removeFromCard(CardEntry entry);
	
	public CreditCard getUserCard();
	
}
