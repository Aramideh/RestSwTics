package ir.gisbox;

import com.gesmallworld.core.tics.TicsConnection;


public class SWServices {

    private static final String serverName = "localhost";
    private static final String port = "3040";

    private static final String JsonErrorMessage = "{Result:false,ResultCode:0,ResultMessage:Error in Java Method}" ;

    public SWServices() {
    }

    protected static int getMaxProtocol() {
        return 1;
    }

    protected static int getMinProtocol() {
        return 1;
    }

    public String HelloWorld(String jsonRequest ) throws Exception {
        String result = JsonErrorMessage;

        try {
            result = sendToSmallworld( jsonRequest, this.serverName , this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }




    public static String sendToSmallworld(String jsonRequest, String host , String port) throws Exception {

        String result = JsonErrorMessage;
        try {
            System.out.println("Stablishing pool on " + host + " : " + port);
            TicsConnection connection = new TicsConnection();
            connection.connect(host, port);
            System.out.println("Stablished...");
            connection.establishProtocol(getMinProtocol(), getMaxProtocol());
            System.out.println("Puting " + jsonRequest);
            connection.putString16(jsonRequest);
            result = connection.getString16();
            System.out.println("Returned :" + result);
        } catch (Exception var18) {
            System.out.println("Tics Connection Faild!!! ");
            var18.printStackTrace();
        }

        return result;
    }



}
