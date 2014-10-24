package com.lnu.soagamble2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lnu.soagamble2.client.event.State;
import com.lnu.soagamble2.client.event.UpdateUIEvent;

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

	public static native void printSportEventTable(JsArrayString events)/*-{
	  $wnd.printSportEventTable(events); 
	}-*/;
	
	public static native void printOdds(JsArrayString jsArrayString)/*-{
	  $wnd.printOdds(jsArrayString); 
	}-*/;
	
	public static native void printBet(JsArrayString jsArrayString)/*-{
	  $wnd.printBet(jsArrayString); 
	}-*/;
	
	public static native void printProfit(JsArrayString jsArrayString)/*-{
	  $wnd.printProfit(jsArrayString); 
	}-*/;
	
	public static native void displayProfit(double profit)/*-{
	  $wnd.displayProfit(profit); 
	}-*/;
	
	public static native void computeAssets(double paid)/*-{
	  $wnd.computeAssets(paid); 
	}-*/;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		exportJSFunction();

		RemoteEventService theRemoteEventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
		//add a listener to the SERVER_MESSAGE_DOMAIN
		theRemoteEventService.addListener(UpdateUIEvent.SERVER_MESSAGE_DOMAIN, new RemoteEventListener() {
			public void apply(Event anEvent) {
				System.out.println("APPLY");
				if(anEvent instanceof UpdateUIEvent) {
					System.out.println("UPDATEUI EVENT");
					UpdateUIEvent event = (UpdateUIEvent)anEvent;
					int state = ((UpdateUIEvent) anEvent).getState();
					JsArrayString jsArrayString = arrayToJsArray(event.getGeneratedObject());
					switch(state){
					case State.GET_SPORT_EVENTS:
						System.out.println("SPORT EVENT");
						printSportEventTable(jsArrayString);
						break;
					case State.REQUEST_ODDS:
						printOdds(jsArrayString);
						break;
					case State.PLACE_BET:
						printBet(jsArrayString);
						break;
					case State.REQUEST_PROFIT:
						printProfit(jsArrayString);
						break;
					case State.MAKE_PAYMENT:
						String[] tab = event.getGeneratedObject();
						double paid = Double.parseDouble(tab[0]);
						computeAssets(paid);
						break;
					}
				}
			}
		});

		workflowService.initialize(callback);
	}

	
	private JsArrayString arrayToJsArray(String[] tab){
		JsArrayString jsArrayString = JsArrayString.createArray().cast();
	    for (String s : tab) {
	        jsArrayString.push(s);
	    }
	    return jsArrayString;
	}
	
	public static native void exportJSFunction()/*-{
	  $wnd.launchWorkflow = @com.lnu.soagamble2.client.SOAGamble2::launchWorkflow();
	}-*/;
	
	

	public static void launchWorkflow(){
		workflowService.createClient(clientCallback);
	}

	static AsyncCallback<String> callback = new AsyncCallback<String>(){

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(String result) {
			
		}

	};
	
	static AsyncCallback<Double> clientCallback = new AsyncCallback<Double>(){

		@Override
		public void onFailure(Throwable caught) {
			displayProfit(-1.0);

		}

		@Override
		public void onSuccess(Double result) {
			displayProfit(result);
		}

	};
}
