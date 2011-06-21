package com.u3b.client.components;

import java.io.Serializable;

public interface ComponentExecutable<T extends Serializable>{
	
	void doUIAction(T dto);
	
}