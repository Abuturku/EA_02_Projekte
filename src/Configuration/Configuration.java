package Configuration;

/**
 * Created by 9364290 on 18.01.16.
 */
public enum Configuration {

    instance;
    public Enum crossoverType = CrossoverEnum.KPoint;
        public int kForCrossOver = 3; //when kPoint is selected
        public float mixingRation = 0.5f; //when uniform is selected
    public Enum mutationType = MutationEnum.Displacement;
    public Enum selectionType = SelectionEnum.RouletteWheel;
    public MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
    public double mutationRatio = 0.0005;
}
