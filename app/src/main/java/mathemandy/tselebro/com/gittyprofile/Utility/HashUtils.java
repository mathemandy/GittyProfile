package mathemandy.tselebro.com.gittyprofile.Utility;

import java.util.Locale;

/**
 * Created by mathemandy on 09 Mar 2017.
 */
public class HashUtils {
    public  static  String computeWeakHash (String string){
        return String.format(Locale.US, "%08x%08x", string.hashCode(), string.length());
    }

}
