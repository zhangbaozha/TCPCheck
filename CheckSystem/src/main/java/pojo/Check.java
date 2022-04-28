package pojo;



public class Check {
    private int id;
    private String username;
    private String check_in_time;

    public Check(int id, String username, String check_in_time, String check_out_time, String check_place, String states) {
        this.id = id;
        this.username = username;
        this.check_in_time = check_in_time;
        this.check_out_time = check_out_time;
        this.check_place = check_place;
        this.states = states;
    }

    private String check_out_time;
    private String check_place;
    private String states;

    public Check() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }

    public String getCheck_place() {
        return check_place;
    }

    public void setCheck_place(String check_place) {
        this.check_place = check_place;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", username:'" + username + '\'' +
                ", check_in_time:'" + check_in_time + '\'' +
                ", check_out_time:'" + check_out_time + '\'' +
                ", check_place:'" + check_place + '\'' +
                ", states:'" + states + '\'' +
                '}';
    }
}
