package hu.bme.mit.spaceship;

public interface TorpedoStoreInterface {
	public boolean fire(int numberOfTorpedos);
	public boolean isEmpty();
	public int getTorpedoCount();
	
}
