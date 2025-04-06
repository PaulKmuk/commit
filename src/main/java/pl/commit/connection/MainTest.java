package pl.commit.connection;

import pl.commit.connection.dao.ConnectionDataDAO;
import pl.commit.connection.model.ConnectionData;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MainTest {
   public static void openRemoteDesktopWithIp(String ip) {
      try {
         // Otwórz mstsc.exe
         Runtime.getRuntime().exec("cmd.exe /c start mstsc");

         // Poczekaj chwilę, aż okno się otworzy (np. 1 sekunda)
         Thread.sleep(1000);

         // Symulacja wpisywania IP przez PowerShell
         String powershellCommand = "powershell -Command \"$wshell = New-Object -ComObject WScript.Shell; $wshell.SendKeys('" + ip + "')\"";
         Runtime.getRuntime().exec(powershellCommand);
      } catch (IOException | InterruptedException e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {

      openRemoteDesktopWithIp("192.168.1.100");

   }
}
