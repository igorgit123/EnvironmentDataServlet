package pack;

import javax.servlet.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@WebServlet(
        name = "Info servlet",
        description = "a small test servlet",
        urlPatterns = {"/"}
)
public class EnvironmentServiceServlet extends HttpServlet {

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {




        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<HTML>");
        out.println("<HEAD><TITLE>Environment Data Servlet</TITLE></HEAD>");
        out.println("<BODY>");


        //C++ server data



        try{


            IEnvService service = new CppserverData();
            String[] sensors = service.requestEnvironmentDataTypes();

            out.println("<h1>C++ Server Environment Data</h1>");
            out.println("<table border='1'>  <tr> <th>Timestamp</th> <th>Sensor</th> <th>Value</th> </tr>");


            for (int i = 0; i < sensors.length; i++) {


                out.println("<tr>");

                out.println("<td>");
                out.println(service.requestEnvironmentData(sensors[i]).getTimestamp());
                out.println("</td>");
                out.println("<td>");
                out.println(service.requestEnvironmentData(sensors[i]).getSensorName());
                out.println("</td>");
                out.println("<td>");

                for(int j=0; j<service.requestEnvironmentData(sensors[i]).getSensorData().length; j++){
                    out.println(service.requestEnvironmentData(sensors[i]).getSensorData()[j]+"; ");
                }

                out.println("</td>");

                out.println("</tr>");

                service.requestAll();
            }
            out.println("</table>");
        }catch (Exception e){
            out.println(e.toString());
            out.println("C++ server unavailable");

        }

        //RMI server data

     /*   try {


            RMIEnvService rmIinterface = (RMIEnvService) Naming.lookup("//localhost:4444/RMIserver");


            if(RMIsensors==null){
                out.println("RMI server offline");
            }else {
          */


        try {
            String adress = "RMIserver";
            RMIEnvService rmIinterface = (RMIEnvService) Naming.lookup("//localhost:4444/pack.RMIserver");
            String[] sensors = rmIinterface.requestEnvironmentDataTypes();







            out.println("<h1>RMI Server Environment Data</h1>");
            out.println("<table border='1'>  <tr> <th>Timestamp</th> <th>Sensor</th> <th>Value</th> </tr>");

            for (String data : sensors) {


                out.println("<tr>");

                out.println("<td>");
                out.println(rmIinterface.requestEnvironmentData(data).getTimestamp());
                out.println("</td>");
                out.println("<td>");
                out.println(rmIinterface.requestEnvironmentData(data).getSensorName());
                out.println("</td>");
                out.println("<td>");
                for (int i : rmIinterface.requestEnvironmentData(data).getSensorData()) {
                    out.println(i + "; ");
                }
                out.println("</td>");

                out.println("</tr>");

                rmIinterface.requestAll();


            }
        } catch (Exception e) {
            out.println("<h1>RMI server offline.</h1>");
            out.println(e.toString());
        }


  /*  } catch(
    Exception e)

    {
        out.println(e.getMessage());
    }
*/

        out.println("</HTML>");
    }

    public void destroy() {
    }
}