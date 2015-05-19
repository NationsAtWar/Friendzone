package org.nationsatwar.friendzone.buckets;

import java.util.List;

public class BucketAPI {
	
	/**
	 * Creates a new bucket
	 * 
	 * @param bucketName The name of your new bucket
	 * @return The new bucket you created, null if name already exists
	 */
	public static Bucket createBucket(String bucketName) {
		
		return BucketManager.createBucket(bucketName);
	}
	
	/**
	 * Gets a bucket by name
	 * 
	 * @param bucketName The name of the bucket you wish to retrieve
	 * @return The bucket, null if name doesn't exist
	 */
	public static Bucket getGucket(String bucketName) {
		
		return BucketManager.getBucket(bucketName);
	}
	
	/**
	 * Gets a list of buckets by tag
	 * 
	 * @param tag The tag of the buckets you wish to retrieve
	 * @return The list of buckets with the specified tag
	 */
	public static List<Bucket> getBucketsByTag(String tag) {
		
		return BucketManager.getBucketsByTag(tag);
	}
}