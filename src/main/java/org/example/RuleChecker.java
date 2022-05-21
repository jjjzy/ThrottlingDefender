package org.example;

public class RuleChecker {
    private Rule rule;

    public boolean check(String ip, String url) {
        if (rule.matchUrl(url)) {
            ///.....
            return false;
        } else {
            return true;
        }
    }
}
