
public class Project implements IProject {

	private int cost;
	private int fitness;
	private int number;

	public Project(int number, int cost, int fitness) {
		this.cost = cost;
		this.fitness = fitness;
		this.number = number;
	}

	@Override
	public int getCost() {
		return this.cost;
	}

	@Override
	public int getFitness() {
		return this.fitness;
	}

	@Override
	public int getNumber() {
		return this.number;
	}

	@Override
	public String toString() {
		return "Number: " + this.number + " Cost: " + this.cost + " Fitness: " + this.fitness;
	}
}
