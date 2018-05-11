package hu.bme.mit.spaceship;

import java.util.Random;

/**
* Class storing and managing the torpedoes of a ship
*/
public class TorpedoStore implements TorpedoStoreInterface {

  private double failureRate = 0.0;

  private int torpedoCount = 0;
  private Random generator = new Random();

  public TorpedoStore(int numberOfTorpedos){
    this.torpedoCount = numberOfTorpedos;

    // update failure rate if it was specified in an environment variable
    String failureEnv = System.getenv("IVT_RATE");
    if (failureEnv != null){
      try {
        failureRate = Double.parseDouble(failureEnv);
      } catch (NumberFormatException nfe) {
        failureRate = 0.0;
      }
    }
  }

  @Override
  public boolean fire(int numberOfTorpedos){
    if(numberOfTorpedos < 1 || numberOfTorpedos > this.torpedoCount){
      throw new IllegalArgumentException("numberOfTorpedos");
    }

    boolean success = false;

    // simulate random overheating of the launcher bay which prevents firing
    double r = generator.nextDouble();

    if (r >= failureRate) {
      // successful firing
      this.torpedoCount -= numberOfTorpedos;
      success = true;
    } else {
      // simulated failure
      success = false;
    }

    return success;
  }

  @Override
  public boolean isEmpty(){
    return this.torpedoCount <= 0;
  }

  @Override
  public int getTorpedoCount() {
    return this.torpedoCount;
  }
}
