package org.nationsatwar.friendzone.buckets;

import java.util.List;

public class BucketAPI {
	
	public static void saveAllBuckets() {
		
		BucketManager.saveBuckets();
	}
	
	public static void loadAllBuckets() {
		
		BucketManager.loadBuckets();
	}
	
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
	 * Removes the specified bucket
	 * 
	 * @param bucketName The name of the bucket you wish to remove
	 */
	public static void removeBucket(String bucketName) {
		
		BucketManager.removeBucket(bucketName);
	}
	
	/**
	 * Gets a bucket by name
	 * 
	 * @param bucketName The name of the bucket you wish to retrieve
	 * @return The bucket, null if name doesn't exist
	 */
	public static Bucket getBucket(String bucketName) {
		
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
	
	/**
	 * Returns a list of all buckets
	 */
	public static List<Bucket> getAllBuckets() {
		
		return BucketManager.getAllBuckets();
	}
	
	/**
	 * Returns a list of all bucket names
	 */
	public static List<String> getAllBucketNames() {
		
		return BucketManager.getAllBucketNames();
	}
}