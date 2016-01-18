package Configuration;

/**
 * Created by 9364290 on 18.01.16.
 */
public enum Configuration {

    instance;
    public Enum cossoverType = CrossoverEnum.KPoint;
    public Enum mutationType = MutationEnum.Displacement;
    public Enum selectionType = SelectionEnum.RouletteWheel;
    MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());
}
