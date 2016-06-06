package dtotest;

import dto.Drug;
import util.Json2Codec;

/**
 * @author ztang
 * generated at : Mar 10, 2015 - 10:38:34 AM
 */
public class DrugCodeTest {

	public DrugCodeTest() {
		super();
	}
	
	public void codec_Drug() throws Exception{
		Drug dto = new Drug();
		dto.setNdcId("1234567");
		dto.setStrength(1000);
		
		String payload = Json2Codec.marshal(dto);		
		System.out.println(payload);		
	}
	
	public static void main(String[] args) throws Exception {		
		DrugCodeTest agent = new DrugCodeTest();

		agent.codec_Drug();
		
		System.exit(0);
	}

}
