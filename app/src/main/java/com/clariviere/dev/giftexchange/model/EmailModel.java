package com.clariviere.dev.giftexchange.model;

import android.util.Log;

import com.clariviere.dev.giftexchange.abstract_classes.CommunicationModel;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailModel extends CommunicationModel{

    private Properties mProps = new Properties();
    private Session mSession = Session.getDefaultInstance(mProps,null);

    private MimeMessage mMimeMessage;
    private Message mGmailMessage;

    public EmailModel(String to, String from, String subject, String text){
        super(to,from,subject,text);
        try{
            createEmail();
            createMessage();
        }catch(Exception messageEx){
            messageEx.printStackTrace();
        }
    }

    private void createEmail() throws MessagingException{
        mMimeMessage = new MimeMessage(mSession);
        mMimeMessage.setFrom(new InternetAddress(getCommunicationFrom()));
        mMimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(getCommunicationTo()));
        mMimeMessage.setSubject(getCommunicationSubject());
        mMimeMessage.setText(getCommunicationMessage());
    }

    protected void createMessage(){
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            mMimeMessage.writeTo(bytes);
            String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
            Message message = new Message();
            message.setRaw(encodedEmail);
            mGmailMessage = message;
        }
        catch(MessagingException messagingEx){
            Log.d("Messaging Exception", messagingEx.getMessage());
        }
        catch(IOException ioException){
            Log.d("IOException", ioException.getMessage());
        }

    }

    public Message getGmailMessage(){
        return mGmailMessage;
    }
}