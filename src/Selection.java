import Configuration.Configuration;
import Configuration.SelectionEnum;

/**
 * Created by Jan.Rissmann on 18.01.2016.
 */
public class Selection implements ISelection{


    public Chromosome[] topTenPercentOfPopulation(IPopulation population){


        return null;
    }

    public IChromosome[] getParents(IPopulation population, SelectionEnum selectionType){
        IChromosome[] parents = new Chromosome[2];

        IPopulation partOfPopulationFather = new Population(new Chromosome[population.getPopulation().length/2]);
        IPopulation partOfPopulationMother = new Population(new Chromosome[population.getPopulation().length/2]);

        for(int i=0; i < population.getPopulation().length; i+=2){
            partOfPopulationFather.getPopulation()[i/2] = population.getPopulation()[i];
            partOfPopulationMother.getPopulation()[i/2] = population.getPopulation()[i+1];
        }

        switch (selectionType){
            case RouletteWheel:
                parents[0] = doRouletteWheelSelection(partOfPopulationFather);
                parents[1] = doRouletteWheelSelection(partOfPopulationMother);
                break;
            case Tournament:
                parents[0] = doTournamentSelection(partOfPopulationFather);
                parents[1] = doTournamentSelection(partOfPopulationMother);
                break;
        }

        return parents;
    }

    private IChromosome doRouletteWheelSelection(IPopulation partOfPopulation) {
        double count = 0;
        double sumPopulationFitness = partOfPopulation.getSumPopulationFitness();

        double randomValue = Configuration.instance.randomGenerator.nextDouble(true, false);//1 ausgeschlossen 0 eingeschlossen
        randomValue = (int)(randomValue * 100000.0) / 100000.0;
        //System.out.println("START");
        for(IChromosome chromosome : partOfPopulation.getPopulation()){
            double selectionProbability = Math.round(chromosome.getFitness()/sumPopulationFitness * 100000.0) / 100000.0 - 0.00001;
            Range range = new Range(count, count + selectionProbability);
            if (range.inRange(randomValue)){
                //System.out.println("ENDE");
                return chromosome;
            }
            count+=selectionProbability;
            //System.out.println("RANGE: " + range.min +" < "+ range.max);
        }


        //System.out.println("IHR LIEGT FALSCH " +randomValue);
        //System.exit(2);
        return partOfPopulation.getPopulation()[partOfPopulation.getPopulation().length-1];
    }

    private IChromosome doTournamentSelection(IPopulation population){
        return population.getFittest();
    }

}
