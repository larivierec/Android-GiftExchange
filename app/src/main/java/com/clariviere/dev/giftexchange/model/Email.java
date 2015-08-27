package com.clariviere.dev.giftexchange.model;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.Draft;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    private String mTo;
    private String mFrom;
    private String mSubject;
    private String mText;

    private Properties mProps = new Properties();
    private Session mSession = Session.getDefaultInstance(mProps,null);

    private MimeMessage mMimeMessage;
    private Message mMessage;

    public Email(String to, String from, String subject, String text){
        mTo = to;
        mFrom = from;
        mSubject = subject;
        mText = text;
        try{
            createEmail();
            createMessage();
        }catch(Exception messageEx){
            messageEx.printStackTrace();
        }
    }

    private void createEmail() throws MessagingException{
        mMimeMessage = new MimeMessage(mSession);
        mMimeMessage.setFrom(new InternetAddress(mFrom));
        mMimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(mTo));
        mMimeMessage.setSubject(mSubject);
        mMimeMessage.setText(mText);
    }

    public void createMessage() throws MessagingException, IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        mMimeMessage.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        mMessage = message;
    }

    public String getFrom(){
        return mFrom;
    }

    public Message getMessage(){
        return mMessage;
    }
}