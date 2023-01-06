/*
 * Copyright 2019 Adobe
 * All Rights Reserved.
 *
 * NOTICE: Adobe permits you to use, modify, and distribute this file in
 * accordance with the terms of the Adobe license agreement accompanying
 * it. If you have received this file from a source other than Adobe,
 * then your use, modification, or distribution of it requires the prior
 * written permission of Adobe.
 */




import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
//import org.json.simple.JSONObject;  
import org.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


import org.json.*;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.time.LocalDate; // import the LocalDate class





public class DocGenAPI_RestAPI_HSBC_Harness  {

	// Standard Vars
	
    static String json = "{\n  \"mediaType\": \"application/vnd.openxmlformats-officedocument.wordprocessingml.document\"\n}";
	static String bearer_token = "Bearer eyJhbGciOiJSUzI1NiIsIng1dSI6Imltc19uYTEta2V5LWF0LTEuY2VyIiwia2lkIjoiaW1zX25hMS1rZXktYXQtMSIsIml0dCI6ImF0In0.eyJpZCI6IjE2NzI5MTczODc5MjRfYWM2NDE0MjQtY2I0NS00NWQ0LTk0OWYtNDEzYTk2NzMwMDA3X3VlMSIsInR5cGUiOiJhY2Nlc3NfdG9rZW4iLCJjbGllbnRfaWQiOiJjYjBkNWI0OGVjZDM0N2Y0YjNmY2IwMGMwNDgyNjA5NCIsInVzZXJfaWQiOiI4NzY3NkFCODYzOEUyRTNEMEE0OTVFMjNAdGVjaGFjY3QuYWRvYmUuY29tIiwiYXMiOiJpbXMtbmExIiwiYWFfaWQiOiI4NzY3NkFCODYzOEUyRTNEMEE0OTVFMjNAdGVjaGFjY3QuYWRvYmUuY29tIiwiY3RwIjowLCJmZyI6IlhDWU1JRTdHRlBFNUlYVUtNTVFWWkhZQUdNPT09PT09IiwibW9pIjoiYmI3ZGU0MzUiLCJleHBpcmVzX2luIjoiODY0MDAwMDAiLCJjcmVhdGVkX2F0IjoiMTY3MjkxNzM4NzkyNCIsInNjb3BlIjoib3BlbmlkLERDQVBJLEFkb2JlSUQsYWRkaXRpb25hbF9pbmZvLm9wdGlvbmFsQWdyZWVtZW50cyJ9.HcIM-b05P8uw0S4g9qmvhXtt0CPR8ZounFl8yST7W2eMQ48-Drk6gD4UGcj5sgaJ03GY_iZXpWJLS0ZmLbh--dRx2ss6pcho8lGN62Pu98p53bIx6C_xsIYZs9Xo1QYjUhN4uUyb51LzuIPrpdEl82IpJvq8_BVMhYc4Eht84_av6oljdFweBwlS24OTPURkZtjGAynkF_jirkEejxGoQ6VM5MHysEoaPpAWKfud9yEhVmM1AFkThB2_Aizd1HBJc5FzOEbNxyUg9QXBKByfpDehkR1ikFNVF0ZNF11dN-pFvrgp_xKTZypWzugn3swh2AD5GiCAQsiC41II1C7R7Q";
	static String x_api_key = "cb0d5b48ecd347f4b3fcb00c04826094";
	static String content_type = "application/json";
	
	// create the POST request for get pre-signed URL for upload
	
	public static String Get_Pre_signedURL_for_DocTemplate() throws IOException {
		
		RequestBody body1 = RequestBody.create(
		  	      MediaType.parse("application/json"), json);
			
         @SuppressWarnings("deprecation")
 		OkHttpClient client = new OkHttpClient();
 		
         Request request = new Request.Builder()
                 .url("https://pdf-services.adobe.io/assets")
                 .method("POST", body1)
                 .addHeader("x-api-key", x_api_key)
				 .addHeader("x-request-id", "123")
				 .addHeader("Authorization", bearer_token)
				 .addHeader("Content-Type", content_type)
				 .build();
         
         Response response = client.newCall(request).execute();
         System.out.println("response="+response.toString());
         String return_param="error";
        	if (response.isSuccessful())
        	{return_param = response.body().string();}
        	return return_param;
         
         }
	
	
	// create the POST request for DocGen
	
