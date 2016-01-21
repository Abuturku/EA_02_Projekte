package Configuration;

/**
 * Created by 9364290 on 18.01.16.
 */
public enum Configuration {

    instance;
    public CrossoverEnum crossoverType = CrossoverEnum.KPoint;
        public int kForCrossOver = 3; //for kPoint crossover
        public float mixingRatio = 0.5f; //for uniform crossover
    public MutationEnum mutationType = MutationEnum.Displacement;
    public SelectionEnum selectionType = SelectionEnum.RouletteWheel;
    public MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    public double mutationRatio = 0.0005;
    public int populationSize = 100;
}
