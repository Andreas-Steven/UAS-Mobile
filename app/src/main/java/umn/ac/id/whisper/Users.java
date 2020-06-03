package umn.ac.id.whisper;

public class Users
{


    private String ID;
    private String NamaLengkap;
    private String NIM;
    private String Email;
    private String ImageURL;

    public Users()
    {

    }

    public Users(String id, String Nama, String NIM, String Email, String ImageURL)
    {
        this.ID = id;
        this.NamaLengkap = Nama;
        this.NIM = NIM;
        this.Email = Email;
        this.ImageURL = ImageURL;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public void setNamaLengkap(String namaLengkap) {
        NamaLengkap = namaLengkap;
    }

    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getId() { return ID; }

    public String getNamaLengkap()
    {
        return NamaLengkap;
    }

    public String getNIM()
    {
        return NIM;
    }

    public String getEmail()
    {
        return Email;
    }

    public String getImageURL() { return ImageURL; }
}
