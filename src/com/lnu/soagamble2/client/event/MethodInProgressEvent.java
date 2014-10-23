package com.lnu.soagamble2.client.event;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;

public class MethodInProgressEvent implements Event {
	private static final long serialVersionUID = -1479753254935255374L;

	public static final Domain SERVER_MESSAGE_DOMAIN = DomainFactory.getDomain("server_message_domain");

	private String methodName;
	
	public MethodInProgressEvent() {}

    public MethodInProgressEvent(String name) {
        methodName = name;
    }

    public String getServerGeneratedMessage() {
        return methodName;
    }

    public String toString() {
        StringBuilder theStringBuilder = new StringBuilder(100);
        theStringBuilder.append(MethodInProgressEvent.class.getName());
        theStringBuilder.append(new char[] {' ', '('});
        theStringBuilder.append(methodName);
        theStringBuilder.append(')');
        return theStringBuilder.toString();        
    }
}
