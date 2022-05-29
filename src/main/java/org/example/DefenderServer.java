package org.example;

import org.apache.commons.io.IOUtils;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.*;

public class DefenderServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    //    private List<RuleChecker> ruleCheckers;

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        while (true) {
            new DefenderClientHandler(serverSocket.accept()).start();
        }
    }

    private static class DefenderClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        private Jedis jedis = new Jedis("localhost", 6380);

        private RuleChecker ruleChecker;

        public DefenderClientHandler(Socket socket) throws Exception {
            this.clientSocket = socket;
            List<Rule> rules = loadRuleCheckers();
            ruleChecker = new RuleChecker(rules);
        }

        private List<Rule> loadRuleCheckers() throws Exception {
            PropertiesLoader loader = new PropertiesLoader("application.resources");
            if(!loader.configurationValidation()){
                throw new InvalidApplicationResourcesException("Invalid Application Resources");
            }

            Properties config = loader.getConfiguration();

            List<Rule> rules = new ArrayList<Rule>();
            for (int i = 1; i < loader.getNumberOfConfigurations() + 1; i++) {
                String key1 = "rule" + String.valueOf(i) + ".count";
                int count = Integer.parseInt(config.getProperty(key1));

                String key2 = "rule" + String.valueOf(i) + ".seconds";
                int second = Integer.parseInt(config.getProperty(key2));

                String key3 = "rule" + String.valueOf(i) + ".url";
                String url = config.getProperty(key3);

                System.out.println(count + " " + second + " " + url);

                rules.add(new Rule(count, second, url, i));
            }

            return rules;
//            this.ruleChecker.setRules(rules);
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine = in.readLine();

                if (inputLine != null) {
                    String[] request = inputLine.split(" ", 2);
                    String ip = request[0];
                    String url = request[1];

                    System.out.println(ip);
                    System.out.println(url);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());

                    Date value = cal.getTime();

                    for(int i = 0; i < this.ruleChecker.getRules().size(); i++){
                        String key = ip + "-" + String.valueOf(i + 1);

                        jedis.rpush(key, String.valueOf(value));
                    }

                    boolean decision = ruleChecker.check(ip, url);

                    if(decision){
                        out.println("true");
                    }
                    else{
                        out.println("false");
                    }

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
    }
}
