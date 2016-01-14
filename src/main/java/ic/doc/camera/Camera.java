package ic.doc.camera;

public class Camera {
	
	private int state;
	private Sensor sensor;
	private MemoryCard memoryCard;
	private byte[] data;

	public Camera(Sensor s, MemoryCard mc){
		state = 0;
		sensor = s;
		memoryCard = mc;
		
	}
    public void pressShutter() {
    	if(this.state == 1){
    		data = this.sensor.readData();
    		this.memoryCard.write(data);
    	}else return;
    }

    public void powerOn() {
        // not implemented
    	state = 1;
    	sensor.powerUp();
    }

    public void powerOff() {
    	state = 0;
    	sensor.powerDown();
    }
    public int getState(){
    	return this.state;
    }
    public byte[] getData(){
    	return this.data;
    }
}

