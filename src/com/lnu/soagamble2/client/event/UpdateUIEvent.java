package com.lnu.soagamble2.client.event;

import com.google.gwt.user.client.rpc.IsSerializable;
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;

public class UpdateUIEvent implements Event, IsSerializable {

	private static final long serialVersionUID = 6493394055532892961L;

	public static final Domain SERVER_MESSAGE_DOMAIN = DomainFactory.getDomain("server_message_domain");

	private String[] object;
	private int state;
	
	public UpdateUIEvent() {}

    public UpdateUIEvent(String[] obj, int state) {
        object = obj;
        this.state = state;
    }

    public int getState(){
    	return this.state;
    }
    public String[] getGeneratedObject(){
    	return this.object;
    }
    
    public String toString() {
        StringBuilder theStringBuilder = new StringBuilder(100);
        theStringBuilder.append(UpdateUIEvent.class.getName());
        theStringBuilder.append(new char[] {' ', '('});
        theStringBuilder.append(object);
        theStringBuilder.append(')');
        return theStringBuilder.toString();        
    }
}
