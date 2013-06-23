package infrastructure;

import entities.Player;

public class PlayerData extends EntityData{
	private Player.Status status;
	public PlayerData(Player e) {
		super(e);
		// TODO Auto-generated constructor stub
		status = e.getStatus();
	}
	public Player.Status getStatus(){
		return status;
	}

}
