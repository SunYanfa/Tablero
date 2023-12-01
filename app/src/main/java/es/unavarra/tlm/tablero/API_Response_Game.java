package es.unavarra.tlm.tablero;

import java.util.List;

public class API_Response_Game {
    private GameInfo game;
    private List<ship> ships;
    private Gunfire gunfire;

    public GameInfo getGame() {
        return game;
    }

    public List<ship> getShips() {
        return ships;
    }

    public Gunfire getGunfire() {
        return gunfire;
    }


    public class ship {
        private String type;
        private Position[] position;

        public String getType() {
            return type;
        }

        public Position[] getPosition() {
            return position;
        }

    }



    public class Gunfire {
        private List<Result> done;
        private List<Result> received;

        public Gunfire(List<Result> done, List<Result> received) {
            this.done = done;
            this.received = received;
        }
        public List<Result> getDone() {
            return done;
        }
        public List<Result> getReceived() {
            return received;
        }
    }

    public static class Position {
        private char row;
        private int column;

        public Position(char row, int column) {
            this.row = row;
            this.column = column;
        }

        public char getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

    }

    public class Result {
        private String result;
        private Position position;

        public Result(String result, Position position) {
            this.result = result;
            this.position = position;
        }

        public String getResult() {
            return result;
        }

        public Position getPosition() {
            return position;
        }
    }

    public class GameInfo {
        private int id;
        private String state;
        private boolean your_turn;
        private boolean you_won;

        private String updated_at;
        private String created_at;
        private String started_at;
        private String finished_at;
        private User you;
        private User enemy;

        public int getId() {
            return id;
        }

        public String getState() {
            return state;
        }

        public boolean isYour_turn() {
            return your_turn;
        }

        public boolean isYou_won() {
            return you_won;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getStarted_at() {
            return started_at;
        }

        public String getFinished_at() {
            return finished_at;
        }

        public User getYou() {
            return you;
        }

        public User getEnemy() {
            return enemy;
        }
    }

    public class User{
        private int id;
        private String username;

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }
    }
}
