package org.nationsatwar.friendzone;

import org.nationsatwar.friendzone.buckets.Bucket;
import org.nationsatwar.friendzone.buckets.BucketAPI;

public class ExampleFunctions {
	
	// Nothing in this class is actually ever called
	// These are just examples of how you would create various setups
	
	// This is how a party would 
	public void createParty() {
		
		Bucket bucket = BucketAPI.createBucket("UniquePartyID");
	}
}