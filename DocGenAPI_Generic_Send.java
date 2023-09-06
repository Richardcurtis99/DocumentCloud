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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.exception.SdkException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.DocumentMergeOperation;
import com.adobe.pdfservices.operation.pdfops.ExportPDFOperation;
import com.adobe.pdfservices.operation.pdfops.ExtractPDFOperation;
import com.adobe.pdfservices.operation.pdfops.PDFPropertiesOperation;
import com.adobe.pdfservices.operation.pdfops.options.exportpdf.ExportPDFTargetFormat;
import com.adobe.pdfservices.operation.pdfops.options.extractpdf.ExtractElementType;
import com.adobe.pdfservices.operation.pdfops.options.extractpdf.ExtractPDFOptions;
import com.adobe.pdfservices.operation.pdfops.options.pdfproperties.*;
import com.adobe.pdfservices.operation.pdfops.options.documentmerge.DocumentMergeOptions;
import com.adobe.pdfservices.operation.pdfops.options.documentmerge.OutputFormat;
import com.adobe.pdfservices.operation.pdfops.DocumentMergeOperation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
//import org.json.simple.JSONObject;  
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;




/**
 * This sample illustrates how to export a PDF file to a Word (DOCX) file
 * <p>
 * Refer to README.md for instructions on how to run the samples.
 */
public class DocGenAPI_Generic_Send  {
//public class DocGenAPI_Generic implements Runnable  {

    // Initialize the logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(DocGenAPI_Generic_Send.class);

    private static final int crunchifyThreads = 30;
    
 
    public static void main(String[] args) throws Exception, ServiceUsageException, IOException, ServiceApiException {
    	System.out.println("hello world");
    	
    	ExecutorService executor = Executors.newFixedThreadPool(crunchifyThreads);
    	// object creation
    	  for (int i = 0; i < 450; i++) {
              
             // String url = crunchifyList[i];
              Runnable worker = new MyRunnable();
              
              	// execute(): Executes the given command at some time in the future. The command may execute in a new thread, in a pooled thread,
              	// or in the calling thread, at the discretion of the Executor implementation.
              	executor.execute(worker);
    	  	  }
              executor.shutdown();
              
              // Wait until all threads are finish
              // Returns true if all tasks have completed following shut down.
              // Note that isTerminated is never true unless either shutdown or shutdownNow was called first.
              while (!executor.isTerminated()) {
                  // empty body
              }
              System.out.println("\nFinished all threads");
    }
    
    
    	
    	
    	//t1.run();
    	
    	
        
        //Main t1 = new runner();
        //t1.start;
        	
    	//runner();
    


    
    public static class MyRunnable implements Runnable {

        
        	
        
        	@Override
            public void run() {
        		
        	
        	System.out.println("thread: ");
        	LocalDateTime myObj = LocalDateTime.now(); // Create a date object
        	System.out.print(", start date/time - "+myObj);
            //System.out.println(myObj); // Display the current date
        	//System.out.println("start date/time - ")
        	  // Initial setup, create credentials instance.
        	

            // Initial setup, create credentials instance.
        	Credentials credentials=null;
        	InputStream is=null;
        	String jsonTxt="";
			try {
				credentials = Credentials.serviceAccountCredentialsBuilder()
				        .fromFile("/Users/richardcurtis/Downloads/RC_Java+Workspace/DocCloudSDK_quickstart/pdftools-api-credentials3.json")
				        .build();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	// Setup input data for the document merge process.
        	
				try {
					is = new FileInputStream("/Users/richardcurtis/Downloads/payload_Input/SampleData-new.json");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	
			try {
				jsonTxt = IOUtils.toString(is, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //System.out.println(jsonTxt);
            
            JSONObject json = new JSONObject(jsonTxt);    
            
            //JSONObject json = new JSONObject(jsonTxt);
            //JSONObject jsonDataForMerge = new JSONObject(json);
            
 
            // Create an ExecutionContext using credentials.
            ExecutionContext executionContext = ExecutionContext.create(credentials);
 
                       int loopcounter=1;
            for (int i=0;i<loopcounter;i++) {
 
            	// Create a new DocumentMergeOptions instance.
                DocumentMergeOptions documentMergeOptions = new DocumentMergeOptions(json, OutputFormat.PDF);
     
                // Create a new DocumentMergeOperation instance with the DocumentMergeOptions instance.
                
                DocumentMergeOperation documentMergeOperation = DocumentMergeOperation.createNew(documentMergeOptions);
     
            // Set the operation input document template from a source file.
            FileRef documentTemplate = FileRef.createFromLocalFile("/Users/richardcurtis/Downloads/Folder_Input/Template - new.docx");
            documentMergeOperation.setInput(documentTemplate);
 
            // Execute the operation.
            FileRef result=null;
			try {
				result = documentMergeOperation.execute(executionContext);
			} catch (ServiceUsageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
            // Save the result to the specified location.
            
            try {
				result.saveAs("/Users/richardcurtis/Downloads/Folder_Input/documentMergeOutput_"+myObj+".pdf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            myObj = LocalDateTime.now(); // Create a date object
        	System.out.print("end date/time - "+myObj);
            }
        } 	      
        }
    }
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}

