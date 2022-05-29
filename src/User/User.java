package User;

import model.ChessColor;

public class User {
    private final String name;
    public long score;
    private ChessColor color;

    public User(String name,long score){
        this.name=name;
        this.score=score;
        color=ChessColor.NONE;
    }

    public String getName() {
        return name;
    }

    public ChessColor getColor() {
        return color;
    }

    public long getScore() {
        return score;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }


    public void setScore(long score) {
        this.score = score;
    }

}
