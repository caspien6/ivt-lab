package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import edu.emory.mathcs.backport.java.util.Arrays;

@RunWith(Parameterized.class)
public class GT4500Test {

  private GT4500 ship;
  private TorpedoStoreInterface mockFirstDA;
  private TorpedoStoreInterface mockSecondDa;

  @Before
  public void init(){
	mockFirstDA = mock(TorpedoStore.class);
	mockSecondDa = mock(TorpedoStore.class);
    this.ship = new GT4500(mockFirstDA, mockSecondDa);
  }
  
@Parameters
  public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][] {     
               { true, true, false, true, FiringMode.SINGLE, false, false, true },
               { true, true, true, false, FiringMode.SINGLE, false, false, true },
               { false, false, false, true, FiringMode.SINGLE, false, false, false },
               { false, false, true, false, FiringMode.SINGLE, false, false, false },
               { false, false, false, false, FiringMode.SINGLE, true, false, false },
               { false, false, true, false, FiringMode.SINGLE, false, true, false }
         });
  }
  
  @Parameter // first data value (0) is default
  public boolean firePrimary;
  
  @Parameter(1)
  public boolean fireSecondary;
  
  @Parameter(2) // first data value (0) is default
  public boolean isemptyPrimary;
  
  @Parameter(3) // first data value (0) is default
  public boolean isemptySecondary;
  
  @Parameter(4) // first data value (0) is default
  public FiringMode firingMode;

  @Parameter(5)
  public  boolean throwPrimaryException;
  
  @Parameter(6)
  public  boolean throwSecondaryException;
  
  @Parameter(7)
  public  boolean Expected;
  
  @Test
  public void parametrizedTest() {
	// Arrange
	when(mockFirstDA.isEmpty()).thenReturn(isemptyPrimary);
	when(mockSecondDa.isEmpty()).thenReturn(isemptySecondary);
	
	if (!throwPrimaryException){
		when(mockFirstDA.fire(1)).thenReturn(firePrimary);
	}
	else{
		when(mockFirstDA.fire(1)).thenThrow(Exception.class);
	}
	
	if (!throwSecondaryException){
		when(mockSecondDa.fire(1)).thenReturn(fireSecondary);
	}
	else{
		when(mockSecondDa.fire(1)).thenThrow(Exception.class);
	}
	
	// Act
    boolean result = ship.fireTorpedo(firingMode);
    
    //Assert
    assertEquals(Expected, result);
	
	
  }
  
  

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
	when(mockFirstDA.fire(1)).thenReturn(true);
	when(mockSecondDa.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedo_PrimaryEmpty_SecondarySuccess(){
	  //Arrange
	  when(mockFirstDA.isEmpty()).thenReturn(true);
	  when(mockSecondDa.fire(1)).thenReturn(true);
	  
	  // Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	  // Assert
	  assertEquals(true, result);
  }
  @Test
  public void fireTorpedo_SecondaryEmpty_PrimarySuccess(){
	  //Arrange
	  when(mockFirstDA.fire(1)).thenReturn(true);
	  when(mockSecondDa.isEmpty()).thenReturn(true);
	  
	  // Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	  // Assert
	  assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedo_UnloadedWeapons_Failure(){
	  //Arrange
	  when(mockFirstDA.isEmpty()).thenReturn(true);
	  when(mockSecondDa.isEmpty()).thenReturn(true);
	  
	  // Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	  // Assert
	  assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_ThrowErrorPrimary_Failure(){
	  //Arrange
	  when(mockFirstDA.fire(1)).thenThrow(Exception.class);
	  when(mockSecondDa.isEmpty()).thenReturn(true);
	  
	  // Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	  // Assert
	  assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_ThrowErrorSecondary_Failure(){
	  //Arrange
	  when(mockFirstDA.isEmpty()).thenReturn(true);
	  when(mockSecondDa.fire(1)).thenThrow(Exception.class);
	  
	  // Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);

	  // Assert
	  assertEquals(false, result);
  }

  //White-box test
  @Test
  public void fireTorpedo_FireAllThenEmpty_Failure(){
	  //Arrange
	  when(mockFirstDA.fire(1)).thenReturn(true);
	  when(mockSecondDa.fire(1)).thenReturn(true);
	  
	  // Act
	  boolean result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  // Assert
	  assertEquals(true, result);
	  
	  //Arrange
	  when(mockFirstDA.fire(1)).thenReturn(true);
	  when(mockSecondDa.isEmpty()).thenReturn(true);
	  
	  //Act
	  result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  // Assert
	  assertEquals(true, result);
	  
	  //Arrange
	  when(mockFirstDA.isEmpty()).thenReturn(true);
	  when(mockSecondDa.isEmpty()).thenReturn(true);
	  
	  //Act
	  result = ship.fireTorpedo(FiringMode.SINGLE);
	  
	  // Assert
	  assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_fireLaser_Failure(){
	  //Act
	  boolean result = ship.fireLaser(FiringMode.SINGLE);
	  
	  // Assert
	  assertEquals(false, result);
  }
  
}
