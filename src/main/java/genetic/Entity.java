package genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity {

    protected GenPool genPool;
    protected double mutationRatio;
    protected double mutationProbability;
    protected double rating;
    protected List<Gen> genes;

    public Entity(GenPool genPool, int genCount, double mutationRatio, double mutationProbability) {
        this.genPool = genPool;
        this.mutationRatio = mutationRatio;
        this.mutationProbability = mutationProbability;
        genes = new ArrayList<>();
        fillGenes(genCount);
    }

    protected Entity(GenPool genPool, double mutationRatio, double mutationProbability, double rating, List<Gen> genes) {
        this.genPool = genPool;
        this.mutationRatio = mutationRatio;
        this.mutationProbability = mutationProbability;
        this.rating = rating;
        this.genes = genes;
    }

    private void fillGenes(int genCount) {
        for (int i = 0; i < genCount; i++) {
            if (genPool.getGenes().size() > 0) {
                genes.add(genPool.getGenes().get((int) (Math.random() * genPool.getGenes().size())));
            }
        }
    }

    private List<Gen> mutate() {
        List<Gen> newGenes = new ArrayList<>();
        Set<Integer> genesToReplace = new HashSet<>();
        while (genesToReplace.size() < genes.size() * mutationRatio && genesToReplace.size() < genes.size()) {
            genesToReplace.add((int) (Math.random() * genes.size()));
        }
        for (int i = 0; i < genes.size(); i++) {
            if (genesToReplace.contains(i)) {
                if (Math.random() < mutationProbability) {
                    newGenes.add(genPool.getGenes().get((int) (Math.random() * genPool.getGenes().size())));
                } else {
                    newGenes.add(genes.get(i));
                }
            } else {
                newGenes.add(genes.get(i));
            }
        }
        return newGenes;
    }

    protected Entity subClone(Entity entity) {
        return entity;
    }

    public double getRating() {
        return rating;
    }

    public double getMutationRatio() {
        return mutationRatio;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public List<Gen> getGenes() {
        return genes;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Entity clone() {
        return subClone(new Entity(genPool, mutationRatio, mutationProbability, 0, mutate()));
    }

}
