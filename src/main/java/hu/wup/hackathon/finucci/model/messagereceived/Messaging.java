package hu.wup.hackathon.finucci.model.messagereceived;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messaging {
    
    private Sender sender;
    private Recipient recipient;
    private Long timestamp;
    private Message message;
    private AccountLinking accountLinking;

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public AccountLinking getAccountLinking() {
        return accountLinking;
    }

    public void setAccountLinking(AccountLinking accountLinking) {
        this.accountLinking = accountLinking;
    }
}
