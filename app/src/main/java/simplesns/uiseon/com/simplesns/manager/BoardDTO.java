package simplesns.uiseon.com.simplesns.manager;


public class BoardDTO {

    private int board_number;
    private String substance;
    private String write_user_id;
    private String notice_check;
    private String datetime;

    public BoardDTO(int board_number, String substance, String write_user_id, String notice_check, String datetime) {
        super();
        this.board_number = board_number;
        this.substance = substance;
        this.write_user_id = write_user_id;
        this.notice_check = notice_check;
        this.datetime = datetime;
    }

    public BoardDTO() {
        super();
    }

    public void setBoard_number(int board_number) {
        this.board_number = board_number;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public void setWrite_user_id(String write_user_id) {
        this.write_user_id = write_user_id;
    }

    public void setNotice_check(String notice_check) {
        this.notice_check = notice_check;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getBoard_number() {
        return board_number;
    }

    public String getSubstance() {
        return substance;
    }

    public String getWrite_user_id() {
        return write_user_id;
    }

    public String getNotice_check() {
        return notice_check;
    }

    public String getDatetime() {
        return datetime;
    }

}
