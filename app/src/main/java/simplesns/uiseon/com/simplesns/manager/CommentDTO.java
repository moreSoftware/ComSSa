package simplesns.uiseon.com.simplesns.manager;


public class CommentDTO {
    private int comment_number;
    private int board_number;
    private String substance;
    private String write_user_id;
    private String name;
    private String datetime;


    public CommentDTO(int comment_number,int board_number, String substance, String write_user_id, String name, String datetime) {
        super();
        this.comment_number = comment_number;
        this.board_number = board_number;
        this.substance = substance;
        this.write_user_id = write_user_id;
        this.name = name;
        this.datetime = datetime;

    }

    public CommentDTO() {
        super();
    }

    public void setComment_number(int comment_number) {this.comment_number = comment_number;}

    public void setBoard_number(int board_number) {
        this.board_number = board_number;
    }

    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public void setWrite_user_id(String write_user_id) {
        this.write_user_id = write_user_id;
    }



    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public void setName(String name) {this.name = name;}

    public int getComment_number(){return comment_number;}

    public int getBoard_number() {
        return board_number;
    }

    public String getSubstance() {
        return substance;
    }

    public String getWrite_user_id() {
        return write_user_id;
    }



    public String getDatetime() {
        return datetime;
    }


    public String getName(){return name;}

}
