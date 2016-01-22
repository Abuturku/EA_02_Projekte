import Configuration.Configuration;
import Configuration.MersenneTwisterFast;

import java.util.Arrays;

public class Crossover implements ICrossover {
	MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
	private int nProjects = Configuration.INSTANCE.NUMBER_OF_PROJECTS;

	public IChromosome[] doCrossover(IChromosome parent1, IChromosome parent2) {
		IChromosome[] children = new IChromosome[2];
		switch (Configuration.CROSSOVER_TYPE) {
		case ONE_POINT:
			children = doOnePointCrossover(parent1, parent2);
			break;
		case TWO_POINT:
			children = doTwoPointCrossover(parent1, parent2);
			break;
		case K_POINT:
			children = doKPointCrossover(parent1, parent2);
			break;
		case UNIFORM:
			children = doUniformCrossover(parent1, parent2);
			break;
		}
		return children;
	}

	private IChromosome[] doOnePointCrossover(IChromosome parent1, IChromosome parent2) {
		IChromosome[] children = new IChromosome[2];
		IChromosome[] parents = new IChromosome[] { parent1, parent2 };
		int numberOfInvalidPos = 0;
		int[] invalidCOPosition = new int[nProjects-2];
		int numberOfHealthyChildren = 0;

		for (int switchCount = 0; numberOfHealthyChildren < 2; switchCount++) {
			int switchValue = switchCount % 2;
			int randomSplit;
			randomSplit = getRandomSplit(numberOfInvalidPos, invalidCOPosition);
			// here is an untested Splitting Number!
			String chromosomeStringConcatFromParents = concatParentChromosomeOnSplitPosition(parents[switchValue],
					parents[1 - switchValue], randomSplit);
			IChromosome child = new Chromosome(chromosomeStringConcatFromParents);
			if (child.isInPriceBudget()) {
				children[numberOfHealthyChildren] = child;
				numberOfHealthyChildren++;
			}

		}

		return children;
	}

	private String concatParentChromosomeOnSplitPosition(IChromosome parent1, IChromosome parent2, int randomSplit) {
		return parent1.getChromosome().substring(0, randomSplit)
				.concat(parent2.getChromosome().substring(randomSplit, nProjects-1));
	}

	private int getRandomSplit(int numberOfInvalidPos, int[] invalidCOPosition) {
		int randomSplit;
		if (numberOfInvalidPos == 0) {
			randomSplit = randomGenerator.nextInt(1, nProjects-2);
		} else if (numberOfInvalidPos == nProjects-2) {
			throw new IllegalStateException("These parents can't have valid childs... sorry.");
		} else {
			boolean tryAgain = false;
			do {
				randomSplit = randomGenerator.nextInt(1, nProjects-2);
				for (int i = 0; i < numberOfInvalidPos; i++) {
					tryAgain = (invalidCOPosition[i] == randomSplit);
				}
			} while (tryAgain);
		}
		return randomSplit;
	}

	private IChromosome[] doTwoPointCrossover(IChromosome parent1, IChromosome parent2) {
		IChromosome[] children = new IChromosome[2];
		int numberOfHealthyChildren = 0;

		do {
			int randomSplit1;
			int randomSplit2;
			randomSplit1 = randomGenerator.nextInt(1, nProjects-2);
			do {
				randomSplit2 = randomGenerator.nextInt(1, nProjects-2);
			} while (randomSplit1 == randomSplit2);

			int firstSplit = (randomSplit1 < randomSplit2) ? randomSplit1 : randomSplit2;
			int secondSplit = (randomSplit1 > randomSplit2) ? randomSplit1 : randomSplit2;

			// breed 1st child
			IChromosome child = new Chromosome(parent1.getChromosome().substring(0, firstSplit)
					.concat(parent2.getChromosome().substring(firstSplit, secondSplit))
					.concat(parent1.getChromosome().substring(secondSplit, nProjects-1)));
			if (child.isInPriceBudget()) {
				children[numberOfHealthyChildren] = child;
				numberOfHealthyChildren++;
			}
			// breed 2nd child
			child = new Chromosome(parent2.getChromosome().substring(0, firstSplit)
					.concat(parent1.getChromosome().substring(firstSplit, secondSplit))
					.concat(parent2.getChromosome().substring(secondSplit, nProjects-1)));
			if (numberOfHealthyChildren < 2) {
				if (child.isInPriceBudget()) {
					children[numberOfHealthyChildren] = child;
					numberOfHealthyChildren++;
				}
			}

		} while (numberOfHealthyChildren < 2);

		return children;
	}

