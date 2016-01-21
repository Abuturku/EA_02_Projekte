package Configuration;

/**
 * Created by 9364290 on 18.01.16.
 */
public enum Configuration {

    instance;
    public CrossoverEnum crossoverType = CrossoverEnum.Uniform;
    public int kForCrossOver = 5; //for kPoint crossover
    public float mixingRatio = 0.5f; //for uniform crossover
    public MutationEnum mutationType = MutationEnum.Scramble;
    public SelectionEnum selectionType = SelectionEnum.RouletteWheel;
    public MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    public double mutationRatio = 0.0005;
    public int populationSize = 1000; //durch 20 teilbar
    public int maxBudget = 1500;
    public int numberOfProjects = 150;
    //416 bis jetzt max fitness
}
