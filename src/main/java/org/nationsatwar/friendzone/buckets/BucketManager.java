package org.nationsatwar.friendzone.buckets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BucketManager {
	
	private static Map<String, Bucket> bucketList = new HashMap<String, Bucket>();
	
	private BucketManager() {}
	
	protected static Bucket createBucket(String bucketName) {
		
		if (bucketExists(bucketName))
			return null;
			
		Bucket bucket = new Bucket();
		bucketList.put(bucketName, bucket);
		return bucket;
	}
	
	protected static void removeBucket(String bucketName) {
		
		if (!bucketExists(bucketName))
			return;
		
		Bucket bucket = getBucket(bucketName);
		
		List<String> bucketsToRemove = new ArrayList<String>();
		
		for (String checkBucket : bucketList.keySet())
			if (bucket.getChildBuckets().contains(bucketList.get(checkBucket)))
				bucketsToRemove.add(checkBucket);
		
		for (String bucketToRemove : bucketsToRemove)
			bucketList.remove(bucketToRemove);
			
		bucketList.remove(bucketName);
	}
	
	protected static Bucket getBucket(String bucketName) {
		
		if (bucketExists(bucketName))
			return bucketList.get(bucketName);
		else
			return null;
	}
	
	protected static List<Bucket> getBucketsByTag(String tag) {
		
		List<Bucket> buckets = new ArrayList<Bucket>();
		
		for (Bucket bucket : bucketList.values())
			if (bucket.hasTag(tag))
				buckets.add(bucket);
		
		return buckets;
	}
	
	protected static List<Bucket> getAllBuckets() {
		
		List<Bucket> buckets = new ArrayList<Bucket>();

		for (Bucket bucket : bucketList.values())
			buckets.add(bucket);
		
		return buckets;
	}
	
	protected static List<String> getAllBucketNames() {
		
		List<String> bucketNames = new ArrayList<String>();

		for (String bucketName : bucketList.keySet())
			bucketNames.add(bucketName);
		
		return bucketNames;
	}
	
	private static boolean bucketExists(String bucketName) {
		
		if (bucketList.containsKey(bucketName))
			return true;
		else
			return false;
	}
}