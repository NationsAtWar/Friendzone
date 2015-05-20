package org.nationsatwar.friendzone.buckets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class Bucket {
	
	private int hierarchyLevel = 1;
	private int playerCapacity = 0;

	private boolean restrictPlayersToParent;
	private boolean restrictBucketsToParent;
	
	private List<String> allowedSpans;
	private List<Bucket> spanBuckets;
	
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
	 * @return Returns false if a player can't be added due to capacity being reached or if bucket
	 * 		restricts players outside the hierarchy. True otherwise
	 */
	public boolean addPlayer(EntityPlayer player) {
		
		if (playerCapacityReached())
			return false;
		
		UUID playerUUID = player.getUniqueID();
		
		if (restrictPlayersToParent && !parentBucket.containsPlayer(player))
			return false;
			
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
	
	/**
	 * Adds an allowed span to the bucket
	 */
	public void addAllowedSpan(String spanTag) {
		
		allowedSpans.add(spanTag);
	}
	
	/**
	 * Removes an allowed span from the bucket
	 */
	public void removeAllowedSpan(String spanTag) {
		
		allowedSpans.remove(spanTag);
	}
	
	/**
	 * Checks to see if the bucket allows the specified span
	 * 
	 * @param spanTag The span tag you wish to inquire about
	 */
	public boolean allowsSpan(String spanTag) {
		
		if (allowedSpans.contains(spanTag))
			return true;
		else
			return false;
	}
	
	/**
	 * Adds a bucket to the span bucket list
	 * 
	 * @param bucket The bucket you wish to add to the span bucket list
	 * @return Returns false if the span bucket does not contain an allowed tag or if the bucket 
	 * 		exists outside the hierarchy when restricted. True otherwise
	 */
	public boolean addSpanBucket(Bucket bucket) {
		
		if (!spanBucketAllowed(bucket))
			return false;
		
		if (restrictBucketsToParent && !parentBucket.isChildBucket(bucket))
			return false;
		
		spanBuckets.add(bucket);
		return true;
	}
	
	private boolean spanBucketAllowed(Bucket bucket) {
		
		for (String allowedSpan : allowedSpans)
			if (bucket.allowsSpan(allowedSpan))
				return true;
		
		return false;
	}
	
	/**
	 * Sets whether or not to allow players that exist outside the hierarchy
	 * 
	 * @param value The value you wish to set
	 */
	public void setPlayerHierarchyRestriction(boolean value) {
		
		restrictPlayersToParent = value;
	}
	
	/**
	 * Sets whether or not to allow buckets that exist outside the hierarchy
	 * 
	 * @param value The value you wish to set
	 */
	public void setBucketHierarchyRestriction(boolean value) {
		
		restrictBucketsToParent = value;
	}
	
	/**
	 * Sets the maximum amount of players this bucket can hold, 0 is unlimited
	 * 
	 * @return Returns false if the new playerCapacity is less than the bucket's current player count
	 */
	public boolean setPlayerCapacity(int playerCapacity) {
		
		if (playerCapacity > 0 && playerCapacity < playerList.size())
			return false;
		
		this.playerCapacity = playerCapacity;
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
	
	/* Potentially useful
	private boolean existsInHierarchy(Bucket bucket) {
		
		if (getTopLevelBucket().isChildBucket(bucket))
			return true;
		
		return false;
	}

	private boolean existsInHierarchy(UUID playerUUID) {
		
		if (getTopLevelBucket().playerList.contains(playerUUID))
			return true;
		
		return false;
	}
	
	private Bucket getTopLevelBucket() {
		
		if (hierarchyLevel == 1)
			return this;
		
		if (parentBucket.getHierarchyLevel() == 1)
			return parentBucket;
		
		return parentBucket.getTopLevelBucket();
	}
	*/
	
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