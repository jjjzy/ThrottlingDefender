package org.example;

public class Rule {
    String url;
    public boolean matchUrl(String url) {
        return "*".equals(this.url) || this.url.equals(url);
    }

    private int count;
    private int seconds;

    public Rule(int count, int seconds) {
        this.count = count;
        this.seconds = seconds;
    }
}
