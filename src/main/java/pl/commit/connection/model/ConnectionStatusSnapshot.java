package pl.commit.connection.model;

import java.util.Objects;

public class ConnectionStatusSnapshot {

   private int gid;
   private int isActive;
   private int activUser;

   public ConnectionStatusSnapshot(int gid, int isActive, int activUser) {
      this.gid = gid;
      this.isActive = isActive;
      this.activUser = activUser;
   }

   @Override
   public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      ConnectionStatusSnapshot that = (ConnectionStatusSnapshot) o;
      return gid == that.gid && isActive == that.isActive && activUser == that.activUser;
   }

   @Override
   public int hashCode() {
      return Objects.hash(gid, isActive, activUser);
   }

   public int getGid() {
      return gid;
   }

   public int getIsActive() {
      return isActive;
   }

   public int getActivUser() {
      return activUser;
   }
}
