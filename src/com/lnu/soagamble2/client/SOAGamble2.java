package com.lnu.soagamble2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lnu.soagamble2.client.event.MethodInProgressEvent;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SOAGamble2 implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final static WorkflowServiceAsync workflowService = GWT.create(WorkflowService.class);

	public static native void printResult(String message)/*-{
	  $wnd.print(message); 
	}-*/;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		exportJSFunction();
		
		RemoteEventService theRemoteEventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
        //add a listener to the SERVER_MESSAGE_DOMAIN
        theRemoteEventService.addListener(MethodInProgressEvent.SERVER_MESSAGE_DOMAIN, new RemoteEventListener() {
            public void apply(Event anEvent) {
                if(anEvent instanceof MethodInProgressEvent) {
                    MethodInProgressEvent theServerGeneratedMessageEvent = (MethodInProgressEvent)anEvent;
                    printResult(theServerGeneratedMessageEvent.getServerGeneratedMessage());
                }
            }
        });
        
        workflowService.initialize(callback);
	}

	public static native void exportJSFunction()/*-{
	  $wnd.launchWorkflow = @com.lnu.soagamble2.client.SOAGamble2::launchWorkflow();
	}-*/;

	public static void launchWorkflow(){
		workflowService.createClient(callback);
	}

	static AsyncCallback<String> callback = new AsyncCallback<String>(){

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(String result) {
			// TODO Auto-generated method stub

		}

	};
}
