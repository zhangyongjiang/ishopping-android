package com.nextshopper.rest.beans;

import java.util.ArrayList;
import java.util.List;


public class MailMessage {
    public List<String> to = new ArrayList<String>();
    public String from;
    public String userName;
    public String subject;
    public String content;
    public boolean isHtml = true;
}
