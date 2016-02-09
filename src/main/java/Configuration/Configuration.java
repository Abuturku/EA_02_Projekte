package Configuration;

public enum Configuration {

	INSTANCE;
	public final static CrossoverEnum CROSSOVER_TYPE = CrossoverEnum.UNIFORM;
	public final static int K_FOR_CROSS_OVER = 2; // for kPoint crossover
	public final static float MIXING_RATIO = 0.5f; // for uniform crossover
	public final static MutationEnum MUTATION_TYPE = MutationEnum.SCRAMBLE;
	public final static SelectionEnum SELECTION_TYPE = SelectionEnum.ROULETTE_WHEEL;
	public final static double MUTATION_RATIO = 0.0005;
	public final static int POPULATION_SIZE = 88; // durch 20 teilbar
	public final static int MAX_BUDGET = 1500;
	public final static int NUMBER_OF_PROJECTS = 150;
	public final static int SIZE_OF_POPULATION_TO_FIND_PARENTS = 10;
	// 529 bis jetzt max fitness
}
