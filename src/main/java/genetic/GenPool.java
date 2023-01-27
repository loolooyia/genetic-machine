package genetic;

import java.util.ArrayList;
import java.util.List;

public abstract class GenPool {

    private List<Gen> genes;

    public GenPool() {
        genes = new ArrayList<>();
        generateGenes();
    }

    protected abstract void generateGenes();

    public List<Gen> getGenes() {
        return genes;
    }
}
