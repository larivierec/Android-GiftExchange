package com.clariviere.dev.giftexchange.abstract_classes;


public abstract class CommunicationModel {
    private String mTo;
    private String mFrom;
    private String mSubject;
    private String mText;

    public CommunicationModel(String iTo, String iMessage){
        this.mTo = iTo;
        this.mText = iMessage;
    }

    public CommunicationModel(String iTo, String iFrom, String iSubject, String iMessage){
        this.mTo = iTo;
        this.mFrom = iFrom;
        this.mSubject = iSubject;
        this.mText = iMessage;
    }

    public CommunicationModel(CommunicationModel iModel){
        this.mTo = iModel.getCommunicationTo();
        this.mFrom = iModel.getCommunicationFrom();
        this.mSubject = iModel.getCommunicationSubject();
        this.mText = iModel.getCommunicationMessage();
    }

    public String getCommunicationTo(){return mTo;}
    public String getCommunicationFrom(){return mFrom;}
    public String getCommunicationSubject(){return mSubject;}
    public String getCommunicationMessage(){return mText;}

    protected abstract void createMessage();
}
