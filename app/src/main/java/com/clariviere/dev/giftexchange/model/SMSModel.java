package com.clariviere.dev.giftexchange.model;

import com.clariviere.dev.giftexchange.abstract_classes.CommunicationModel;

public class SMSModel extends CommunicationModel {

    public SMSModel(String iTo, String iMessage){
        super(iTo, iMessage);
    }

    protected void createMessage(){}
}
