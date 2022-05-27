package org.example;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

public class DefenderServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    //    private List<RuleChecker> ruleCheckers;

    public void start(int port) throws Exception {
        //        this.rules = loadRuleCheckers();
        serverSocket = new ServerSocket(port);
        while (true) {
            new DefenderClientHandler(serverSocket.accept()).start();
        }

    }

    private static class DefenderClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedWriter out;
        private BufferedReader in;

        public DefenderClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    out.write("yoyooy");
                    out.newLine();
                    System.out.println("hi" + inputLine);
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            } finally {
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(out);
                IOUtils.closeQuietly(clientSocket);
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        DefenderServer server = new DefenderServer();
//        server.start(6666);
        ServerSocket server = null;
        Socket client = null;
        PrintStream out = null;
        BufferedReader buf = null;
        server = new ServerSocket(6666);
        client = server.accept();
        buf = new BufferedReader(
                new InputStreamReader(client
                        .getInputStream()));
        out = new PrintStream(
                client.getOutputStream());
        String str = buf.readLine();
        out.println("reponse from server: "+str);
        System.out.println("HELLO"+str);
        out.close();
        client.close();






//
//
//        try (ServerSocket server = new ServerSocket(6666);
//             Socket client = server.accept();
//             BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
//             BufferedWriter outToClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
//
//            String in = inFromClient.readLine(), out;
//            System.out.println(in);
//            while(in != null){
//                String out1 = in + " from server" + "\n";
//                outToClient.write(out1);
////                outToClient.newLine();
////                outToClient.flush();
//                in = inFromClient.readLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
