package com.nextshopper.rest.beans;

import java.util.List;

public class MessageThread {
	public String msgId;
	public List<MessageDetails> items;
	
	public MessageThread() {
	}
	
	public MessageThread(String msgId, List<MessageDetails> items) {
		this.msgId = msgId;
		this.items = items;
	}
}
