package com.devbliss.doctest;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.devbliss.doctest.templates.Templates;

import de.devbliss.apitester.ApiResponse;
import de.devbliss.apitester.ApiTest;
import de.devbliss.apitester.ApiTest.HTTP_REQUEST;

/**
 * Unit tests for the {@link DocTest}.
 *
 * @author bmary
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LogicDocTestUnitTest {

    private static final String NULL = "NULL";
    private static final String OBJECT = "OBJECT";
    private static final String OBJECT2 = "OBJECT2";
    private static final String RESPONSE_PAYLOAD = "payload";
    private static final int HTTP_STATUS = 204;
    @Mock
    private ApiTest apiTest;
    @Mock
    private DocTestMachine docTestMachine;
    @Mock
    private JSONHelper jsonHelper;
    @Mock
    private Object obj;
    @Mock
    private Templates templates;

    private LogicDocTest docTest;
    private URI uri;
    private ApiResponse response;

    protected class JsonTestClass {
        private String test1;
        private String test2;

        public JsonTestClass(String test1, String test2) {
            this.test1 = test1;
            this.test2 = test2;
        }
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uri = new URI("");
        response = new ApiResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
        when(jsonHelper.toJson(null)).thenReturn(NULL);
        when(jsonHelper.toJson(obj)).thenReturn(OBJECT);
        docTest = instantiateAbstractDocTest();
    }

    @Test
    public void say() {
        docTest.say("abc");
        verify(docTestMachine).say("abc");
    }

    @Test
    public void makeGetRequest() throws Exception {
        when(apiTest.get(uri)).thenReturn(response);
        docTest.makeGetRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.GET);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makeDeleteRequest() throws Exception {
        when(apiTest.delete(uri, null)).thenReturn(response);
        docTest.makeDeleteRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.DELETE);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makeDeleteRequestWithBody() throws Exception {
        when(apiTest.delete(uri, obj)).thenReturn(response);
        docTest.makeDeleteRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.DELETE);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePostRequest() throws Exception {
        when(apiTest.post(uri, null)).thenReturn(response);
        docTest.makePostRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.POST);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePostRequestWithBody() throws Exception {
        when(apiTest.post(uri, obj)).thenReturn(response);
        docTest.makePostRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.POST);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePutRequest() throws Exception {
        when(apiTest.put(uri, null)).thenReturn(response);
        docTest.makePutRequest(uri);
        verify(docTestMachine).sayRequest(uri, NULL, HTTP_REQUEST.PUT);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void makePutRequestWithBody() throws Exception {
        when(apiTest.put(uri, obj)).thenReturn(response);
        docTest.makePutRequest(uri, obj);
        verify(docTestMachine).sayRequest(uri, OBJECT, HTTP_REQUEST.PUT);
        verify(docTestMachine).sayResponse(HTTP_STATUS, RESPONSE_PAYLOAD);
    }

    @Test
    public void assertTrue() throws Exception {
        docTest.assertTrueAndSay(true);
        verify(docTestMachine).sayVerify("true");
    }

    @Test
    public void assertTrueIsFalse() throws Exception {
        try {
            docTest.assertTrueAndSay(false);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("true");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertFalse() throws Exception {
        docTest.assertFalseAndSay(false);
        verify(docTestMachine).sayVerify("false");
    }

    @Test
    public void assertFalseIsFalse() throws Exception {
        try {
            docTest.assertFalseAndSay(true);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("false");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertEqualsString() throws Exception {
        docTest.assertEqualsAndSay("expected", "expected");
        verify(docTestMachine).sayVerify("expected");
    }

    @Test
    public void assertStringAreNotEquals() throws Exception {
        try {
            docTest.assertEqualsAndSay("expected", "result");
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("expected");
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void assertEqualsInt() throws Exception {
        docTest.assertEqualsAndSay(100, 100);
        verify(docTestMachine).sayVerify("100");
    }

    @Test
    public void assertIntAreNotEquals() throws Exception {
        try {
            docTest.assertEqualsAndSay(100, 105);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify("100");
        } catch (Exception e) {
            fail();
        }
    }

    private LogicDocTest instantiateAbstractDocTest() {
        return new LogicDocTest(docTestMachine, apiTest, jsonHelper, templates);
    }

    @Test
    public void assertEqualsJsonObjects() throws Exception {
        JsonTestClass class1 = new JsonTestClass("content1", "content2");
        JsonTestClass class2 = new JsonTestClass("content1", "content2");
        when(jsonHelper.toJson(class1)).thenReturn(OBJECT);
        when(jsonHelper.toJson(class2)).thenReturn(OBJECT);

        docTest.assertJsonEqualsAndSay(class1, class2);
        verify(docTestMachine).sayVerify(anyString());
    }

    @Test
    public void assertStringAreNotEqualsJsonObjects() throws Exception {
        JsonTestClass class1 = new JsonTestClass("content1", "content2");
        JsonTestClass class2 = new JsonTestClass("sthcompletely", "different");
        when(jsonHelper.toJson(class1)).thenReturn(OBJECT);
        when(jsonHelper.toJson(class2)).thenReturn(OBJECT2);

        try {
            docTest.assertJsonEqualsAndSay(class1, class2);
            fail();
        } catch (AssertionError e) {
            verify(docTestMachine, never()).sayVerify(anyString());
        } catch (Exception e) {
            fail();
        }
    }

}
