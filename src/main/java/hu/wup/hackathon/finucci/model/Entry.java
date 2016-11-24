package hu.wup.hackathon.finucci.model;

public class Entry {
    
    private String id;
    private Long time;
    private Messaging messaging;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Messaging getMessaging() {
        return messaging;
    }

    public void setMessaging(Messaging messaging) {
        this.messaging = messaging;
    }
}
