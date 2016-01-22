import Configuration.SelectionEnum;
import Configuration.MersenneTwisterFast;

import java.util.Arrays;

/**
 * Created by Jan.Rissmann on 18.01.2016.
 */
public class Selection implements ISelection{
    MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.nanoTime());


    public IChromosome[] getParents(IPopulation population, SelectionEnum selectionType){

        IPopulation fatherPartOfPopulation = getFatherPartOfPopulation(population);
        IPopulation motherPartOfPopulation = getMotherPartOfPopulation(population);
        IChromosome[] parents = new Chromosome[2];

        switch (selectionType){
            case ROULETTE_WHEEL:
                parents = getParentsWithRouletteWheelSelection(fatherPartOfPopulation, motherPartOfPopulation);
                break;
            case TOURNAMENT:
                parents = getParentsWithTournamentSelection(fatherPartOfPopulation, motherPartOfPopulation);
                break;
        }

        return parents;
    }

    private IChromosome[] getParentsWithTournamentSelection(IPopulation fatherPartOfPopulation, IPopulation motherPartOfPopulation) {
        IChromosome[] parents = new Chromosome[2];
        parents[0] = doTournamentSelection(fatherPartOfPopulation);
        parents[1] = doTournamentSelection(motherPartOfPopulation);
        return parents;
    }

    private IChromosome[] getParentsWithRouletteWheelSelection(IPopulation fatherPartOfPopulation, IPopulation motherPartOfPopulation) {
        IChromosome[] parents = new Chromosome[2];
        parents[0] = doRouletteWheelSelection(fatherPartOfPopulation);
        parents[1] = doRouletteWheelSelection(motherPartOfPopulation);
        return parents;
    }

    private IChromosome doRouletteWheelSelection(IPopulation partOfPopulation) {
        double count = -0.00001;
        double sumPopulationFitness = partOfPopulation.getSumPopulationFitness();
        double randomValue = getRandomValueBetweenNullAndOne();

        for(IChromosome chromosome : partOfPopulation.getPopulation()){
            Range range = getRangeFromChromosomeInPopulation(count, sumPopulationFitness, chromosome);
            if (range.inRange(randomValue)){
                return chromosome;
            }
            count = range.getMax();
        }
        return partOfPopulation.getLastChromosomeOfPopulation();
    }


    private Population getMotherPartOfPopulation(IPopulation population) {
        return new Population(Arrays.copyOfRange(population.getPopulation(), population.getPopulation().length / 2 + 1, population.getPopulation().length - 1));
    }

    private Population getFatherPartOfPopulation(IPopulation population) {
        return new Population(Arrays.copyOfRange(population.getPopulation(), 0, population.getPopulation().length / 2));
    }

    private Range getRangeFromChromosomeInPopulation(double count, double sumPopulationFitness, IChromosome chromosome) {
        double selectionProbability = getSelectionProbabilityFromChromosomeInPopulation(sumPopulationFitness, chromosome);
        return new Range(count + 0.00001, count + selectionProbability);
    }

    private double getSelectionProbabilityFromChromosomeInPopulation(double sumPopulationFitness, IChromosome chromosome) {
        return Math.round(chromosome.getFitness()/sumPopulationFitness * 100000.0) / 100000.0 - 0.00001;
    }

    private double getRandomValueBetweenNullAndOne() {
        double randomValue = randomGenerator.nextDouble(true, false);//1 ausgeschlossen 0 eingeschlossen
        randomValue = (int)(randomValue * 100000.0) / 100000.0;
        return randomValue;
    }

    private IChromosome doTournamentSelection(IPopulation population){
        return population.getFittest();
    }

}