	private IChromosome[] doKPointCrossover(IChromosome parent1, IChromosome parent2) {
		int k = Configuration.K_FOR_CROSS_OVER;
		if (k < 3) {
			throw new IllegalArgumentException("Please use ONE_POINT for k=1 or TWO_POINT for k=2. "
					+ "kPoint works only for values grater than 2.");
		}

		IChromosome[] children = new IChromosome[2];
		int numberOfHealthyChildren = 0;

		do {
			int[] randomSplit = new int[k];
			randomSplit[0] = randomGenerator.nextInt(1, nProjects-2);
			for (int i = 1; i < k; i++) {
				boolean isNew = true;
				randomSplit[i] = randomGenerator.nextInt(1, nProjects-2);
				for (int j = 1; j < i; j++) {
					isNew = randomSplit[i] == randomSplit[j];
					if (isNew) {
						break;
					}
				}
			}

			int[] sortedSplit = Arrays.copyOf(randomSplit, k + 2);
			sortedSplit[k] = 0;
			sortedSplit[k + 1] = nProjects-1;
			Arrays.sort(sortedSplit);

			// breed 1st child
			String childChromosome1 = "";
			for (int i = 0; i < k; i++) {
				IChromosome currentParent = (i % 2 == 0) ? parent1 : parent2;
				childChromosome1 += currentParent.getChromosome().substring(sortedSplit[i], sortedSplit[i + 1]);
			}

			IChromosome child = new Chromosome(childChromosome1);
			if (child.isInPriceBudget()) {
				children[numberOfHealthyChildren] = child;
				numberOfHealthyChildren++;
			}
			// breed 2nd child
			String childChromosome2 = "";
			for (int i = 0; i < k; i++) {
				IChromosome currentParent = (i % 2 == 0) ? parent1 : parent2;
				childChromosome2 += currentParent.getChromosome().substring(sortedSplit[i], sortedSplit[i + 1]);
			}

			child = new Chromosome(childChromosome2);
			if (numberOfHealthyChildren < 2) {
				if (child.isInPriceBudget()) {
					children[numberOfHealthyChildren] = child;
					numberOfHealthyChildren++;
				}
			}

		} while (numberOfHealthyChildren < 2);

		return children;
	}

	private IChromosome[] doUniformCrossover(IChromosome parent1, IChromosome parent2) {
		IChromosome[] children = new IChromosome[2];
		float ratio = 100 * Configuration.MIXING_RATIO; // in Prozent
		if (ratio <= 0 || ratio >= 100) {
			throw new IllegalArgumentException("The mixing ratio has to be a value between 0 and 1.");
		}

		char[] caParent1 = parent1.getChromosome().toCharArray();
		char[] caParent2 = parent2.getChromosome().toCharArray();
		char[] caChild1 = new char[nProjects-1];
		char[] caChild2 = new char[nProjects-1];

		int parent1Full = (int) (1.5 * ratio);
		int parent2Full = (int) (1.5 * ratio);
		int countParent1 = 0;
		int countParent2 = 0;

		int numberOfHealthyChildren = 0;
		do {
			for (int i = 0; i < nProjects-1; i++) {
				int randNr = randomGenerator.nextInt(0, 100);

				if (countParent1 < parent1Full && randNr <= ratio) {
					caChild1[i] = caParent1[i];
					caChild2[i] = caParent2[i];
				} else if (countParent2 < parent2Full && randNr > ratio) {
					caChild1[i] = caParent2[i];
					caChild2[i] = caParent1[i];
				}
			}

			IChromosome child = new Chromosome(caChild1.toString());
			if (child.isInPriceBudget()) {
				children[numberOfHealthyChildren] = child;
				numberOfHealthyChildren++;
			}

			child = new Chromosome(caChild2.toString());
			if (numberOfHealthyChildren < 2) {
				if (child.isInPriceBudget()) {
					children[numberOfHealthyChildren] = child;
					numberOfHealthyChildren++;
				}
			}

		} while (numberOfHealthyChildren < 2);

		return children;
	}

}
