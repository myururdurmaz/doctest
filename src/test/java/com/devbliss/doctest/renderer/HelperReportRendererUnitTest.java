package com.devbliss.doctest.renderer;

import static org.junit.Assert.assertEquals;
import static testutils.Utils.OUTPUT_DIRECTORY;
import static testutils.Utils.cleanUpTheTargetDirectory;
import static testutils.Utils.getFilesInOutputDirectory;
import static testutils.Utils.verifyTheFileHasBeenCreated;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.devbliss.doctest.utils.InvalidReportException;

/**
 * Unit test for {@link HelperReportRenderer}
 * 
 * @author bmary
 * 
 */
public class HelperReportRendererUnitTest {

    private static final String FILE = "test.txt";
    private static final String EXTENSION = ".extension";
    private static final String NAME = "name";
    private static final String DOC = "finaleDoc";
    private HelperReportRenderer renderer;

    @Before
    public void setUp() {
        renderer = new HelperReportRenderer();
        cleanUpTheTargetDirectory();
    }

    @After
    public void tearDown() {
        cleanUpTheTargetDirectory();
    }

    @Test
    public void getCompleteFileName() {
        String result = renderer.getCompleteFileName(NAME, EXTENSION);
        assertEquals(OUTPUT_DIRECTORY + NAME + EXTENSION, result);
    }

    @Test
    public void writeFile() throws Exception {
        renderer.writeFile(OUTPUT_DIRECTORY + FILE, DOC);
        verifyTheFileHasBeenCreated(FILE);
    }

    @Test
    public void writeFileContentIsNull() throws Exception {
        renderer.writeFile(OUTPUT_DIRECTORY + FILE, null);
        assertEquals(0, getFilesInOutputDirectory().length);
    }

    @Test(expected = InvalidReportException.class)
    public void writeFileNameIsEmpty() throws Exception {
        renderer.writeFile("", DOC);
        assertEquals(0, getFilesInOutputDirectory().length);
    }

    @Test(expected = InvalidReportException.class)
    public void writeFileNameIsNull() throws Exception {
        renderer.writeFile(null, DOC);
        assertEquals(0, getFilesInOutputDirectory().length);
    }

    @Test(expected = InvalidReportException.class)
    public void writeFileNameIsInvalid() throws Exception {
        renderer.writeFile("blabla", DOC);
        assertEquals(0, getFilesInOutputDirectory().length);
    }
}