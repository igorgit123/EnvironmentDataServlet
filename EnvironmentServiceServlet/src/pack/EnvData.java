package pack;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

public class EnvData implements Serializable {
    private String sensorName;
    private Timestamp timestamp;
    private int [] sensorData;

    public EnvData() {}

    public EnvData(String sensorName, Timestamp timestamp, int[] sensorData) {
        this.sensorName = sensorName;
        this.timestamp = timestamp;
        this.sensorData = sensorData;
    }

    public String getSensorName() {


        return sensorName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int[] getSensorData() {
        return sensorData;
    }


    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setSensorData(int[] sensorData) {
        this.sensorData = sensorData;
    }

    public String toString() {
        return "Sensor Value (" + sensorName+ ";"+ sensorData[0] +")";


    }
}
