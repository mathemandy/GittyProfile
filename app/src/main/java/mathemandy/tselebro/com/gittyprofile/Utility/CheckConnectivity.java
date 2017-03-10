package mathemandy.tselebro.com.gittyprofile.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

/**
 * Helper Class to Check the Network for Network Connectivity
 */
public class CheckConnectivity {
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;

    public Boolean isNetworkConnected(Context con) {

        try {
            connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            activeNetwork = connectivityManager.getActiveNetworkInfo();

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == connectivityManager.TYPE_MOBILE) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());

        }
        return false;
    }

    public boolean isINternetAvailable (String url){
        try {
            InetAddress ipAdrr = InetAddress.getByName(url);
            if (ipAdrr.equals(" ")){
                return false;
            }else{

                return true;
            }
        }catch (Exception e){
            return  false;
        }
    }
}