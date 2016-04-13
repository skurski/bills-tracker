package com.skurski.gwt.demo.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.skurski.gwt.demo.shared.validator.FieldVerifier;


/**
 * GWT JUnit <b>integration</b> tests must extend GWTTestCase.
 * Using <code>"GwtTest*"</code> naming pattern exclude them from running with
 * surefire during the test phase.
 *
 * If you run the tests using the Maven command line, you will have to 
 * navigate with your browser to a specific url given by Maven. 
 * See http://mojo.codehaus.org/gwt-maven-plugin/user-guide/testing.html 
 * for details.
 */
public class ProjectGwtTest extends GWTTestCase {

    /**
     * Must refer to a valid module that sources this class.
     */
    public String getModuleName() {
        return "com.skurski.gwt.demo.project";
    }

    /**
     * Tests the FieldVerifier.
     */
    public void testFieldVerifier() {
        assertFalse(FieldVerifier.isValidName(null));
        assertFalse(FieldVerifier.isValidName(""));
        assertFalse(FieldVerifier.isValidName("a"));
        assertFalse(FieldVerifier.isValidName("ab"));
        assertFalse(FieldVerifier.isValidName("abc"));
        assertTrue(FieldVerifier.isValidName("abcd"));
    }

    public void testSimple() {
        assertTrue(true);
    }

}
