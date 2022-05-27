package org.example;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
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
        private PrintWriter out;
        private BufferedReader in;

        public DefenderClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                if ((inputLine = in.readLine()) != null) {
                    out.println("true1");
                    System.out.println("hi " + inputLine);
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
        DefenderServer server = new DefenderServer();
        server.start(6666);
//        ServerSocket server = null;
//        Socket client = null;
//        PrintStream out = null;
//        BufferedReader buf = null;
//        server = new ServerSocket(6666);
//        client = server.accept();
//        buf = new BufferedReader(
//                new InputStreamReader(client
//                        .getInputStream()));
//        out = new PrintStream(
//                client.getOutputStream());
//        String str = buf.readLine();
//        out.println("Echo:"+str);
//        System.out.println("HELLO"+str);
//        out.close();
//        client.close();
    }
}
