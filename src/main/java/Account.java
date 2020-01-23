import java.io.Serializable;

public class Account  implements Serializable {
    private String title;
    private String username;
    private String password;
    private String url;

    public Account() {
        this.title = "";
        this.username = "";
        this.password = "";
        this.url = "";
    }

    public Account(String title, String username, String password, String url) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (title != null ? !title.equals(account.title) : account.title != null) return false;
        if (username != null ? !username.equals(account.username) : account.username != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        return url != null ? url.equals(account.url) : account.url == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.title + "\nUsername: " + this.username + "\nPassword: " + this.password;
    }
}
