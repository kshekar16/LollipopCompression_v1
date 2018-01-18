package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.omg.Messaging.SyncScopeHelper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Directory {
	static File desigFile;
	static int iterator;

	public static Scene createScene(Stage window, Scene prevScene, String sPath) {
		// Define and Declare all elements
		Button btnReturn = new Button("Go back to Scene 1");
		btnReturn.setOnAction(e -> window.setScene(prevScene));

		// Create Pane for type of layout you want to have
		GridPane directory = new GridPane();
		directory.getChildren().add(btnReturn);
		directory.setPrefSize(800, 600);

		// Create Scene to lie on top of pane
		Scene scene2 = new Scene(directory);

		return scene2;
	}

	public static BorderPane setSearchPane(Stage window, File[] fileSet) {
		return null;
	}
	
	public static BorderPane setNullPane(Stage window)
	{
		BorderPane outerCenter = new BorderPane();
		Label label = new Label("No files found.");
		outerCenter.setCenter(label);
		
		return outerCenter;
	}
	public static BorderPane setSearchedPane(Stage window, ArrayList<File> searchedFiles)
	{
		BorderPane outerCenter = new BorderPane();
		ScrollPane centerScroll = new ScrollPane();
		GridPane newPane = new GridPane();
		
		centerScroll.setContent(newPane);
		outerCenter.setCenter(centerScroll);
		outerCenter.setBottom(setHBox());
		TextField[] labelList = new TextField[searchedFiles.size()];
		if(labelList.length == 0)
		{
			outerCenter = setNullPane(window);
			return outerCenter;
		}
		
		for(int i = 0; i < searchedFiles.size(); i++)
		{
			labelList[i] = new TextField(searchedFiles.get(i).getName());
		}
		
		int k = 0;
		int l = 0;
		int newRow = (int) (window.getWidth() / 200);
		//System.out.println(window.getWidth());
		//int widthFactor = (int) (window.getWidth() / newRow);
		//System.out.println(widthFactor);
		for (int j = 0; j < labelList.length; j++) 
		{
			if (j % newRow == 0)
			{
				k++;
				l = 0;
			}
			
			labelList[j].setEditable(false);
			labelList[j].setId("directoryFiles");
			newPane.add(labelList[j], l, k);
			l++;
		}

		for(int z = 0; z < labelList.length; z++)
		{
			labelList[z].focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					if (newPropertyValue) 
					{
						for(int i = 0; i < labelList.length; i++)
						{
							if(labelList[i].isFocused())
							{
								
								if(searchedFiles.get(i).isDirectory())
								{
									//setBorderPane(window, dir[i]);
								}
								else
								{
									setDesigFile(searchedFiles.get(i));
								}
							}
						}
					} 
					
					else 
					{
						
					}
				}
			});
		}
		
		
		centerScroll.setPrefWidth(100);
		centerScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		centerScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		return outerCenter;
		
	}

	public static BorderPane setBorderPane(Stage window, File file) {
		// When calling this class, you need to have it show all the folders that are
		// within this directory.
		// 1. Call ActionClass.getFiles()
		// 2. Run for loop to display all the files
		// 3. Make sure all files have an action attached to them.
		BorderPane outerCenter = new BorderPane();
		ScrollPane centerScroll = new ScrollPane();
		GridPane newPane = new GridPane();

		centerScroll.setContent(newPane);
		outerCenter.setCenter(centerScroll);
		outerCenter.setBottom(setHBox());
		File[] dir = ActionClass.getFiles(file.getAbsolutePath());
		TextField[] labelList = new TextField[dir.length];
		for (int i = 0; i < dir.length; i++) {
			labelList[i] = new TextField(dir[i].getName());
		}
		int k = 0;
		int l = 0;
		int newRow = (int) (window.getWidth() / 200);
		//System.out.println(window.getWidth());
		//int widthFactor = (int) (window.getWidth() / newRow);
		//System.out.println(widthFactor);
		for (int j = 0; j < labelList.length; j++) 
		{
			if (j % newRow == 0)
			{
				k++;
				l = 0;
			}
			
			labelList[j].setEditable(false);
			labelList[j].setId("directoryFiles");
			newPane.add(labelList[j], l, k);
			l++;
		}

		for(int z = 0; z < labelList.length; z++)
		{
			labelList[z].focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {
					if (newPropertyValue) 
					{
						for(int i = 0; i < labelList.length; i++)
						{
							if(labelList[i].isFocused())
							{
								
								if(dir[i].isDirectory())
								{
									setBorderPane(window, dir[i]);
								}
								else
								{
									setDesigFile(dir[i]);
								}
							}
						}
					} 
					
					else 
					{
						
					}
				}
			});
		}
		
		
		centerScroll.setPrefWidth(100);
		centerScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		centerScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		return outerCenter;
	}

	public static void setDesigFile(File file) 
	{
		desigFile = file;
		System.out.println(desigFile);
	}

	public static HBox setHBox() {
		HBox hbox = new HBox();
		Button btnOpen = new Button("Open");
		Button btnCompress = new Button("Compress");
		Button btnDecompress = new Button("Decompress");
		
		btnOpen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				try {
					ActionClass.openFile(desigFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		
		hbox.getChildren().addAll(btnOpen, btnCompress, btnDecompress);

		return hbox;
	}

	public static int getNumItems(File file) {
		File[] dir = file.listFiles();
		int numItems = dir.length;

		return numItems;
	}
}