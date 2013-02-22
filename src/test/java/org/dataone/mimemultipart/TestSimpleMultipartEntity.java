/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For 
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 * $Id$
 */

package org.dataone.mimemultipart;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;


public class TestSimpleMultipartEntity {
	private static Log log = LogFactory.getLog(TestSimpleMultipartEntity.class);
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
		smpe.cleanupTempFiles();
	}
	
	
	@Test
	public void testGetDescription() throws IOException
	{
		SimpleMultipartEntity smpe = new SimpleMultipartEntity();
		String content = "this is a very short test input stream.";
		InputStream is = IOUtils.toInputStream(content);
		smpe.addFilePart("isTestPart", is);
		File t = new File(smpe.getLastTempfile());
				
		smpe.addParamPart("ppppp", "12345");
		
		String description = smpe.getDescription();
		System.out.println(description);
		
		assertTrue( "description file length should equal content length",
				description.contains(String.valueOf(t.length())));
		assertTrue( "description should contain the param part name",
				description.contains("ppppp"));
		assertTrue( "description should contain the param part value",
				description.contains("12345"));
		
		
		smpe.cleanupTempFiles();
	}
	
	@Test
	public void testFileCleanup() throws ClientProtocolException, IOException 
	{
		SimpleMultipartEntity smpe = new SimpleMultipartEntity();
		String content = "this is a very short test input stream.";
		smpe.addFilePart("isTestPart", content);
		File t = new File(smpe.getLastTempfile());
		assertEquals("tempfile length is equal to input", content.length(),t.length());
		
		smpe.cleanupTempFiles();
		assertFalse("temp file should be cleanedup", t.exists());
	}
	
	
	private HttpResponse doPost(String url, SimpleMultipartEntity smpe) 
	throws ClientProtocolException, IOException 
	{
		HttpEntityEnclosingRequestBase req = new HttpPost(url);
		req.setEntity(smpe);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		return httpClient.execute(req);
	}
	
	@Test
	public void echoTestAddParamPart() throws ClientProtocolException, IOException 
	{	
		SimpleMultipartEntity smpe = new SimpleMultipartEntity();
		smpe.addParamPart("testOne", "bizbazbuzzzz");
		smpe.addParamPart("testTwo", "flip-flap-flop");
		
		HttpResponse res = doPost(echoAndParseServiceUrl,smpe);
		smpe.cleanupTempFiles();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		log.info("Echoed content:");
		log.info(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testTwo ] = flip-flap-flop"));
	}
	
	@Test
	public void echoTestAddFilePart_File() throws ClientProtocolException, IOException 
	{
		SimpleMultipartEntity smpe = new SimpleMultipartEntity();
		
		smpe.addParamPart("testOne", "bizbazbuzzzz");
		
		File outputFile = File.createTempFile("mmp.output.", null);
		FileWriter fw = new FileWriter(outputFile);
		fw.write("flip-flap-flop");
		fw.flush();
		fw.close();
		smpe.addFilePart("testTwo", outputFile);
		
		HttpResponse res = doPost(echoAndParseServiceUrl,smpe);
		smpe.cleanupTempFiles();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		log.info("Echoed content:");
		log.info(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.FILES=<MultiValueDict: {u'testTwo': [<InMemoryUploadedFile: mmp.output."));
	}
	
	@Test
	public void echoTestAddFilePart_Stream() throws ClientProtocolException, IOException 
	{
		SimpleMultipartEntity smpe = new SimpleMultipartEntity();
		smpe.addParamPart("testOne", "bizbazbuzzzz");

		InputStream is = IOUtils.toInputStream("flip-flap-flop");
		smpe.addFilePart("testTwo", is);
		
		HttpResponse res = doPost(echoAndParseServiceUrl,smpe);
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		log.info("Echoed content:");
		log.info(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.FILES=<MultiValueDict: {u'testTwo': [<InMemoryUploadedFile: mmp.output."));
	}
	
	@Test
	public void echoTestAddFilePart_String() throws ClientProtocolException, IOException 
	{
		SimpleMultipartEntity smpe = new SimpleMultipartEntity();
		smpe.addParamPart("testOne", "bizbazbuzzzz");
		
		smpe.addFilePart("testTwo","flip-flap-flop");
		
		HttpResponse res = doPost(echoAndParseServiceUrl,smpe);
		smpe.cleanupTempFiles();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		log.info("Echoed content:");
		log.info(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.FILES=<MultiValueDict: {u'testTwo': [<InMemoryUploadedFile: mmp.output."));
	}
	
	

}
