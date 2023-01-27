package genetic;

import implementation.main.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Evolution {

    public static void runCycles(World world, List<Entity> entities, int count) {
        for (int i = 0; i < count; i++) {
            for (Entity entity : entities) {
                world.doAction(entity);
            }
            entities.sort(Comparator.comparingDouble(Entity::getRating));
            Collections.reverse(entities);
            int originalSize = entities.size();
            entities.removeIf(entity -> entities.indexOf(entity) >= entities.size() * Constants.ROBOT_SELECTION_PERCENT);
            List<Entity> bestEntities = new ArrayList<>(entities);
            int key = 0;
            while (entities.size() < originalSize) {
                entities.add(bestEntities.get(key).clone());
                key++;
                if (key >= bestEntities.size()) key = 0;
            }
        }
    }

}
