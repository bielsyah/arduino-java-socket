package towwayserialcomm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class socket 
{    
    static String ipAddress = "";
    static int port = 5050;
    
    public static void main(String[] args)throws Exception
    {
        try
        {
            Socket mySocket = new Socket(ipAddress, port);
            
            InputStreamReader in = new InputStreamReader(mySocket.getInputStream());
            PrintWriter out = new PrintWriter(mySocket.getOutputStream());
            
            Reader reader = new Reader(in);
            Writer writer = new Writer(out);
            
            Thread readerThread = new Thread(reader);
            Thread writerThread = new Thread(writer);
            
            readerThread.start();
            writerThread.start();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private static class Reader implements Runnable
    {
        InputStreamReader in;
        public Reader(InputStreamReader in)
        {
            this.in = in;
        }
        @Override
        public void run() 
        {
            try 
            {
                BufferedReader bfReader = new BufferedReader(in);
                String line;
                while ((line = bfReader.readLine()) != null) 
                {
                    System.out.println(line);
                }
                bfReader.close();
            }
            catch (Exception ex) 
            {
                System.out.println(ex.getMessage());
            }
        }    
    }    
    private static class Writer implements Runnable
    {
        PrintWriter pw;
        public Writer(PrintWriter pw)
        {
            this.pw = pw;
        }

        @Override
        public void run()
        {
            try 
            {
                Scanner scanner = new Scanner(System.in);
                String msgInput;
                while (!Thread.currentThread().isInterrupted()) 
                {
                    msgInput = scanner.nextLine();
                    pw.println(msgInput);
                    pw.flush();
                }
                scanner.close();
            } 
            catch (Exception ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
