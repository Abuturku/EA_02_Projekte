import Configuration.Configuration;
import Configuration.MersenneTwisterFast;

public class Chromosome implements IChromosome {
	MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

	private String chromosomeString;

	public Chromosome(String chromosomeString) {
		this.chromosomeString = chromosomeString;
	}

	public Chromosome() {
		generateRandomChromosome();
	}

	public IChromosome generateRandomChromosome() {
		do {
			this.chromosomeString = generateRandomChromosomeString();
		} while (!this.isInPriceBudget());
		return this;
	}

	private String generateRandomChromosomeString() {
		StringBuilder characters = new StringBuilder();
		for (int index = 0; index < Configuration.NUMBER_OF_PROJECTS; index++) {
			if (randomGenerator.nextInt(0, 100) < 80) {
				characters.append(0);
			} else {
				characters.append(1);
			}

		}
		return characters.toString();
	}

	public String getChromosome() {
		return chromosomeString;

	}

	public boolean isInPriceBudget() {
		if (this.getCost() >= Configuration.MAX_BUDGET) {
			return false;
		} else {
			return true;
		}
	}

	private int getCost() {
		char[] characters = chromosomeString.toCharArray();
		int costOfChromosome = 0;
		for (int index = 0; index < characters.length; index++) {
			char character = characters[index];
			if (character == '1') {
				costOfChromosome += Application.PROJECTS[index].getCost();
			}

		}
		return costOfChromosome;
	}

	public int getFitness() {
		char[] characters = chromosomeString.toCharArray();
		int fitnessOfChromosome = 0;
		for (int index = 0; index < characters.length; index++) {
			char character = characters[index];
			if (character == '1') {
				fitnessOfChromosome += Application.PROJECTS[index].getFitness();
			}

		}

		return fitnessOfChromosome;

	}

	public int compareTo(IChromosome chromosome) {
		int fitnessOne = this.getFitness();
		int fitnessTwo = chromosome.getFitness();
		if (fitnessOne < fitnessTwo) {
			return 1;
		} else if (fitnessOne > fitnessTwo) {
			return -1;
		} else {
			return 0;
		}
	}

}
