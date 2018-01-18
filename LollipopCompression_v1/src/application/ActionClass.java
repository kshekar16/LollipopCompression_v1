package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.stage.Stage;

import java.awt.Desktop;
import java.awt.image.BufferedImage;

public class ActionClass 
{
	static ArrayList<File> searchedFiles = new ArrayList<File>(5);

	public static File[] getFiles(String sPath) {
		/*
		 * String Username = System.getProperty("user.name"); String sPath = "C:/Users/"
		 * + Username; System.out.println(sPath);
		 */

		File directory = new File(sPath);
		File[] dir = directory.listFiles();

		//for (int i = 0; i < dir.length; i++) {
			//System.out.println(dir[i]);
		//}
		// System.out.println("");
		// System.out.println("Files in Increasing Order (not including directories)");
		// System.out.println("");
		//
		// ascendingOrder(dir);
		// dateModified(dir);
		//
		return dir;
	}

	public static void ascendingOrder(File[] dir) {
		// Function Not Working Yet

		for (int i = 0; i < dir.length; i++) {
			if (dir[i].length() == 0) {
				continue;
			}
			System.out.println(dir[i]);
			System.out.println("File size is: " + dir[i].length());
		}
	}

	public static void dateModified(File[] dir) {
		for (int i = 0; i < dir.length; i++) {
			System.out.println(dir[i]);
			System.out.println("Date Modified: " + dir[i].lastModified());
		}
	}

	public static void openFile(File file) throws IOException 
	{

		if (!Desktop.isDesktopSupported()) {
			System.out.println("Desktop is not supported");
			return;
		}

		Desktop desktop = Desktop.getDesktop();
		if (file.exists()) {
			desktop.open(file);
		}

	}

	public static void createDirectory(String sPath) throws IOException {
		try {
			File directory = new File(sPath + "/LollipopCompression");
			boolean success = directory.mkdir();
			if (success) {
				System.out.println("Success");
			} else {
				System.out.println("Failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertImage(String sPath, String sName, URL url) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(url);
			ImageIO.write(image, "png", new File(sPath + "/" + sName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initiateFontAwesome() {

	}

	public static File[] sortDirectories(String sPath) {

		return null;

	}

	public static File[] initiateMainDirectories(String sPath) throws IOException {
		File[] mainDir = new File[6];
		mainDir[0] = searchMainDirectory(sPath, "Music");
		mainDir[1] = searchMainDirectory(sPath, "Picture");
		mainDir[2] = searchMainDirectory(sPath, "Video");
		mainDir[3] = searchMainDirectory(sPath, "Documents");
		mainDir[4] = searchMainDirectory(sPath, "Downloads");
		mainDir[5] = searchMainDirectory(sPath, "Desktop");

		return mainDir;
	}

	public static File searchMainDirectory(String sPath, String sfileName) throws IOException {
		try {
			File directory = new File(sPath);
			File[] dir = directory.listFiles();

			for (File file : dir) {
				if (file.getName().startsWith(sfileName) && file.isDirectory()) {
					return file;
				} else if (sfileName.equalsIgnoreCase(file.getName()) && file.isDirectory()) {
					return file;
				} else if (file.getName().contains(sfileName) && file.isDirectory()) {
					return file;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void searchPath(String keyWord, String sPath) throws IOException {
		try {
			// Loop through every folder to see if the file exists based on whether it
			// contains or starts with entered info
			// Start by going to basic path and getting all overall files and folders
			// Loop through all overall files and determine if they are a directory or file
			// If they are a directory, recursively check if it is a directory, if it is,
			// recall the function until there are only
			// files and loop through them to check if they're equal to or contain the
			// search word

			File ovlFolder = new File(sPath);
			File[] ovlFiles = ovlFolder.listFiles();
			//System.out.println(ovlFiles.length);
			for (int i = 0; i < ovlFiles.length; i++) 
			{
				if (ovlFiles[i].getName().contains(keyWord) || ovlFiles[i].getName().startsWith(keyWord)|| ovlFiles[i].getName().equalsIgnoreCase(keyWord)) 
				{
					// searchedFiles[j] = ovlFiles[i];
					if (ovlFiles[i] != null)
					{
						searchedFiles.add(ovlFiles[i]);
						//System.out.println(searchedFiles.size());
						//System.out.println(ovlFiles[i].getAbsolutePath());
					}
					// System.out.println(j);
					// j++;
				}
				else
				{
					continue;
				}
				if (ovlFiles[i].isDirectory()) {
					searchPath(keyWord, ovlFiles[i].getAbsolutePath());
				}

			}
			// for(int a = 0; a < searchedFiles.length; a++)
			// {
			// System.out.println(searchedFiles[a].getAbsolutePath());
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<File> getSearchedFiles()
	{
		for(int i = 0; i < searchedFiles.size(); i++)
		{
			System.out.println(searchedFiles.get(i));
		}
		
		return searchedFiles;
	}
}
