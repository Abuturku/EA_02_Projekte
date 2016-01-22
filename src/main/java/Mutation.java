import Configuration.Configuration;
import Configuration.MersenneTwisterFast;

import static Configuration.MutationEnum.*;

public class Mutation implements IMutation {
	MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());

	@Override
	public IChromosome doMutation(IChromosome chromosome) {
		if (randomGenerator.nextFloat() <= Configuration.MUTATION_RATIO) {
			if (Configuration.MUTATION_TYPE.equals(DISPLACEMENT)) {
				// System.out.println("Mutation: DISPLACEMENT");
				return doMutationDisplacement(chromosome);
			} else if (Configuration.MUTATION_TYPE.equals(EXCHANGE)) {
				// System.out.println("Mutation: EXCHANGE");
				return doMutationExchange(chromosome);
			} else if (Configuration.MUTATION_TYPE.equals(INSERTION)) {
				// System.out.println("Mutation: INSERTION");
				return doMutationInsertion(chromosome);
			} else if (Configuration.MUTATION_TYPE.equals(INVERSION)) {
				// System.out.println("Mutation: INVERSION");
				return doMutationInversion(chromosome);
			} else if (Configuration.MUTATION_TYPE.equals(SCRAMBLE)) {
				// System.out.println("Mutation: SCRAMBLE");
				return doMutationScramble(chromosome);
			}

			System.out.println("Incorrect ENUM Type");
			return chromosome;
		} else {
			return chromosome;
		}
	}

	private IChromosome doMutationDisplacement(IChromosome chromosome) {
		StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
		int start = randomGenerator.nextInt(chromosomeString.length() / 2);
		int end = randomGenerator.nextInt(start, chromosomeString.length());

		// System.out.println("Displace Chromosome: " +
		// chromosomeString.toString());

		String toDisplace = chromosomeString.substring(start, end);
		chromosomeString.delete(start, end);

		int displaceIndex = randomGenerator.nextInt(chromosomeString.length());
		chromosomeString.insert(displaceIndex, toDisplace);

		// System.out.println("New Chromosome: " + chromosomeString.toString());

		IChromosome newChromosome = new Chromosome(chromosomeString.toString());
		if (newChromosome.isInPriceBudget()) {
			return newChromosome;
		} else {
			// System.out.println("Chromosome is not Valid. Create random
			// Chromosome");
			return chromosome.generateRandomChromosome();
		}
	}

	private IChromosome doMutationExchange(IChromosome chromosome) {
		StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
		int indexOne = randomGenerator.nextInt(chromosomeString.length());
		int indexTwo = randomGenerator.nextInt(chromosomeString.length());

		char charOne = chromosomeString.charAt(indexOne);
		char charTwo = chromosomeString.charAt(indexTwo);

		// System.out.print("Swap Character on '" + indexOne + "' and '" +
		// indexTwo + "' on Chromosome: " + chromosomeString.toString());

		chromosomeString.deleteCharAt(indexOne);
		chromosomeString.insert(indexOne, charTwo);

		chromosomeString.deleteCharAt(indexTwo);
		chromosomeString.insert(indexOne, charOne);

		// System.out.println("New Chromosome: " + chromosomeString.toString());

		IChromosome newChromosome = new Chromosome(chromosomeString.toString());
		if (newChromosome.isInPriceBudget()) {
			return newChromosome;
		} else {
			// System.out.println("Chromosome is not Valid. Create random
			// Chromosome");
			return chromosome.generateRandomChromosome();
		}
	}

	private IChromosome doMutationInsertion(IChromosome chromosome) {
		StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
		int indexCut = randomGenerator.nextInt(chromosomeString.length());
		int indexPaste = randomGenerator.nextInt(chromosomeString.length());

		char cutChar = chromosomeString.charAt(indexCut);

		// System.out.println("Cut char '" + cutChar + "' at '"+ indexCut + "'
		// from Chromosome: " + chromosomeString.toString());

		chromosomeString.deleteCharAt(indexCut);
		chromosomeString.insert(indexPaste, cutChar);

		// System.out.println("New Chromosome: " + chromosomeString.toString());

		IChromosome newChromosome = new Chromosome(chromosomeString.toString());
		if (newChromosome.isInPriceBudget()) {
			return newChromosome;
		} else {
			// System.out.println("Chromosome is not Valid. Create random
			// Chromosome");
			return chromosome.generateRandomChromosome();
		}
	}

	private IChromosome doMutationInversion(IChromosome chromosome) {
		StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
		int start = randomGenerator.nextInt(chromosomeString.length() - 2);
		int end = randomGenerator.nextInt(start, chromosomeString.length());

		// System.out.println("Reverse chromosome: " +
		// chromosomeString.toString());

		StringBuilder toTurn = new StringBuilder(chromosomeString.substring(start, end));
		toTurn.reverse();
		chromosomeString.replace(start, end, toTurn.toString());

		// System.out.println("New chromosome: " + chromosomeString.toString());

		IChromosome newChromosome = new Chromosome(chromosomeString.toString());
		if (newChromosome.isInPriceBudget()) {
			return newChromosome;
		} else {
			// System.out.println("Chromosome is not Valid. Create random
			// Chromosome");
			return chromosome.generateRandomChromosome();
		}
	}

	private IChromosome doMutationScramble(IChromosome chromosome) {
		StringBuilder chromosomeString = new StringBuilder(chromosome.getChromosome());
		int start = randomGenerator.nextInt(chromosomeString.length() - 2);
		int end = randomGenerator.nextInt(start, chromosomeString.length());

		// System.out.println("Mix Chromosome: " + chromosomeString.toString());

		StringBuilder toMix = new StringBuilder(chromosomeString.substring(start, end));

		for (int i = 0; i < toMix.length(); i++) {

			int indexOne = randomGenerator.nextInt(toMix.length());
			int indexTwo = randomGenerator.nextInt(toMix.length());

			char charOne = toMix.charAt(indexOne);
			char charTwo = toMix.charAt(indexTwo);

			toMix.deleteCharAt(indexOne);
			toMix.insert(indexOne, charTwo);

			toMix.deleteCharAt(indexTwo);
			toMix.insert(indexOne, charOne);

		}

		chromosomeString.replace(start, end, toMix.toString());

		// System.out.println("New chromosome: " + chromosomeString.toString());

		IChromosome newChromosome = new Chromosome(chromosomeString.toString());
		if (newChromosome.isInPriceBudget()) {
			return newChromosome;
		} else {
			// System.out.println("Chromosome is not Valid. Create random
			// Chromosome");
			return chromosome.generateRandomChromosome();
		}
	}
}
