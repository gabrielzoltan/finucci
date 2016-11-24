package hu.wup.hackathon.finucci.model.sendapi;

public class Messaging {
    private Message message;
    private Recipient recipient;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
