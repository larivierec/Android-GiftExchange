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

    public EmailModel(String to, String from, String subject, String text) {
        super(to, from, subject, text);
    }

    @Override
    protected void createMessage() {

    }
}