		public static String SendDocGen(String assetID) throws IOException, SQLException {
			
			MediaType mediaType = MediaType.parse("application/json");
			
			//MediaType mediaType = "application/json; charset=utf-8".toMediaType();
					
				
	         @SuppressWarnings("deprecation")
	 		OkHttpClient client4 = new OkHttpClient();
	 		
	
	         String message;
	         JSONObject jsonpayload = new JSONObject();
	         jsonpayload.put("assetID", assetID);
	         jsonpayload.put("outputFormat", "pdf");
	     
	         JSONObject jsoncustomer = new JSONObject();
	         
	         JSONObject customeraddresslines = new JSONObject();
	         customeraddresslines.put("addressLine1Text", "5 NKW BAIeg");
	         customeraddresslines.put("addressLine2Text", "dfimKdb dHzU");
	         customeraddresslines.put("addressLine3Text", "ftQtpeKYJ");
	         customeraddresslines.put("addressLine4Text", "kgcW");
	         customeraddresslines.put("country", "sn31 1rP");
	         customeraddresslines.put("zip", "GB");
	         
	         jsoncustomer.put("customer", customeraddresslines);
	         
	         jsonpayload.put("jsonDataForMerge",jsoncustomer);
	         System.out.println("JSON payload = "+jsonpayload);
	         
	         // send to DocGen Service 
	         //RequestBody body2 = RequestBody.create(mediaType, jsonpayload.toString());
	         RequestBody body2 = RequestBody.create(jsonpayload.toString(),mediaType);
	         Request request = new Request.Builder()
	                 .url("https://pdf-services.adobe.io/operation/documentgeneration")
	                 .method("POST", body2)
	                 .addHeader("x-api-key", x_api_key)
					 //.addHeader("x-request-id", "456")
					 .addHeader("Authorization", bearer_token)
					 .addHeader("Content-Type", content_type)
					 .build();

	         
	         System.out.println("req="+request.toString());
	         //System.out.println("bod="+request.body().toString());
	         Response response = client4.newCall(request).execute();
	         System.out.println("response="+response.headers().toString());
	         List<String> location = response.headers("location");
	         System.out.println("location size = "+location.size());
	         String URIlocation = location.get(0); 
	         System.out.println("location value = "+URIlocation);
	         
	         
	         String return_param="error";
	        //	if (response.isSuccessful())
	        //	{return_param = response.body().string();}
	        //	return return_param;
	         
	         String TM_insert = TableMgt("update",assetID,"",URIlocation,null);
	         

	         return "ok";
	         

	         
	         }
	
	// This function will upload the template file to the server for processing
	
		public static String uploadDocToStore_PUT(String uploadURI) throws IOException {
			
	        
	         @SuppressWarnings("deprecation")
	         
	         OkHttpClient client1 = new OkHttpClient().newBuilder().build();
	         String fileName = "/Users/richardcurtis/Downloads/DocGen JMeter Scenarios/DocGen_Prod_JMeter/input/Global_Contract_Note_Template_new.docx";
	         File uploadFile = new File(fileName);
	         
	         MediaType mediaType = MediaType.parse("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	         byte [] data =  FileUtils.readFileToByteArray(uploadFile);
	         RequestBody requestBody = RequestBody.create(mediaType,data);
	         
	         Request request = new Request.Builder()
	                 .url(uploadURI)
	                 .put(requestBody)
	                 .build();

	         Response execute = client1.newCall(request).execute();
	         System.out.println("PUT request="+execute);
	         
			return execute.toString();
	         }
		
	// This function will fetch a key from a JSON file.
		
	public static String parseJSONforToken(String elementToFind,String JSONstring) {
	
		JSONObject obj = new JSONObject(JSONstring);
		String elementValue = (String) obj.get(elementToFind);
		System.out.println ("found="+elementToFind+", found ="+elementValue);
		return elementValue;
	}

	// Write DB table
	public static String TableMgt(String feature,String TransactionID, String status, String location, Blob document) throws SQLException {

		// create a connection to MySQL via JDBC
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost:3306/RC_schema?" +
		                                   "user=root&password={PWD}");

		    // Do something with the Connection

		
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		stmt = conn.createStatement();
		// which feature
		if (feature.equals("truncate")) 
			{
				System.out.println("Truncating table");
				stmt.execute("TRUNCATE TABLE RC_schema.DocGen_SentForProcessing");
				
			}
		if (feature.equals("insert"))
			{
				System.out.println("Inserting into table");
				LocalDateTime myObj = LocalDateTime.now();
				System.out.println("INSERT INTO RC_schema.DocGen_SentForProcessing (TransactionID,SubmittedDateTime,State,Payload,location) VALUES ('"+TransactionID+"','"+myObj.toString()+"','Initalise',null,null)");
			 	stmt.execute("INSERT INTO RC_schema.DocGen_SentForProcessing (TransactionID,SubmittedDateTime,State,Payload,location) VALUES ('"+TransactionID+"','"+myObj.toString()+"','Initalise',null,null)");			
			}
        if (feature.equals("update"))
        	{
        		System.out.println("Updating table");
        		System.out.println("UPDATE RC_schema.DocGen_SentForProcessing SET state = 'Created', location = '"+location+"' WHERE TransactionID = '"+TransactionID+"'");
        		stmt.execute("UPDATE RC_schema.DocGen_SentForProcessing SET state = 'Created', location = '"+location+"' WHERE TransactionID = '"+TransactionID+"'");
	
			
        	}
        	//
		rs = stmt.getResultSet();
		return "ok";
		
	}
	// mainline
	
	public static void main(String[] args) throws Exception {
			String assetID=null;
			System.out.println("started");
			
			String TM_truncate = TableMgt("truncate","","","",null);
			// Get pre-signed URL for the doc template that we will upload in the next step
			
			String pre_signedURL_response =  Get_Pre_signedURL_for_DocTemplate();
			System.out.println("pre-signed URL ="+ pre_signedURL_response);
			
			// Now we have the Pre-signed URL, we can upload to the server for reference
			
			String uploadURI = parseJSONforToken("uploadUri",pre_signedURL_response);
			assetID = parseJSONforToken("assetID",pre_signedURL_response);
			System.out.println("assetID="+assetID);
			// Insert initial DB Table record, as a place holder to store the Doc Gen location for the poll requester
			String TM_insert = TableMgt("insert",assetID,"","",null);
			//Upload doc to the Pre-signed URL location
			String uploaddoc_resp = uploadDocToStore_PUT(uploadURI);
			System.out.println("upload status = "+uploaddoc_resp);
			
			//call DocGen for content creation (includes DB Updates as well)
			String docGenResult = SendDocGen(assetID);
			
			
			System.out.println("Finished");

     }
		          
		          
}	 

	
    

