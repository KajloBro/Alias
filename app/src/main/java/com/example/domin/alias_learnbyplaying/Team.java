package com.example.domin.alias_learnbyplaying;

public class Team implements java.io.Serializable {
    private String name;
    private String player1;
    private String player2;
    private int score;

    public String getName() {
        return name;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() { return player2; }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void addScore(int score) { this.score += score; }

    public Team(String name, String player1, String player2) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
        this.score = 0;
    }
}
