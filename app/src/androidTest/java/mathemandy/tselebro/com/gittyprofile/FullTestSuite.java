package mathemandy.tselebro.com.gittyprofile;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;

/**
 * Created by mathemandy on 10 Mar 2017.
 */

public class FullTestSuite {
    public static Test suite(){

        return  new TestSuiteBuilder(FullTestSuite.class)
                .includeAllPackagesUnderHere().build();
    }

    public  FullTestSuite() {
        super();
    }
}
