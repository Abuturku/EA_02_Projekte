/**
 * Created by 9364290 on 18.01.16.
 */
public class Projekt implements IProjekt {

    private int cost;
    private int fitness;

    public Projekt (int cost, int fitness){
        this.cost = cost;
        this.fitness = fitness;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getFitness() {
        return this.fitness;
    }
}
