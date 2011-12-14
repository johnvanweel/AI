package nl.johnvanweel.particlefilters.actor.agent.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask capable of calculating the Successor State for a List of Particles.
 * User: John van Weel
 * Date: 12/14/11
 * Time: 4:54 PM
 */
public class SuccessorStatePredictor extends RecursiveTask<List<Particle>> {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 460;

    private final List<Particle> particleList;
    private final int dX;
    private final int dY;
    private final Random random = new Random();

    public SuccessorStatePredictor(List<Particle> particleList, int dX, int dY) {
        this.particleList = particleList;
        this.dX = dX;
        this.dY = dY;
    }

    @Override
    protected List<Particle> compute() {
        if (particleList.size() <= 500) {
            List<Particle> particleListPrime = new ArrayList<>();

            for (int i = 0; i < particleList.size(); i++) {
                Particle p = particleList.get(i);

                Particle pPrime = new Particle(p.getX() + dX + (random.nextInt(10) - 5), p.getY() + (random.nextInt(10) - 5) + dY, p.getWeight());

                if (isParticleOutOfArea(pPrime)) {
                    pPrime = createRandomParticle(p.getWeight());
                }

                particleListPrime.add(pPrime);
            }

            return particleListPrime;
        } else {
            return splitTasks();
        }
    }

    private List<Particle> splitTasks() {
        int length = particleList.size() / 3;

        List<Particle> left = null;
        List<Particle> right = null;
        List<Particle> middle = null;

        ForkJoinTask<List<Particle>> p1 = new SuccessorStatePredictor(particleList.subList(0, length), dX, dY).fork();
        ForkJoinTask<List<Particle>> p2 = new SuccessorStatePredictor(particleList.subList(length, length * 2), dX, dY).fork();
        ForkJoinTask<List<Particle>> p3 = new SuccessorStatePredictor(particleList.subList(length * 2, length * 3), dX, dY).fork();

        left = p1.join();
        middle = p2.join();
        right = p3.join();

        left.addAll(middle);
        left.addAll(right);

        return left;
    }

    private Particle createRandomParticle(double weight) {
        Particle p = new Particle(random.nextInt(WIDTH), random.nextInt(HEIGHT), weight);
        return p;
    }

    private boolean isParticleOutOfArea(Particle pPrime) {
        return pPrime.getX() > WIDTH + WIDTH / 10 || pPrime.getY() > HEIGHT + HEIGHT / 10;
    }
}
