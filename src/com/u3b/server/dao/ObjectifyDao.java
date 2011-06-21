package com.u3b.server.dao;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.helper.DAOBase;
import com.u3b.client.domain.Account;
import com.u3b.client.domain.CardEntry;
import com.u3b.client.domain.CreditCard;
import com.u3b.client.domain.User;

public class ObjectifyDao<T> extends DAOBase
{
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Account.class);
		ObjectifyService.register(CreditCard.class);
		ObjectifyService.register(CardEntry.class);
	}
	
	private Class<T> clazz;

	/**
	 * We've got to get the associated domain class somehow
	 *
	 * @param clazz
	 */
	protected ObjectifyDao(Class<T> clazz)
	{
		this.clazz = clazz;
	}

	public Key<T> add(T entity)

	{
		Key<T> key = ofy().put(entity);
		return key;
	}

	public void delete(T entity)
	{
		ofy().delete(entity);
	}

	public void delete(Key<T> entityKey)
	{
		ofy().delete(entityKey);
	}

	public T get(Long id) throws EntityNotFoundException
	{
		T obj = ofy().get(this.clazz, id);
		return obj;
	}

}
