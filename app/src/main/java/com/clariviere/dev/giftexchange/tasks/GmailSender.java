package com.clariviere.dev.giftexchange.tasks;

import android.util.Log;

import com.clariviere.dev.giftexchange.interfaces.ICommunicationModel;
import com.clariviere.dev.giftexchange.model.EmailModel;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSender {

    private Properties mProps = new Properties();
    private Session mSession = Session.getDefaultInstance(mProps,null);

    private MimeMessage mMimeMessage;
    private Message mGmailMessage;
    private ICommunicationModel mModel;

    public GmailSender()
    {}

    private void createEmail() throws MessagingException {
        mMimeMessage = new MimeMessage(mSession);
        mMimeMessage.setFrom(new InternetAddress(mModel.getCommunicationFrom()));
        mMimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(mModel.getCommunicationTo()));
        mMimeMessage.setSubject(mModel.getCommunicationSubject());
        mMimeMessage.setText(mModel.getCommunicationMessage());
    }

    public void setCommunicationModel(ICommunicationModel model) { mModel = model; }

    private void createMessage(){
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

    public void sendMessage(Gmail service, String userID) throws IOException {
        try
        {
            createEmail();
            createMessage();
        }
        catch(MessagingException e)
        {
            System.out.println(e.getMessage());
        }

        Message message = service.users().messages().send(userID, mGmailMessage).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
    }
}
