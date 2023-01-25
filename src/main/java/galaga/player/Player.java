package galaga.player;

import galaga.gameObjects.PlayerShip;

public class Player {

    private PlayerShip ship;
    private String nickname;
    private float score;


    public float addScore(float score) {
        if (this.ship.getHealth() <= 0.0) return this.score;
        this.setScore(this.score+score);
        return this.score;
    }

    // constructors

    public Player(String nickname) {
        this.nickname = nickname;
    }


    public Player(String nickname, PlayerShip ship) {
        this.nickname = nickname;
        this.ship = ship;
        this.score =  0;
    }

    // getters/setters


    public PlayerShip getShip() {
        return this.ship;
    }


    public void setShip(PlayerShip ship) {
        this.ship = ship;
    }


    public String getNickname() {
        return this.nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public float getScore() {
        return this.score;
    }


    public void setScore(float score) {
        this.score = Math.max(0.0f, score);
    }
}
