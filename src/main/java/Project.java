
public class Project implements IProject {

	private int cost;
	private int fitness;
	private int number;

	public Project(int number, int cost, int fitness) {
		this.cost = cost;
		this.fitness = fitness;
		this.number = number;
	}

	public int getCost() {
		return this.cost;
	}

	public int getFitness() {
		return this.fitness;
	}

	public int getNumber() {
		return this.number;
	}

	@Override
	public String toString() {
		return "Number: " + this.number + " Cost: " + this.cost + " Fitness: " + this.fitness;
	}
}
