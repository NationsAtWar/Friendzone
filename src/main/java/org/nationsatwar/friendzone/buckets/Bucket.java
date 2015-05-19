package org.nationsatwar.friendzone.buckets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class Bucket {
	
	private int hierarchyLevel = 1;
	private int playerCapacity = 0;
	
	private Bucket parentBucket;
	private List<Bucket> childBuckets = new ArrayList<Bucket>();
	
	private List<UUID> playerList = new ArrayList<UUID>();
	
	private List<String> tags = new ArrayList<String>();
	
	protected Bucket() {}
	
	protected Bucket(String tag) {
		
		tags.add(tag);
	}
	
	/**
	 * Adds a player to this bucket. Also adds the player to all parent buckets.
	 * 
	 * @param player The player you wish to add to the bucket
	 * @return Returns false if a player can't be added due to capacity being reached, true otherwise
	 */
	public boolean addPlayer(EntityPlayer player) {
		
		if (playerCapacityReached())
			return false;
		
		UUID playerUUID = player.getUniqueID();
		addPlayer(playerUUID);
		return true;
	}
	
	private void addPlayer(UUID playerUUID) {
		
		if (!playerList.contains(playerUUID))
			playerList.add(playerUUID);
		
		parentBucket.addPlayer(playerUUID);
	}
	
	/**
	 * Removes a player from this bucket. Also removes the player from all child buckets.
	 * 
	 * @param player The player you wish to add to the bucket
	 */
	public void removePlayer(EntityPlayer player) {
		
		UUID playerUUID = player.getUniqueID();
		removePlayer(playerUUID);
	}
	
	private void removePlayer(UUID playerUUID) {

		if (playerList.contains(playerUUID))
			playerList.remove(playerUUID);
		
		for (Bucket childBucket : childBuckets)
			childBucket.removePlayer(playerUUID);
	}
	
	/**
	 * Used to see if a player exists inside this bucket
	 */
	public boolean containsPlayer(EntityPlayer player) {
		
		if (playerList.contains(player.getUniqueID()))
			return true;
		else
			return false;
	}
	
	/**
	 * Adds a tag to the bucket
	 */
	public void addTag(String tag) {
		
		tags.add(tag);
	}
	
	/**
	 * Removes a tag from the bucket
	 */
	public void removeTag(String tag) {
		
		tags.remove(tag);
	}
	
	/**
	 * Checks to see if the bucket has the specified tag
	 * 
	 * @param tag The tag you wish to inquire about
	 */
	public boolean hasTag(String tag) {
		
		if (tags.contains(tag))
			return true;
		else
			return false;
	}
	
	/**
	 * Sets this bucket's parent bucket
	 * 
	 * @param bucket The parent bucket you want to set
	 * @return Returns false if you try to set a pre-existing child as a parent, true otherwise
	 */
	public boolean setParent(Bucket bucket) {
		
		if (isChildBucket(bucket)) {
			
			System.out.println("Error: Can't set a child as a parent.");
			return false;
		}
		
		parentBucket = bucket;
		
		if (hasParent()) {
			
			parentBucket.addChildBucket(this);
			hierarchyLevel = bucket.getHierarchyLevel() + 1;
		} else
			hierarchyLevel = 1;
		
		for (Bucket childBucket : childBuckets)
			childBucket.setParent(this);
		
		return true;
	}
	
	private boolean isChildBucket(Bucket bucket) {
		
		if (childBuckets.contains(bucket))
			return true;
		
		for (Bucket childBucket : childBuckets)
			if (childBucket.isChildBucket(bucket))
				return true;
		
		return false;
	}
	
	private int getHierarchyLevel() {
		
		return hierarchyLevel;
	}
	
	private boolean hasParent() {
		
		if (parentBucket == null)
			return false;
		else
			return true;
	}
	
	private boolean playerCapacityReached() {
		
		if (playerCapacity > 0 && playerList.size() >= playerCapacity)
			return true;
		
		if (parentBucket.playerCapacityReached())
			return true;
		
		return false;
	}
	
	private void addChildBucket(Bucket bucket) {
		
		childBuckets.add(bucket);
	}
}