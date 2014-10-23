package com.lnu.soagamble2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface WorkflowServiceAsync {
	void initialize(AsyncCallback<String> callback);
	void createClient(AsyncCallback<String> callback);
}
