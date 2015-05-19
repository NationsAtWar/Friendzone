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
	
	private static boolean bucketExists(String bucketName) {
		
		if (bucketList.containsKey(bucketName))
			return true;
		else
			return false;
	}
}