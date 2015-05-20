package org.nationsatwar.friendzone.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import org.nationsatwar.friendzone.buckets.Bucket;
import org.nationsatwar.friendzone.buckets.BucketAPI;
import org.nationsatwar.palette.chat.ChatMessage;

public class ChatCommands implements ICommand {
	
	private List<String> aliases;
	
	private String selectedBucketName = "";
	private Bucket selectedBucket;
	
	public ChatCommands() {
		
		aliases = new ArrayList<String>();
		aliases.add("friendzone");
		aliases.add("fz");
	}

	@Override
	public int compareTo(Object o) {
		
		return 0;
	}

	@Override
	public String getName() {
		
		return "friendzone";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "friendzone <add/remove/get> (bucket name)";
	}

	@Override
	public List<String> getAliases() {
		
		return aliases;
	}
	
	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		
		if (!(sender instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) sender;
		
		// Called if there's no arguments
		if (args.length < 1) {

			ChatMessage.sendMessage(player, "Type more shit down, dude");
			return;
		}
		
		String command = args[0].toLowerCase();
		
		// Add command
		if (command.equals("add")) {
			
			// Insufficient arguments
			if (args.length < 2) {
				
				ChatMessage.sendMessage(player, "Adds a bucket");
				return;
			}
			
			String bucketName = combineArgs(args, 1, args.length - 1);
			
			BucketAPI.createBucket(bucketName);
			ChatMessage.sendMessage(player, "Created new bucket: " + bucketName);
			return;
		}
		
		// Remove command
		if (command.equals("remove")) {
			
			// Insufficient arguments
			if (args.length < 2) {
				
				ChatMessage.sendMessage(player, "Removes a bucket");
				return;
			}
			
			String bucketName = combineArgs(args, 1, args.length - 1);
			
			BucketAPI.removeBucket(bucketName);
			ChatMessage.sendMessage(player, "Removed bucket: " + bucketName);
			return;
		}
		
		// List command
		if (command.equals("list")) {
			
			String bucketNames = "";
			
			for (String bucketName : BucketAPI.getAllBucketNames()) {
				
				if (bucketNames.isEmpty())
					bucketNames += bucketName;
				else
					bucketNames += ", " + bucketName;
			}
			
			ChatMessage.sendMessage(player, "Bucket List: " + bucketNames);
			
			return;
		}
		
		// Select command
		if (command.equals("select")) {
			
			// Insufficient arguments
			if (args.length < 2) {
				
				ChatMessage.sendMessage(player, "Selects a bucket");
				return;
			}
			
			String bucketName = combineArgs(args, 1, args.length - 1);
			
			selectedBucket = BucketAPI.getBucket(bucketName);
			
			if (selectedBucket != null)
				selectedBucketName = bucketName;
			
			ChatMessage.sendMessage(player, "Selected bucket: " + bucketName);
			return;
		}
		
		// Debug command
		if (command.equals("debug")) {
			
			ChatMessage.sendMessage(player, "Debug: " + selectedBucketName);
			return;
		}
		
		// Select command
		if (command.equals("bucket")) {
			
			if (selectedBucket == null) {
				
				ChatMessage.sendMessage(player, "No bucket is currently set");
				return;
			}
			
			// Insufficient arguments
			if (args.length < 2) {
				
				ChatMessage.sendMessage(player, "Currently selected bucket: " + selectedBucketName);
				return;
			}
			
			String subcommand = args[1].toLowerCase();
			
			// Parent command
			if (subcommand.equals("parent")) {
				
				// Insufficient arguments
				if (args.length < 3) {
					
					ChatMessage.sendMessage(player, "Sets the bucket's parent");
					return;
				}
				
				String bucketName = combineArgs(args, 2, args.length - 1);
				Bucket parentBucket = BucketAPI.getBucket(bucketName);
				
				if (parentBucket != null) {
					
					selectedBucket.setParent(parentBucket);
					ChatMessage.sendMessage(player, selectedBucketName + "'s parent is now: " + bucketName);
				} else
					ChatMessage.sendMessage(player, "Bucket does not exist.");
				
				return;
			}
			
			return;
		}
		
		ChatMessage.sendMessage(player, "Unrecognized command");
	}
	
	private String combineArgs(String[] args, int startArgument, int endArgument) {
		
		if (args.length < endArgument + 1 || startArgument > endArgument || startArgument < 0)
			return "Error";
		
		String combinedString = args[startArgument];
		
		for (int i = startArgument + 1; i <= endArgument; i++)
			combinedString += " " + args[i];
		
		return combinedString;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender sender, String[] args,
			BlockPos pos) {
		
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		
		return false;
	}
}