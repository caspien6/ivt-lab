package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedo stores and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStoreInterface primaryTorpedoStore;
  private TorpedoStoreInterface secondaryTorpedoStore;


  public GT4500(TorpedoStoreInterface primarytorpedostoreinterface, TorpedoStoreInterface secondarytorpedostoreinterface) {
    this.primaryTorpedoStore = primarytorpedostoreinterface;
    this.secondaryTorpedoStore = secondarytorpedostoreinterface;
  }

  public boolean fireLaser(FiringMode firingMode) {
    // TODO not implemented yet
    return false;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty, the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedo(FiringMode firingMode) {

    boolean firingSuccess = false;
    try{
    	if (firingMode == FiringMode.SINGLE ) {
            if (! secondaryTorpedoStore.isEmpty()) {
              firingSuccess = secondaryTorpedoStore.fire(1);

            }
            else if (! primaryTorpedoStore.isEmpty()){
                firingSuccess = primaryTorpedoStore.fire(1);
                          }
       }
       else if (firingMode == FiringMode.ALL){
           firingSuccess = true;
   	// try to fire both of the torpedo stores
      }
    }
    catch (Exception e) {
		return false;
	}
    
            // if both of the stores are empty, nothing can be done, return
    return firingSuccess;

    }




}
