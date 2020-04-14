package pack;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class CppserverData implements IEnvService {

    Socket sock;
    InputStream in;
    PrintWriter out;

    EnvData sensor;
    ArrayList<EnvData> sensorArrayList = new ArrayList<EnvData>();

/*



    public void startSocket() throws IOException {



    }

*/



    @Override
    public  String[] requestEnvironmentDataTypes() {
        try {
            sock = new Socket("127.0.0.1", 4977);
            in = sock.getInputStream();
            out = new PrintWriter(sock.getOutputStream(), true);
        }catch(IOException e){
            System.out.println("problem establishing connection");
            return null;
        }

        out.println("getSensortypes()");
        String [] p=new String[3];
        int data=-1;
        StringBuffer line = new StringBuffer();



        try {
            while (((data = in.read()) != -1)) {

                line.append((char)data);

                if (((char) data) == '#') {
                    break;
                }


            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println();
        }

        System.out.println(line);
        String temp = line.toString().substring(0, line.length()-1);

        StringTokenizer st= new StringTokenizer(temp, ";");
        int i=0;
        while(st.hasMoreElements()){
            p[i]=st.nextToken();
            i++;
        }

        for(int t=0; t<p.length; t++){
            System.out.println(p[t]);
        }


        return p;
    }







    @Override
    public EnvData requestEnvironmentData(String _type) {
        int [] sensorData = new int[3];

        out.println("Sensor()");

        int data=-1;
        StringBuffer line = new StringBuffer();
        try {
            while (((data = in.read()) != -1)) {

                line.append((char)data);

                if (((char) data) == '#') {
                    break;
                }


            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println();
        }

        //  System.out.println(line);
        String temp;
        if(line.charAt(1)=='c'|| line.charAt(0)==' '){
            temp=line.toString().substring(2, line.length()-1);
        }else{
            temp=line.toString().substring(0, line.length()-1);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        StringTokenizer st= new StringTokenizer(temp.toString(), ";");
        int i=0;
        while(st.hasMoreElements()){
            sensorData[i]=Integer.parseInt(st.nextToken());
            //   System.out.println(st.nextToken());
            i++;
        }



        sensor = new EnvData( _type, timestamp, sensorData);
        sensorArrayList.add(sensor);


        return sensor;
    }

    @Override
    public EnvData[] requestAll() {

        EnvData [] sensorArray = new EnvData[3];

        for(int i=0; i<sensorArray.length; i++){

            sensorArray[i] = sensorArrayList.get(i);

        }

        return sensorArray;


    }
}
