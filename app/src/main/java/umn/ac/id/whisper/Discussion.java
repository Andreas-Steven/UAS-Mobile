package umn.ac.id.whisper;

public class Discussion
{
    private String ID;
    private String Title;
    private String Image;
    private String Diskusi;
    private String UserID;

    public Discussion()
    {

    }

    public Discussion(String ID, String title, String image, String diskusi, String UserID)
    {
        this.ID = ID;
        this.Title = title;
        this.Image = image;
        this.Diskusi = diskusi;
        this.UserID = UserID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDiskusi() {
        return Diskusi;
    }

    public void setDiskusi(String diskusi) {
        Diskusi = diskusi;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
