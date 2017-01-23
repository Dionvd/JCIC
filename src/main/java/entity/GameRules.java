package entity;

public class GameRules 
{
	//Node Spread - spreading changes a neutral node to yours, or an enemy node to a neutral node.
	int costSpread = 20; //spreads to 1 adjacent node.
	int costSpreadAll = 80; //spreads to all adjacent nodes.
	int costSpreadLine = 80; //spreads to 5 nodes in a straight line from this node
	
	//Power spread
	int costEnpower = 10; //gives 1% power to all your adjacent nodes.
	int costGreatEnpower = 50; //gives 5% power to all your adjacent nodes.
	
	//Type change - only 1 can be active
	int costPowerline = 50; //this node becomes a powerline node, nodes can consume the power from adjacent or connected powerline nodes.  
	int costOverclock = 80; //this node becomes overclocked, an overclocked node generates power 3x as fast.
	int costGuard = 30; //this node becomes guarded, a guarded node is protected against 1 takeover and blocks line spreads.   
	int costStorage = 30; //this node can store 3x as much energy.
	
	//Offensive
	int costDrain = 5; //consumes 5 power from an adjacent enemy node.
	int costExplode = 80; //neutralizes all nodes within two tiles of this node, including your own.
	
	
    public GameRules() {
    }

    

	public int getCostSpread() {
		return costSpread;
	}


	public void setCostSpread(int costSpread) {
		this.costSpread = costSpread;
	}


	public int getCostSpreadAll() {
		return costSpreadAll;
	}


	public void setCostSpreadAll(int costSpreadAll) {
		this.costSpreadAll = costSpreadAll;
	}


	public int getCostSpreadLine() {
		return costSpreadLine;
	}


	public void setCostSpreadLine(int costSpreadLine) {
		this.costSpreadLine = costSpreadLine;
	}


	public int getCostEnpower() {
		return costEnpower;
	}


	public void setCostEnpower(int costEnpower) {
		this.costEnpower = costEnpower;
	}


	public int getCostGreatEnpower() {
		return costGreatEnpower;
	}


	public void setCostGreatEnpower(int costGreatEnpower) {
		this.costGreatEnpower = costGreatEnpower;
	}


	public int getCostPowerline() {
		return costPowerline;
	}


	public void setCostPowerline(int costPowerline) {
		this.costPowerline = costPowerline;
	}


	public int getCostOverclock() {
		return costOverclock;
	}


	public void setCostOverclock(int costOverclock) {
		this.costOverclock = costOverclock;
	}


	public int getCostGuard() {
		return costGuard;
	}


	public void setCostGuard(int costGuard) {
		this.costGuard = costGuard;
	}


	public int getCostStorage() {
		return costStorage;
	}


	public void setCostStorage(int costStorage) {
		this.costStorage = costStorage;
	}


	public int getCostDrain() {
		return costDrain;
	}


	public void setCostDrain(int costDrain) {
		this.costDrain = costDrain;
	}


	public int getCostExplode() {
		return costExplode;
	}


	public void setCostExplode(int costExplode) {
		this.costExplode = costExplode;
	}

    
    
}
