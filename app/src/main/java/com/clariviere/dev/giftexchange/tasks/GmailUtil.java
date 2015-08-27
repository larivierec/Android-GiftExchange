package com.clariviere.dev.giftexchange.tasks;

import com.clariviere.dev.giftexchange.model.Email;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import java.io.IOException;

public class GmailUtil {

    public static void sendMessage(Gmail service, String userID, Email email) throws IOException {
        Message message = service.users().messages().send(userID, email.getMessage()).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
    }
}
