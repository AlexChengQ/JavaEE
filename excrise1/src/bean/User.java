package bean;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private int uid;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String birthday;
    private String updatetime;
    private int state;
    private String activecode;
    private String headicon;

    public User() {
    }

    public User(int uid, String username, String nickname, String password, String email, String birthday, String updatetime, int state, String activecode, String headicon) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.updatetime = updatetime;
        this.state = state;
        this.activecode = activecode;
        this.headicon = headicon;
    }

    public String getHeadicon() {
        return headicon;
    }

    public void setHeadicon(String headicon) {
        this.headicon = headicon;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUpdatetime() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getActivecode() {
        return activecode;
    }

    public void setActivecode(String activecode) {
        this.activecode = activecode;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", state=" + state +
                ", activecode='" + activecode + '\'' +
                ", headicon='" + headicon + '\'' +
                '}';
    }
}
