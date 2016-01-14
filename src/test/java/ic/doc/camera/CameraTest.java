package ic.doc.camera;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class CameraTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
    	//set up
    	final Sensor sensor = context.mock(Sensor.class);
    	Camera camera = new Camera(sensor, null);
    	//expectations
    	context.checking(new Expectations(){{
    		 exactly(1).of(sensor).powerUp();
    	}});
    	//test code
    	camera.powerOn();
    	//verify camera state
    	assertTrue(1 == camera.getState());
    }
    @Test
    public void switchingTheCameraOffPowersDownTheSensor(){
    	//set up
    	final Sensor sensor = context.mock(Sensor.class);
    	Camera camera = new Camera(sensor, null);
    	//expectations
    	context.checking(new Expectations(){{
    		 exactly(1).of(sensor).powerDown();
    	}});
    	//test code
    	camera.powerOff();
    	//verify camera state
    	assertTrue(0 == camera.getState());
    }
    @Test
    public void pressingShutterWithPowerOffDoesNothing(){
    	//set up
    	final Sensor sensor = context.mock(Sensor.class);
    	final MemoryCard memoryCard = context.mock(MemoryCard.class);
    	Camera camera = new Camera(sensor,memoryCard);
    	//expectations
    	context.checking(new Expectations(){{
    		 never(sensor).readData();
    		 never(sensor).powerUp();
    		 never(sensor).powerDown();
    		 never(memoryCard).write(with(any(byte[].class)));
    	}});
    	//verify camera state
    	assertTrue(0 == camera.getState());
       	//test code
    	camera.pressShutter();
    	//verify camera state
    	assertTrue(0 == camera.getState());
    }
    @Test
    public void pressingShutterWithPowerOnCopyDataFromSensorToMemoryInSeconds(){
    	//set up
    	final Sensor sensor = context.mock(Sensor.class);
    	final MemoryCard memoryCard = context.mock(MemoryCard.class);
    	Camera camera = new Camera(sensor,memoryCard);
    	final Sequence sequence = context.sequence("sequence");
    	//expectations
    	byte[] data;
    	context.checking(new Expectations(){{
    		exactly(1).of(sensor).readData();inSequence(sequence);will(returnValue(data));
    		exactly(1).of(memoryCard).write(data);inSequence(sequence);
    	}});
    	//test code
    	camera.powerOn();
    	camera.pressShutter();
    }

}
