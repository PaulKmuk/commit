package pl.commit.connection.model;

import java.time.LocalDateTime;

public class Employee {

    private int id;
    private String name;
    private String login;
    private String password;
    private String status;
    private LocalDateTime insstmp;
    private LocalDateTime updstmp;

    public Employee(int id, String name, String login, String password, String status, LocalDateTime insstmp, LocalDateTime updstmp) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.status = status;
        this.insstmp = insstmp;
        this.updstmp = updstmp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInsstmp() {
        return insstmp;
    }

    public void setInsstmp(LocalDateTime insstmp) {
        this.insstmp = insstmp;
    }

    public LocalDateTime getUpdstmp() {
        return updstmp;
    }

    public void setUpdstmp(LocalDateTime updstmp) {
        this.updstmp = updstmp;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", insstmp=" + insstmp +
                ", updstmp=" + updstmp +
                '}';
    }
}
