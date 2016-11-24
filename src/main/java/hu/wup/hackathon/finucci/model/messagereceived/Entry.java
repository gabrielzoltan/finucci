package hu.wup.hackathon.finucci.model.messagereceived;

import java.util.ArrayList;
import java.util.List;

public class Entry {
    
    private String id;
    private Long time;
    private List<Messaging> messaging = new ArrayList<>();

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

    public List<Messaging> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<Messaging> messaging) {
        this.messaging = messaging;
    }
}
