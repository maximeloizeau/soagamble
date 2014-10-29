package com.webapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface WorkflowServiceAsync {
	void initialize(AsyncCallback<String> callback);
	void createClient(int waitingTime, boolean favorite, AsyncCallback<Double> callback);
}
