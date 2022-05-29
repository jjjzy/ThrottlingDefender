package org.example;

public class Rule {
    private String url;
    public boolean matchUrl(String url) {
//        return "*".equals(this.url) || this.url.equals(url);
        return url.contains(this.url);
    }

    private int count;
    private int seconds;

    private int ruleIdentifier;

    public Rule(int count, int seconds, String url, int ruleIdentifier) {
        this.count = count;
        this.seconds = seconds;
        this.url = url;
        this.ruleIdentifier = ruleIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getRuleIdentifier() {
        return ruleIdentifier;
    }

    public void setRuleIdentifier(int ruleIdentifier) {
        this.ruleIdentifier = ruleIdentifier;
    }
}
