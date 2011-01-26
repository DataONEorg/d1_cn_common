package org.dataone.service.types;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;


public class ObjectFormatTest {

	// convert is kind of slow, because it doesn't hash the string values
	// going to see how it behaves if I add indexing
	@Test
	public void testConvert()   {
		String unknownFormatString = "foo";
		String knownFormatString = "application/octet-stream";

		ObjectFormat f1 = null;
		ObjectFormat f2 = null;
		
		StopWatch w = new StopWatch();
		
		w.start();
		for (int i = 0; i < 1000000; i++) {
			f1 = ObjectFormat.convert(knownFormatString);
			f2 = ObjectFormat.convert(knownFormatString);
			
			if (f1.toString().trim().equals(f2.toString().trim())) {
			}
		}
		w.stop();		
		long t = w.getTime();
		System.out.println("time for 2 converts plus string comp: " + t);


		w.reset();

		w.start();
		f1 = ObjectFormat.convert(knownFormatString);
		f2 = ObjectFormat.convert(knownFormatString);

		for (int i = 0; i < 1000000; i++) {
			f1 = ObjectFormat.convert(knownFormatString);
			f2 = ObjectFormat.convert(knownFormatString);
			if (f1.equals(f2)) {
			}
		}			
		w.stop();		

		t = w.getTime();
		System.out.println("time for 2 converts plus object comp: " + t);

	}


}
