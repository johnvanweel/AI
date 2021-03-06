package nl.johnvanweel.particlefilters.actor.agent.particle;

import nl.johnvanweel.particlefilters.actor.agent.IAgent;
import nl.johnvanweel.particlefilters.actor.sensor.ISensor;
import nl.johnvanweel.particlefilters.actor.sensor.data.SensorData;
import nl.johnvanweel.particlefilters.ui.WorldPanel;
import nl.johnvanweel.particlefilters.util.Centroid;
import nl.johnvanweel.particlefilters.util.Cluster;
import nl.johnvanweel.particlefilters.util.DataPoint;
import nl.johnvanweel.particlefilters.util.JCA;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by IntelliJ IDEA.
 * User: jvweelp
 * Date: 12/12/11
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParticleAgent implements IAgent {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 460;
    private List<Particle> particleList = new ArrayList<>();

    private final Random random = new Random();

    @Autowired
    private WorldPanel panel;

    private final ISensor[] sensors;

    private final int amountOfParticles;

    public ParticleAgent(int amount, ISensor[] sensors) {
        this.amountOfParticles = amount;
        this.sensors = sensors;
    }

    @PostConstruct
    public void postConstruct() {
        this.particleList = seedRandomParticles();
    }

    private List<Particle> seedRandomParticles() {
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < amountOfParticles; i++) {
            particles.add(createRandomParticle((double) 1 / amountOfParticles));
        }

        largestCluster = new Cluster(null);
        largestCluster.setCentroid(new Centroid(0.0D, 0.0D));

        return particles;
    }

    public synchronized void render(Graphics g) {
        for (Particle p : particleList) {
            p.render(g);
        }

        g.setColor(Color.RED);
        g.drawOval((int) largestCluster.getCentroid().getCx() - (10), (int) largestCluster.getCentroid().getCy() - (10), 10, 10);
    }

    @Override
    public synchronized void assessMovement(int dX, int dY, SensorData[] sensorData) {
        double eta = calculateEta(sensorData);
        List<Particle> particleListPrime = predictSuccessorStates(this.particleList, dX, dY);
        computeWeights(sensorData, eta, particleListPrime);
        particleListPrime = sampleNewParticleList(particleListPrime);

        replaceParticles(particleListPrime);

    }

    private Point calculatedLocation = new Point(0, 0);
    private Cluster largestCluster = new Cluster("");

    @Override
    public Point getLocation() {
        return calculatedLocation;
    }

    private void updateCalculatedLocation() {
        // TODO CHECK MODULE 6.10 FOR REPLACEMENT
        Vector<DataPoint> v = new Vector<>();
        for (Particle p : particleList) {
                v.add(new DataPoint(p.getX(), p.getY(), p.toString()));
        }

        JCA jca = new JCA(1, 2, v);
        jca.startAnalysis();

        Cluster largestCluster = null;
        for (Cluster c : jca.getClusters()) {
            if (largestCluster == null || largestCluster.getNumDataPoints() < c.getNumDataPoints()) {
                largestCluster = c;
            }
        }

        calculatedLocation = new Point((int) largestCluster.getCentroid().getCx(), (int) largestCluster.getCentroid().getCy());
        this.largestCluster = largestCluster;
    }

    private List<Particle> predictSuccessorStates(List<Particle> particleList, int dX, int dY) {
        SuccessorStatePredictor p = new SuccessorStatePredictor(particleList, dX, dY);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(p);
        try {
            List<Particle> result = p.get();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            return new ArrayList<>();
        }
    }

    private void replaceParticles(List<Particle> particleListPrime) {
        particleList.clear();
        particleList.addAll(particleListPrime);
        updateCalculatedLocation();
    }

    private List<Particle> sampleNewParticleList(List<Particle> particleListPrime) {
        List<Particle> newList = new ArrayList<>();

        for (int i = 0; i < particleListPrime.size(); i++) {
            Particle p = pickParticle(particleListPrime);
            newList.add(p);
        }

        return newList;
    }

    private Particle pickParticle(List<Particle> particleListPrime) {
        Double pick = random.nextDouble();

        double accum = 0D;
        for (Particle p : particleListPrime) {
            accum += p.getWeight();
            if (accum >= pick) {
                return p;
            }
        }

        return particleListPrime.get(particleListPrime.size() - 1);
    }

    private void computeWeights(SensorData[] sensorData, double eta, List<Particle> particleListPrime) {
        Iterator<Particle> particleIterator = particleListPrime.iterator();
        while (particleIterator.hasNext()) {
            Particle p = particleIterator.next();

            double z = normalizeSensorData(sensorData, p);

            p.setWeight(((z / sensors.length) / eta));
        }
    }

    private double normalizeSensorData(SensorData[] sensorData, Particle p) {
        double z = 0.0D;
        for (SensorData d : sensorData) {
            for (ISensor s : sensors) {
                z += d.matchesWith(s.poll(p.getX(), p.getY()));
            }
        }
        return z;
    }


    private Particle createRandomParticle(double weight) {
        Particle p = new Particle(random.nextInt(WIDTH), random.nextInt(HEIGHT), weight);
        return p;
    }


    private double calculateEta(SensorData[] sensorData) {
        double eta = 0D;
        for (Particle p : particleList) {
            eta += normalizeSensorData(sensorData, p);
        }
        return eta / sensors.length;
    }
}
