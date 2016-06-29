package httpSample;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.commons.lang3.StringEscapeUtils;





import org.omg.CORBA.portable.InputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;







import util.Json2Codec;

public class HttpSample {
	static CloseableHttpClient httpClient;

	public HttpSample() {
		super();
		init();
	}
	public void init(){
		httpClient = HttpClients.createDefault();		
	}
	public UploadResponse runUploadGet(String fileName)
	{
		UploadResponse upload = null;
		String page = new String("https://drfirst.brickftp.com/api/rest/v1/files/");
		page = page.concat(fileName);
		String authString = new String("79094c529c0bf4bd2279018800ef40064ab38934:x");
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		HttpGet httpGet = new HttpGet(page);
		httpGet.setHeader("Authorization", "Basic " + authStringEnc);
		System.out.println(httpGet.getRequestLine());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);

		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server .... \n");
		output = br.readLine();
			System.out.println(output);
			upload = Json2Codec.unmarshal(UploadResponse.class, output);
			String uploadEscape = new String(StringEscapeUtils.unescapeHtml4(upload.getDownload_uri()));
			upload.setUpload_uri(uploadEscape);
			
		response.close();
		
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upload;
	}
	public UploadResponse runUploadStart(String fileName)
	{
		UploadResponse upload = null;
		String page = null;
		page = new String("https://drfirst.brickftp.com/api/rest/v1/files/");
		page = page.concat(fileName);
		System.out.println(page);
		String authString = new String("79094c529c0bf4bd2279018800ef40064ab38934:x");
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		try
		{
			HttpPost httppost = new HttpPost(page);
		
			httppost.setHeader("Authorization", "Basic " + authStringEnc);
			StringEntity input = new StringEntity("{\"action\":\"put\"}");
			input.setContentType("application/json");
			httppost.setEntity(input);
			System.out.println(httppost.getRequestLine());
			CloseableHttpResponse response = httpClient.execute(httppost);
			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));
			
			String output;
			System.out.println("Output from Server .... \n");
			output = br.readLine();
				System.out.println(output);
				upload = Json2Codec.unmarshal(UploadResponse.class, output);
				String uploadEscape = new String(StringEscapeUtils.unescapeHtml4(upload.getUpload_uri()));
				upload.setUpload_uri(uploadEscape);
			response.close();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upload;
	}
	
	public void runUploadAWSStep(String url, String filePath)
	{	
		HttpPut httpPut = new HttpPut(url);
		try {
			CloseableHttpResponse response = null;
			File file = new File(filePath);
			
			FileEntity input = new FileEntity(file);
			httpPut.setEntity(input);
			response = httpClient.execute(httpPut);
			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			output = br.readLine();
				System.out.println(output);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void closeUpload(String refId, String fileName)
	{
		String page = new String("https://drfirst.brickftp.com/api/rest/v1/files/");
		page = page.concat(fileName);
		String authString = new String("79094c529c0bf4bd2279018800ef40064ab38934:x");
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		try
		{
			HttpPost httppost = new HttpPost(page);
			httppost.setHeader("Authorization", "Basic " + authStringEnc);
			StringEntity input = new StringEntity("{\"action\":\"end\", \"ref\":\""+ refId+ "\"}");
			input.setContentType("application/json");
			httppost.setEntity(input);
			System.out.println(httppost.getRequestLine());
			CloseableHttpResponse response = httpClient.execute(httppost);
			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			output = br.readLine();
				System.out.println(output);
			response.close();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void downloadFile(String downloadURL, String filePath)
	{
		HttpGet httpget = new HttpGet(downloadURL);
		try {
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if(entity != null)
			{
				long len = entity.getContentLength();
				BufferedInputStream is = new BufferedInputStream(entity.getContent());
				FileOutputStream fos = new FileOutputStream(new File(filePath));
				int inByte;
				while((inByte = is.read()) != -1)
				     fos.write(inByte);
				is.close();
				fos.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws MalformedURLException
	{
		UploadResponse upload = null;
		UploadResponse download = null;
		HttpSample sample = new HttpSample();
		String fileName = new String("testing.docx");
		String path = new String("C:\\users\\ncheng\\documents\\testingJava.docx");
		upload = sample.runUploadStart(fileName);
		
		sample.runUploadAWSStep(upload.getUpload_uri(), fileName);
		sample.closeUpload(upload.getRef(), fileName);
		download = sample.runUploadGet(fileName);
		sample.downloadFile(download.getDownload_uri(), path);
		
		System.out.println("Paste this link into browser: \n" + download.getDownload_uri());
	}

}
