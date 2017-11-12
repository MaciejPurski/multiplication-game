package model;

public class Player {

    private int AIDepth;
    private Character pchar;

    public Player() {
        this.AIDepth = 0;
        this.pchar = Character.HUMAN;
    }

    public Player(int AIDepth) {
        this.AIDepth = AIDepth;
        this.pchar = Character.AI;
    }

    public int getAIDepth() {
        return AIDepth;
    }

    public Character getPchar() {
        return pchar;
    }

    public enum Character {HUMAN, AI}
}
