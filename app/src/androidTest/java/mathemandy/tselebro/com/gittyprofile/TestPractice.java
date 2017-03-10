package mathemandy.tselebro.com.gittyprofile;

import android.test.AndroidTestCase;

/**
 * Created by mathemandy on 10 Mar 2017.
 */

public class TestPractice extends AndroidTestCase {
      /*This gets run before every test
    */

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testThatDemonstratesAssertions() throws Throwable {

        int a = 5;
        int b = 3;
        int c = 5;
        int d = 10;

        assertEquals("X should be equal", a, c);
        assertTrue("y should be true", d > a);
        assertFalse("Z should be false", a == b );

        if (b > d){
            fail("XX should never Happen ");
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
