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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.dataone.service.util.Constants;
import org.junit.Test;


public class testMultipartTransmission {
	private static Log log = LogFactory.getLog(testMultipartTransmission.class);
	private static final String echoServiceUrl = "http://dev-testing.dataone.org/testsvc/echo";
	private static final String echoAndParseServiceUrl = "http://dev-testing.dataone.org/testsvc/echomm";
	
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
		log.info("Echoed content:");
		log.info(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testTwo ] = flip-flap-flop"));
	}
	
	@Test
	public void echoTestAddFilePart_File() throws ClientProtocolException, IOException 
	{
		MultipartRequestHandler mprh = new MultipartRequestHandler(echoAndParseServiceUrl,Constants.POST);
		mprh.addParamPart("testOne", "bizbazbuzzzz");
		
		File outputFile = File.createTempFile("mmp.output.", null);
		FileWriter fw = new FileWriter(outputFile);
		fw.write("flip-flap-flop");
		fw.flush();
		fw.close();
		
		mprh.addFilePart("testTwo",outputFile);
		
		HttpResponse res = mprh.executeRequest();
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
		MultipartRequestHandler mprh = new MultipartRequestHandler(echoAndParseServiceUrl,Constants.POST);
		mprh.addParamPart("testOne", "bizbazbuzzzz");

		InputStream is = IOUtils.toInputStream("flip-flap-flop");
		mprh.addFilePart("testTwo",is);
		
		HttpResponse res = mprh.executeRequest();
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
		MultipartRequestHandler mprh = new MultipartRequestHandler(echoAndParseServiceUrl,Constants.POST);
		mprh.addParamPart("testOne", "bizbazbuzzzz");
		
		mprh.addFilePart("testTwo","flip-flap-flop");
		
		HttpResponse res = mprh.executeRequest();
		int code = res.getStatusLine().getStatusCode();
		InputStream content = res.getEntity().getContent();
		String echoed = IOUtils.toString(content);
		log.info("Echoed content:");
		log.info(echoed);
		assertTrue("message parsed",echoed.contains("request.REQUEST[ testOne ] = bizbazbuzzzz"));
		assertTrue("message parsed",echoed.contains("request.FILES=<MultiValueDict: {u'testTwo': [<InMemoryUploadedFile: mmp.output."));
	}
}
