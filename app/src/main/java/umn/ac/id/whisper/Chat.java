package umn.ac.id.whisper;

public class Chat
{
    private String Nama;
    private String DiscussionID;
    private String Chat;

    public Chat()
    {

    }

    public Chat(String Nama, String Chat, String DiscussionID) {
        this.Nama = Nama;
        this.Chat = Chat;
        this.DiscussionID = DiscussionID;
    }

    public String getSender() {
        return Nama;
    }

    public void setSender(String nama) {
        Nama = nama;
    }

    public String getMessage() {
        return Chat;
    }

    public void setMessage(String chat) {
        Chat = chat;
    }

    public String getDiscussionID() {
        return DiscussionID;
    }

    public void setDiscussionID(String discusionID) {
        DiscussionID = discusionID;
    }
}
