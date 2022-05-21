package org.example;

public class Rule {
    String url;
    public boolean matchUrl(String url) {
        return "*".equals(this.url) || this.url.equals(url);
    }
}
