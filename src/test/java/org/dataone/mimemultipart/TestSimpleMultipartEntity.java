package org.dataone.mimemultipart;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.dataone.service.Constants;
import org.junit.Test;


public class TestSimpleMultipartEntity {

	private static final String echoServiceUrl = "http://dev-testing.dataone.org/testsvc/echo";
	private static final String echoAndParseServiceUrl = "http://dev-testing.dataone.org/testsvc/echomm";
	
	@Test
	public void testTempFileCreation_InputStream() throws IOException
	{
		SimpleMultipartEntity smpe = new SimpleMultipartEntity();
		String content = "this is a very short test input stream.";
		InputStream is = IOUtils.toInputStream(content);
		smpe.addFilePart("isTestPart", is);
		File t = new File(smpe.getLastTempfile());
		assertEquals("tempfile length is equal to input", content.length(),t.length());
	}
	
	@Test
	public void echoTestAddParamPart() throws ClientProtocolException, IOException 
	{
		MultipartRequestHandler mprh = new MultipartRequestHandler(echoAndParseServiceUrl,Constants.POST);
		
		mprh.addParamPart("testOne", "bizbazbuzzzz");
		mprh.addParamPart("testTwo", "flip-flap-flop");
		
		HttpResponse res = mprh.executeRequest();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		System.out.println("Echoed content:");
		System.out.println(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testTwo ] = flip-flap-flop"));
	}
	
	@Test
	public void echoTestAddFilePart_File() throws ClientProtocolException, IOException 
	{
		MultipartRequestHandler mprh = new MultipartRequestHandler(echoAndParseServiceUrl,Constants.POST);
		mprh.addParamPart("testOne", "bizbazbuzzzz");
		
		Date d = new Date();
		File tmpDir = new File(Constants.TEMP_DIR);
		File outputFile = new File(tmpDir, "mmp.output." + d.getTime());
		FileWriter fw = new FileWriter(outputFile);
		fw.write("flip-flap-flop");
		fw.flush();
		fw.close();
		
		mprh.addFilePart("testTwo", outputFile);
		
		HttpResponse res = mprh.executeRequest();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		System.out.println("Echoed content:");
		System.out.println(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.FILES=<MultiValueDict: {u'testTwo': [<InMemoryUploadedFile: mmp.output."));
	}
	
	@Test
	public void echoTestAddFilePart_Stream() throws ClientProtocolException, IOException 
	{
		MultipartRequestHandler mprh = new MultipartRequestHandler(echoAndParseServiceUrl,Constants.POST);
		mprh.addParamPart("testOne", "bizbazbuzzzz");

		InputStream is = IOUtils.toInputStream("flip-flap-flop");
		mprh.addFilePart("testTwo", is);
		
		HttpResponse res = mprh.executeRequest();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		System.out.println("Echoed content:");
		System.out.println(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.FILES=<MultiValueDict: {u'testTwo': [<InMemoryUploadedFile: mmp.output."));
	}
	
	@Test
	public void echoTestAddFilePart_String() throws ClientProtocolException, IOException 
	{
		MultipartRequestHandler mprh = new MultipartRequestHandler(echoAndParseServiceUrl,Constants.POST);
		mprh.addParamPart("testOne", "bizbazbuzzzz");
		
		mprh.addFilePart("testTwo","flip-flap-flop");
		
		HttpResponse res = mprh.executeRequest();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		System.out.println("Echoed content:");
		System.out.println(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.FILES=<MultiValueDict: {u'testTwo': [<InMemoryUploadedFile: mmp.output."));
	}
}
