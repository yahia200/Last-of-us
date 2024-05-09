package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

public abstract class Hero extends Character {

	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;

	public Hero(String name, int maxHp, int attackDamage, int maxActions) {
		super(name, maxHp, attackDamage);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.supplyInventory = new ArrayList<Supply>();
		this.vaccineInventory = new ArrayList<Vaccine>();
	}
	public String toString() {
		return this.getName() + "\n"+ "Hp: " + this.getCurrentHp()+ "\n"+"Type: "+ this.getClass().getSimpleName() +  "\n"+ "AttackDmg: " + this.getAttackDmg()+ "\n"+ "Actions: " + this.getActionsAvailable()
		+  "\n"+  "Vaccines: " + vaccineInventory.size()+  "\n"+ "Supplies: " + supplyInventory.size();
	}
	
	public String toStringOther() {
		return this.getName() + "\n"+ "Hp: " + this.getCurrentHp() + "\n"+"Type: "+ this.getClass().getSimpleName() +  "\n"+ "AttackDmg: " + this.getAttackDmg()+ "\n"+ "Actions: " + this.getActionsAvailable();
	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}

	public void setActionsAvailable(int actionsAvailable) {
		if (actionsAvailable <= 0)
			this.actionsAvailable = 0;
		else
			this.actionsAvailable = actionsAvailable;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		if (actionsAvailable < 1)
			throw new NotEnoughActionsException("You need at least 1" + "\n" + "action point in" + "\n" + "order to move.");
		int tX = getLocation().x;
		int tY = getLocation().y;
		switch (d) {
		case DOWN:
			tX--;
			break;
		case LEFT:
			tY--;
			break;
		case RIGHT:
			tY++;
			break;
		case UP:
			tX++;
			break;
		}
		if (tX < 0 || tY < 0 || tX > Game.map.length - 1 || tY > Game.map.length - 1)
			throw new MovementException("You cannot move" +"\n"+ "outside the borders" +"\n"+ "of the map.");
		if (Game.map[tX][tY] instanceof CharacterCell && ((CharacterCell) Game.map[tX][tY]).getCharacter() != null)
			throw new MovementException("You cannot move to"+"\n"+"an occuppied cell.");
		else if (Game.map[tX][tY] instanceof CollectibleCell) {
			((CollectibleCell) Game.map[tX][tY]).getCollectible().pickUp(this);
		} else if (Game.map[tX][tY] instanceof TrapCell) {
			this.setCurrentHp(this.getCurrentHp() - ((TrapCell) Game.map[tX][tY]).getTrapDamage());
		}
		Game.map[getLocation().x][getLocation().y] = new CharacterCell(null);
		this.actionsAvailable--;

		if (this.getCurrentHp() ==  0) {
			return;
		}
		Game.map[tX][tY] = new CharacterCell(this);
		setLocation(new Point(tX, tY));
		Game.adjustVisibility(this);
	}

	@Override
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		if (this.getTarget() == null)
			throw new InvalidTargetException("You should select a" +"\n"+ "target to attack first.");
		if (this.getTarget() instanceof Hero)
			throw new InvalidTargetException("You can only" +"\n"+ "attack zombies.");
		if(!Game.zombies.contains(this.getTarget()))
			return;
		if (actionsAvailable < 1)
			throw new NotEnoughActionsException("You need at least 1" +"\n"+ "action point to" +"\n"+ "be able to attack.");
		if (!checkDistance())
			throw new InvalidTargetException("You are only able to" +"\n"+ "attack adjacent targets.");
		super.attack();
		if (this instanceof Fighter && (this.isSpecialAction()))
			return;
		actionsAvailable--;
	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		if (this.getSupplyInventory().size() == 0)
			throw new NoAvailableResourcesException(
					"You need to have at least 1" +"\n"+ "supply in your inventory to" +"\n"+ "use your special abililty.");
		this.supplyInventory.get(0).use(this);
		this.setSpecialAction(true);
	}

	public boolean checkDistance() {
		Point p1 = getLocation();
		Point p2 = getTarget().getLocation();
		if (Math.abs(p1.x - p2.x) > 1)
			return false;
		else if (Math.abs(p1.y - p2.y) > 1)
			return false;
		return true;
	}

	public void cure() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
		if (this.vaccineInventory.size() == 0)
			throw new NoAvailableResourcesException(
					"You need to have at least 1" +"\n"+ "vaccine in your inventory to" +"\n"+ "be able to cure zombies.");
		if (this.actionsAvailable < 1)
			throw new NotEnoughActionsException("You need to have at least 1 " +"\n"+ "action point in "+"\n"+"order to cure a zombie.");
		if (this.getTarget() == null)
			throw new InvalidTargetException("You need to pick a" +"\n"+ "target to cure first.");
		if (!checkDistance())
			throw new InvalidTargetException("You are only able to " +"\n"+ "cure adjacent targets.");
		if (!(this.getTarget() instanceof Zombie))
			throw new InvalidTargetException("You can only"+"\n"+"cure zombies.");
		this.vaccineInventory.get(0).use(this);
		actionsAvailable--;
	}

}
