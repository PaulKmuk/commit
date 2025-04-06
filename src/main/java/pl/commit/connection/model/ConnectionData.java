package pl.commit.connection.model;

import java.time.LocalDateTime;
import java.util.List;

public class ConnectionData {

    private int gid;
    private String company;
    private String status;
    private LocalDateTime insstmp;
    private LocalDateTime updstmp;

    private List<SingleConnectionData> listConnection;

    public ConnectionData(int gid, String company, String status, LocalDateTime insstmp, LocalDateTime updstmp, List<SingleConnectionData> listConnection) {
        this.gid = gid;
        this.company = company;
        this.status = status;
        this.insstmp = insstmp;
        this.updstmp = updstmp;
        this.listConnection = listConnection;
    }

    public static class SingleConnectionData {

        private int gid;
        private int conid;
        private String ip;
        private String password;
        private String description;
        private String status;
        private int isActive;
        private int activuser;
        private LocalDateTime insstmp;
        private LocalDateTime updstmp;
        private String usrlogin;

        public SingleConnectionData(int gid, int conid, String ip, String password, String description, String status, int isActive, int activuser, LocalDateTime insstmp, LocalDateTime updstmp, String usrlogin) {
            this.gid = gid;
            this.conid = conid;
            this.ip = ip;
            this.password = password;
            this.description = description;
            this.status = status;
            this.isActive = isActive;
            this.activuser = activuser;
            this.insstmp = insstmp;
            this.updstmp = updstmp;
            this.usrlogin = usrlogin;
        }

        public String getUsrlogin() {
            return usrlogin;
        }

        public void setUsrlogin(String usrlogin) {
            this.usrlogin = usrlogin;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public int getConid() {
            return conid;
        }

        public void setConid(int conid) {
            this.conid = conid;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public int getActivuser() {
            return activuser;
        }

        public void setActivuser(int activuser) {
            this.activuser = activuser;
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
            return "SingleConnectionData{" +
                    "gid=" + gid +
                    ", conid=" + conid +
                    ", ip='" + ip + '\'' +
                    ", password='" + password + '\'' +
                    ", description='" + description + '\'' +
                    ", status='" + status + '\'' +
                    ", isActive=" + isActive +
                    ", activuser=" + activuser +
                    ", insstmp=" + insstmp +
                    ", updstmp=" + updstmp +
                    ", usrlogin='" + usrlogin + '\'' +
                    '}';
        }
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public List<SingleConnectionData> getListConnection() {
        return listConnection;
    }

    @Override
    public String toString() {
        return "ConnectionData{" +
                "gid=" + gid +
                ", company='" + company + '\'' +
                ", status='" + status + '\'' +
                ", insstmp=" + insstmp +
                ", updstmp=" + updstmp +
                ", listConnection=" + listConnection +
                '}';
    }
}
