package es.unavarra.tlm.tablero;

public class API_Request_Game {
    private String ship_type;
    private API_Response_Game.Position[] positions;

    public API_Request_Game(String ship_type, API_Response_Game.Position[] positions) {
        this.ship_type = ship_type;
        this.positions = positions;
    }

    public String getShip_type() {
        return ship_type;
    }

    public API_Response_Game.Position[] getPositions() {
        return positions;
    }
}
