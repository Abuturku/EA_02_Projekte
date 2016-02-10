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
			children = doKPointCrossover(parent1, parent2, Configuration.K_FOR_CROSS_OVER);
			break;
		case UNIFORM:
			children = doUniformCrossover(parent1, parent2);
			break;
		}
		return children;
	}

	private IChromosome[] doOnePointCrossover(IChromosome parent1, IChromosome parent2) {
		IChromosome[] children = new IChromosome[2];
		int numberOfInvalidPos = 0;
		int[] invalidCOPosition = new int[nProjects-2];
		int numberOfHealthyChildren = 0;

		while( numberOfHealthyChildren < 2) {
			int randomSplit = getRandomSplit(numberOfInvalidPos, invalidCOPosition);
			// here is an untested Splitting Number!
			String chromosomeStringConcatFromParents = concatParentChromosomeOnSplitPosition(parent1,parent2, randomSplit);
			IChromosome child = new Chromosome(chromosomeStringConcatFromParents);
			numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, child);
			if(numberOfHealthyChildren<2){
				String mirroredChromosomeStringConcatFromParents = concatParentChromosomeOnSplitPosition(parent2,parent1, randomSplit);
				IChromosome mirroredChild = new Chromosome(mirroredChromosomeStringConcatFromParents);
				numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, mirroredChild);
			}
		}

		return children;
	}

	private IChromosome[] doTwoPointCrossover(IChromosome parent1, IChromosome parent2) {
		IChromosome[] children = new IChromosome[2];
		int numberOfHealthyChildren = 0;

		while(numberOfHealthyChildren < 2) {
			int randomSplit1 = randomGenerator.nextInt(1, nProjects-2);
			int randomSplit2 = getSecondRandomSplitValue(randomSplit1);
			int firstSplit = getSmallerValue(randomSplit1, randomSplit2);
			int secondSplit = getBiggerValue(randomSplit1, randomSplit2);

			// breed 1st child
			IChromosome child = new Chromosome(concatParentChromosomeOnSplitPosition(parent1, parent2, firstSplit, secondSplit));
			numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, child);
			// breed 2nd child
			if (numberOfHealthyChildren < 2) {
				child = new Chromosome(concatParentChromosomeOnSplitPosition(parent2, parent1, firstSplit, secondSplit));
				numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, child);
			}

		}

		return children;
	}

	private IChromosome[] doKPointCrossover(IChromosome parent1, IChromosome parent2, int kForCrossOver) {
		/**if (k < 3) {
			throw new IllegalArgumentException("Please use ONE_POINT for k=1 or TWO_POINT for k=2. "
					+ "kPoint works only for values grater than 2.");
		}**/

		IChromosome[] children = new IChromosome[2];
		int numberOfHealthyChildren = 0;

		while(numberOfHealthyChildren < 2) {
			int[] randomSplit = getRandomSplitValues(kForCrossOver);

			// breed 1st child
			String childChromosome1 = buildChildChromosomeString(parent1, parent2, randomSplit, kForCrossOver);
			IChromosome child1 = new Chromosome(childChromosome1);
			numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, child1);
			// breed 2nd child
			if (numberOfHealthyChildren < 2) {
				String childChromosome2 = buildChildChromosomeString(parent2, parent1, randomSplit, kForCrossOver);
				IChromosome child2 = new Chromosome(childChromosome2);
				numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, child2);
			}

		}

		return children;
	}

	private IChromosome[] doUniformCrossover(IChromosome parent1, IChromosome parent2) {
		IChromosome[] children = new IChromosome[2];
		float mixingRatio = getValidMixingRatio();

		char[] caParent1 = parent1.getChromosome().toCharArray();
		char[] caParent2 = parent2.getChromosome().toCharArray();
		char[] caChild1 = new char[nProjects];
		char[] caChild2 = new char[nProjects];

		int parent1Full = (int) (1.5 * mixingRatio);
		int parent2Full = (int) (1.5 * mixingRatio);
		int countParent1 = 0;
		int countParent2 = 0;

		int numberOfHealthyChildren = 0;

		do {
			for (int i = 0; i < nProjects-1; i++) {
				int randNr = randomGenerator.nextInt(0, 100);
				boolean takeParent1 = randNr <= mixingRatio;
				boolean takeParent2 = randNr > mixingRatio;
				if(takeParent2 && countParent2 >= parent2Full){ //parent2 is full
					takeParent1 = true;
					takeParent2 = false;
				}
				if(takeParent1 && countParent1 >= parent1Full){ //parent1 is full
					takeParent2 = true;
					takeParent1 = false;
				}

				if (takeParent1) {
					//parent1 is chosen
					caChild1[i] = caParent1[i];
					caChild2[i] = caParent2[i];
					countParent1++;
				}
				else if (takeParent2) {
					//parent2 is chosen
					caChild1[i] = caParent2[i];
					caChild2[i] = caParent1[i];
					countParent2++;
				}
			}
			IChromosome child = new Chromosome(String.valueOf(caChild1));
			numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, child);

			if (numberOfHealthyChildren < 2) {
				IChromosome mirroredChild = new Chromosome(String.valueOf(caChild2));
				numberOfHealthyChildren = addChildIfPosible(children, numberOfHealthyChildren, mirroredChild);
			}

		} while (numberOfHealthyChildren < 2);

		return children;
	}

	private float getValidMixingRatio() {
		float ratio = 100 * Configuration.MIXING_RATIO; // in Prozent
		if (ratio <= 0 || ratio >= 100) {
			throw new IllegalArgumentException("The mixing ratio in Config has to be a value between 0 and 1.");
		}
		return ratio;
	}

	private int addChildIfPosible(IChromosome[] children, int numberOfHealthyChildren, IChromosome child) {
		if (child.isInPriceBudget()) {
			children[numberOfHealthyChildren] = child;
			numberOfHealthyChildren++;
		}
		return numberOfHealthyChildren;
	}

	private String concatParentChromosomeOnSplitPosition(IChromosome parent1, IChromosome parent2, int randomSplit) {
		return parent1.getChromosome().substring(0, randomSplit)
				.concat(parent2.getChromosome().substring(randomSplit, nProjects));
	}

	private int getRandomSplit(int numberOfInvalidPos, int[] invalidCOPosition) {
		int randomSplit;
		if (numberOfInvalidPos < nProjects-2) {
			randomSplit = getValidRandomSplitValue(numberOfInvalidPos, invalidCOPosition);
		} else{
			throw new IllegalStateException("These parents can't have valid childs... sorry.");
		}
		return randomSplit;
	}

	private int getValidRandomSplitValue(int numberOfInvalidPos, int[] invalidCOPosition) {
		int randomSplit;
		boolean tryAgain = false;
		do {
			randomSplit = randomGenerator.nextInt(1, nProjects-2);
			tryAgain = controlRandomSplitValueIsValid(numberOfInvalidPos, invalidCOPosition, randomSplit, tryAgain);
		} while (tryAgain);
		return randomSplit;
	}

	private boolean controlRandomSplitValueIsValid(int numberOfInvalidPos, int[] invalidCOPosition, int randomSplit, boolean tryAgain) {
		for (int i = 0; i < numberOfInvalidPos; i++) {
			tryAgain = (invalidCOPosition[i] == randomSplit);
		}
		return tryAgain;
	}

	private int getBiggerValue(int randomSplit1, int randomSplit2) {
		return (randomSplit1 > randomSplit2) ? randomSplit1 : randomSplit2;
	}

	private int getSmallerValue(int randomSplit1, int randomSplit2) {
		return (randomSplit1 < randomSplit2) ? randomSplit1 : randomSplit2;
	}

	private String concatParentChromosomeOnSplitPosition(IChromosome parent1, IChromosome parent2, int firstSplit, int secondSplit) {
		return parent1.getChromosome().substring(0, firstSplit)
				.concat(parent2.getChromosome().substring(firstSplit, secondSplit))
				.concat(parent1.getChromosome().substring(secondSplit, nProjects));
	}

	private int getSecondRandomSplitValue(int randomSplit1) {
		int randomSplit2;
		do {
			randomSplit2 = randomGenerator.nextInt(1, nProjects-2);
		} while (randomSplit1 == randomSplit2);
		return randomSplit2;
	}

	private String buildChildChromosomeString(IChromosome parent1, IChromosome parent2, int[] randomSplit, int kForCrossOver) {
		String childChromosome = "";
		for (int i = 0; i <= kForCrossOver; i++) {
			IChromosome currentParent = (i % 2 == 0) ? parent1 : parent2;
			childChromosome += currentParent.getChromosome().substring(randomSplit[i], randomSplit[i + 1]);
		}
		return childChromosome;
	}

	private int[] getRandomSplitValues(int k) {
		int[] randomSplit = new int[k + 2];
		randomSplit[0] = 0;
		randomSplit[1] = nProjects;
		for (int i = 2; i < k+2; i++) {
			randomSplit[i] = getValidRandomSplitValue(i, randomSplit);
		}
		Arrays.sort(randomSplit);
		return randomSplit;
	}

}
