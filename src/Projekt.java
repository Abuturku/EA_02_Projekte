/**
 * Created by 9364290 on 18.01.16.
 */
public class Projekt implements IProjekt {

    private int cost;
    private int fitness;
    private int number;

    public Projekt (int cost, int fitness, int number){
        this.cost = cost;
        this.fitness = fitness;
        this.number = number;
    }

    @Override
    public int getCost() {
        return this.cost;
    }

    @Override
    public int getFitness() {
        return this.fitness;
    }

    @Override
    public int getNumber() {
        return this.number;
    }
}
