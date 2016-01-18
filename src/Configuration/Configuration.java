package Configuration;

/**
 * Created by 9364290 on 18.01.16.
 */
public enum Configuration {

    instance;
    final Enum cossoverType = CrossoverEnum.KPoint;
    final Enum mutationType = MutationEnum.Displacement;
    final Enum selectionType = SelectionEnum.RouletteWheel;

}
