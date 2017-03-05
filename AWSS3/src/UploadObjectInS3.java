
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadObjectInS3 {

	private static String bucketName = "inputdatafor";
	private static String bucketName1 = "ravi18695";
	private static String keyName = "AKIAIYJE4JIAHAAYEYHQ";
	private static String uploadFileName = "C:/Users/aswin/odrive/Desktop/abc.txt";
	private static String uploadFileName1 = "C:/Users/aswin/odrive/Desktop/abcd.txt";
	private static String backup_bucketName = "backupbucketfor";

	public static void main(String[] args) throws IOException {

		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
		InputStream inputStream;
		OutputStream outputStream;
		try {
			System.out.println("Uploading a new object to S3 from a file\n");
			File file = new File(uploadFileName);
		
			
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
			
			
			com.amazonaws.services.s3.model.S3Object object = s3client
					.getObject(new GetObjectRequest(bucketName, keyName));
			
			
//			BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
//			
//			String line;
//			int counter = 1;
//
//			
//			while((line = reader.readLine()) != null) {
//				
//
//			counter = counter + 1;
//			
//			//if(counter == 4 || counter ==5){
//				
//				try {
//					
//					File file1 = new File("C:/Users/aswin/odrive/Desktop/abcd.txt");
//					FileWriter fileWriter = new FileWriter(file,true);
//					fileWriter.write(line);
//				
//					fileWriter.flush();
//					fileWriter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//				System.out.println(line);
//			}
//		//	}
//			
			inputStream = object.getObjectContent();

		outputStream = new FileOutputStream(new File(uploadFileName1));

			try {
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {

					outputStream.write(bytes, 0, read);

				}

				System.out.println("Uploading a output object to S3 from a file\n");
				File file1 = new File(uploadFileName1);
				s3client.putObject(new PutObjectRequest(bucketName1, keyName, file1));

				System.out.println("Done...");

				if (inputStream.read(bytes) > 50) {

					AmazonS3 s3client1 = new AmazonS3Client(new ProfileCredentialsProvider());
					s3client.setRegion(Region.getRegion(Regions.US_WEST_1));

					try {
						if (!(s3client1.doesBucketExist(backup_bucketName))) {
							// Note that CreateBucketRequest does not specify
							// region. So bucket is
							// created in the region specified in the client.
							s3client.createBucket(new CreateBucketRequest(backup_bucketName));
						}
						// Get location.
						String bucketLocation = s3client
								.getBucketLocation(new GetBucketLocationRequest(backup_bucketName));
						System.out.println("Back up bucket location = " + bucketLocation);

					} catch (AmazonServiceException ase) {
						System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
								+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
						System.out.println("Error Message:    " + ase.getMessage());
						System.out.println("HTTP Status Code: " + ase.getStatusCode());
						System.out.println("AWS Error Code:   " + ase.getErrorCode());
						System.out.println("Error Type:       " + ase.getErrorType());
						System.out.println("Request ID:       " + ase.getRequestId());
					} catch (AmazonClientException ace) {
						System.out.println("Caught an AmazonClientException, which " + "means the client encountered "
								+ "an internal error while trying to " + "communicate with S3, "
								+ "such as not being able to access the network.");
						System.out.println("Error Message: " + ace.getMessage());
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					}

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}
}
