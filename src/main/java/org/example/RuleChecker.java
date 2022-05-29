package org.example;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RuleChecker {
    private List<Rule> rules;

    public RuleChecker(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public Rule findMatchRule(String url){
        for(Rule i : this.rules){
            if(i.matchUrl(url)){
                return i;
            }
        }
        return null;
    }

    public boolean check(String ip, String url) {
//        if (rule.matchUrl(url)) {
//            ///.....
//            return false;
//        } else {
//            return true;
//        }

        Jedis jedis = new Jedis("localhost", 6380);
        Rule matchingRule = findMatchRule(url);

        int count = matchingRule.getCount();
        int second = matchingRule.getSeconds();
        int identifier = matchingRule.getRuleIdentifier();

        String key = ip + "-" + String.valueOf(identifier);
        List<String> timeStamp = jedis.lrange(key, 0, -1);

        if(timeStamp.size() <= count){
            return true;
        }

////        for(String i : timeStamp){
//        System.out.println(timeStamp);
////        }
//
//        System.out.println(newDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(timeStamp.get(timeStamp.size() - 1)));
        System.out.println(cal.getTime());
        cal.add(Calendar.SECOND, -second);
        System.out.println(cal.getTime());
        System.out.println("--------------------------");

        Date minimumDate = cal.getTime();

        int qualifiedTimeCount = 0;
        for(String i : timeStamp){
            Date curDate = new Date(i);
            int result = minimumDate.compareTo(curDate);

            if(result <= 0){
                qualifiedTimeCount++;
            }
        }

        if(qualifiedTimeCount > count){
            return false;
        }




        return true;
    }
}
