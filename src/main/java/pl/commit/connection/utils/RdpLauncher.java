package pl.commit.connection.utils;

import pl.commit.connection.dao.ConnectionDataDAO;
import pl.commit.connection.model.ConnectionData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class RdpLauncher {


   public static void openRdpCredentials(ConnectionData.SingleConnectionData singleConnectionData) {
      try {
         String cmdkeyCmd = String.format("cmdkey /generic:TERMSRV/%s /user:%s /pass:", singleConnectionData.getIp(), singleConnectionData.getUsrlogin());
         Runtime.getRuntime().exec(cmdkeyCmd);

         String mstscCmd = String.format("cmd.exe /c start mstsc /v:%s", singleConnectionData.getIp());
         Runtime.getRuntime().exec(mstscCmd);

         // update na bazie o aktywnym połączeniu
         ConnectionDataDAO.setConnectedUser(singleConnectionData, true);
         System.out.println("uruchomienie połączenia");
         // uruchomienie wątku
         new Thread(() -> {
            boolean running = true;
            while (running) {
               try {
                  Thread.sleep(5000);
                  if(!isMstscRunning()){
                     running = false;
                     System.out.println("mstsc.exe został zamknięty");
                     // update na bazie że połączenie zostało zakonczone
                     ConnectionDataDAO.setConnectedUser(singleConnectionData, false);
                     Runtime.getRuntime().exec("cmdkey /delete:TERMSRV/" + singleConnectionData.getIp());
                  }
                  System.out.println("mstsc.exe połącznie aktywne");
               } catch (InterruptedException e) {
                  e.printStackTrace();
                  break;
               } catch (IOException e) {
                  throw new RuntimeException(e);
               }
            }
         }).start();

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   // sprawdzenie czy zdalny pulpit jest połączony
   public static boolean isMstscRunning() throws IOException {
      Process process = Runtime.getRuntime().exec("tasklist");
      try (InputStream inputStream = process.getInputStream();
           Scanner scanner = new Scanner(inputStream)){
         while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.toLowerCase().contains("mstsc.exe")){
               return true; // process running
            }
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      return false;
   }
}
