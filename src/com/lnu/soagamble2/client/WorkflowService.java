package com.lnu.soagamble2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("workflow")
public interface WorkflowService extends RemoteService {
	String initialize();
	Double createClient();
}
