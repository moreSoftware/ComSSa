package simplesns.uiseon.com.simplesns.manager;


public class UserDTO {

    private int userNumber;
    private String type;
    private String id;
    private String passwd;
    private String passwdConfirm;

    public UserDTO(int userNumber, String type, String id, String passwd, String passwdConfirm) {
        super();
        this.userNumber = userNumber;
        this.type = type;
        this.id = id;
        this.passwd = passwd;
        this.passwdConfirm = passwdConfirm;
    }

    public UserDTO() {
        super();
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setPasswdConfirm(String passwdConfirm) {
        this.passwdConfirm = passwdConfirm;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getPasswdConfirm() {
        return passwdConfirm;
    }

}
