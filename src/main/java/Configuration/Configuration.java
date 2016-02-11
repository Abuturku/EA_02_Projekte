package Configuration;

public enum Configuration {

	INSTANCE;
	public final static CrossoverEnum CROSSOVER_TYPE = CrossoverEnum.UNIFORM;
	public final static int K_FOR_CROSS_OVER = 3; // for kPoint crossover
	public final static float MIXING_RATIO = 0.75f; // for uniform crossover; best results with > 0.5, like 0.75 -> awesome.
	public final static MutationEnum MUTATION_TYPE = MutationEnum.SCRAMBLE; //best results with SCRAMBLE
	public final static SelectionEnum SELECTION_TYPE = SelectionEnum.ROULETTE_WHEEL; //best results with Roulette_wheel
	public final static double MUTATION_RATIO = 0.0005;
	public static int POPULATION_SIZE = 27; //best results with small numbers like 27
	public final static int MAX_BUDGET = 1500; //don't change! given by task.
	public final static int NUMBER_OF_PROJECTS = 150; //don't change! given by task.
	public final static int SIZE_OF_POPULATION_TO_FIND_PARENTS = 8;
	public final static int MAX_GENERATION = 50000;
	// 529 reached max fitness with settings; optimum with another method is 531 (only 9€ not used).
}
