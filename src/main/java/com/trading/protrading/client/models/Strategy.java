package com.trading.protrading.client.models;

import java.util.List;

public class Strategy {

    private String name;
    private List<Rule> rules;

    public Strategy(String name, List<Rule> rules) {
        this.name = name;
        this.rules = rules;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Strategy Name - ");
        builder.append(name);
        builder.append('\n');
        builder.append("Rules:\n");
        for (Rule rule : this.rules) {
            builder.append(rule.toString());
        }
        return builder.toString();
    }
}
