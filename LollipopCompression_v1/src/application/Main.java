package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import jiconfont.javafx.IconFontFX;
import jiconfont.javafx.IconNode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
 * Lollipop Compression v1: Design and Implement Swing elements using JavaFx
 * @author Kaveer Shekar
 * 		   Computer Engineering
 *		   Purdue University '20
 *		   All Rights Reserved
 */

public class Main extends Application {
	Stage window;
	Scene scene1, scene2;
	String Username = System.getProperty("user.name");
	String css = this.getClass().getResource("application.css").toExternalForm();
	String sPath = "C:\\Users\\" + Username;
	TextField urlBar;
	BorderPane outerCenter;
	GridPane centerPane;
	Text txtItems;
	int iItems;
	ArrayList<File> searchedFiles;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane homePane = new BorderPane();
			Scene homeScene = new Scene(homePane);
			homeScene.getStylesheets().add(css);

			// Class:
			// object.getStyleClass().add("class_name");
			// ID:
			// object.setId("id_name");

			ActionClass.createDirectory(sPath);

			HBox topPane = setTopPane(primaryStage); // Top Part of BorderPane
			ScrollPane leftScrolly = setLeftPane(primaryStage); // Left Part of BorderPane

			// ----------Center Part of Home Screen------------------------------

			outerCenter = new BorderPane();
			centerPane = new GridPane();
			outerCenter.setCenter(centerPane);

			// ----------Bottom Part of Screen-----------------------------------
			txtItems = new Text("Kayzorgh Inc. \u00a9 All Rights Reserved");

			homePane.setLeft(leftScrolly);
			homePane.setTop(topPane);
			homePane.setPrefSize(800, 600);
			homePane.setBottom(txtItems);
			homePane.setCenter(outerCenter);

			primaryStage.setScene(homeScene);
			primaryStage.setTitle("Lollipop Compression");
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HBox setTopPane(Stage primaryStage) {
		// ----------Top Part of Home Screen-----------------------------
		HBox topPane = new HBox();
		topPane.setPadding(new Insets(15, 12, 15, 12));
		urlBar = new TextField(sPath);
		Button btnRefresh = new Button();
		TextField searchBar = new TextField();
		searchBar.setPrefWidth(topPane.getPrefWidth());
		searchBar.setPromptText("Search");
		Button btnSearch = new Button();
		topPane.getChildren().addAll(urlBar, btnRefresh, searchBar, btnSearch);
		HBox.setHgrow(urlBar, Priority.ALWAYS);
		HBox.setHgrow(btnSearch, Priority.ALWAYS);
		HBox.setHgrow(btnRefresh, Priority.ALWAYS);
		HBox.setHgrow(searchBar, Priority.ALWAYS);

		btnSearch.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					ActionClass.searchPath(searchBar.getText(), urlBar.getText());
					searchedFiles = ActionClass.getSearchedFiles();
					BorderPane newPane = Directory.setSearchedPane(primaryStage, searchedFiles);
					outerCenter.setCenter(newPane);
					//outerCenter = Directory.setSearchedPane(window, searchedFiles);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

			}
		});

		searchBar.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					System.out.println("searchBar on focus");
				} else {
					if (searchBar.getText() == "") {
						searchBar.setText("Search");
					}
					System.out.println("searchBar out focus");
				}
			}
		});

		urlBar.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					urlBar.selectAll();
					System.out.println("urlBar on focus");
				} else {
					System.out.println("urlBar out focus");
				}
			}
		});

		searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						ActionClass.searchPath(searchBar.getText(), urlBar.getText());
						searchedFiles = ActionClass.getSearchedFiles();
						BorderPane newPane = Directory.setSearchedPane(primaryStage, searchedFiles);
						outerCenter.setCenter(newPane);
						//outerCenter = Directory.setSearchedPane(window, searchedFiles);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		urlBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					System.out.println("Enter Pressed");
				}
			}
		});
		return topPane;
	}

	public ScrollPane setLeftPane(Stage primaryStage) {
		ScrollPane leftScrolly = new ScrollPane();
		VBox fileList = new VBox();
		HBox desktopBox = new HBox();
		Button btnDesktop = new Button("Desktop");

		btnDesktop.setId("leftPaneTabs");
		Accordion accDesktop = new Accordion();
		TitledPane tpDesktop = new TitledPane("", new Button("Desktop"));
		accDesktop.setId("accordionLeft");
		accDesktop.getPanes().add(tpDesktop);
		desktopBox.getChildren().addAll(accDesktop, btnDesktop);

		HBox documentsBox = new HBox();
		Button btnDocuments = new Button("Documents");
		Accordion accDocuments = new Accordion();
		TitledPane tpDocuments = new TitledPane("", new Button("Documents"));
		accDocuments.getPanes().add(tpDocuments);
		documentsBox.getChildren().addAll(accDocuments, btnDocuments);
		btnDocuments.setId("leftPaneTabs");

		HBox downloadsBox = new HBox();
		Button btnDownloads = new Button("Downloads");
		Accordion accDownloads = new Accordion();
		TitledPane tpDownloads = new TitledPane("", new Button("Downloads"));
		accDownloads.getPanes().add(tpDownloads);
		downloadsBox.getChildren().addAll(accDownloads, btnDownloads);
		btnDownloads.setId("leftPaneTabs");

		HBox picturesBox = new HBox();
		Button btnPictures = new Button("Pictures");
		Accordion accPictures = new Accordion();
		TitledPane tpPictures = new TitledPane("", new Button("Pictures"));
		accPictures.getPanes().add(tpPictures);
		picturesBox.getChildren().addAll(accPictures, btnPictures);
		btnPictures.setId("leftPaneTabs");

		HBox musicBox = new HBox();
		Button btnMusic = new Button("Music");
		Accordion accMusic = new Accordion();
		TitledPane tpMusic = new TitledPane("", new Button("Music"));
		accMusic.getPanes().add(tpMusic);
		musicBox.getChildren().addAll(accMusic, btnMusic);
		btnMusic.setId("leftPaneTabs");

		HBox videosBox = new HBox();
		Button btnVideos = new Button("Videos");
		Accordion accVideos = new Accordion();
		TitledPane tpVideos = new TitledPane("", new Button("Videos"));
		accVideos.getPanes().add(tpVideos);
		videosBox.getChildren().addAll(accVideos, btnVideos);
		btnVideos.setId("leftPaneTabs");

		btnDesktop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File desktopFile;
				try {
					desktopFile = ActionClass.searchMainDirectory(sPath, "Desktop");
					BorderPane newPane = Directory.setBorderPane(primaryStage, desktopFile);
					iItems = Directory.getNumItems(desktopFile);
					txtItems.setText(iItems + " items.");
					urlBar.setText(desktopFile.getAbsolutePath());
					outerCenter.setCenter(newPane);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		btnDocuments.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File documentFile;
				try {
					documentFile = ActionClass.searchMainDirectory(sPath, "Documents");
					BorderPane newPane = Directory.setBorderPane(primaryStage, documentFile);
					iItems = Directory.getNumItems(documentFile);
					txtItems.setText(iItems + " items.");
					urlBar.setText(documentFile.getAbsolutePath());
					outerCenter.setCenter(newPane);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		btnDownloads.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File downloadFile;
				try {
					downloadFile = ActionClass.searchMainDirectory(sPath, "Downloads");
					BorderPane newPane = Directory.setBorderPane(primaryStage, downloadFile);
					iItems = Directory.getNumItems(downloadFile);
					txtItems.setText(iItems + " items.");
					urlBar.setText(downloadFile.getAbsolutePath());
					outerCenter.setCenter(newPane);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		btnPictures.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File pictureFile;
				try {
					pictureFile = ActionClass.searchMainDirectory(sPath, "Pictures");
					BorderPane newPane = Directory.setBorderPane(primaryStage, pictureFile);
					iItems = Directory.getNumItems(pictureFile);
					txtItems.setText(iItems + " items.");
					urlBar.setText(pictureFile.getAbsolutePath());
					outerCenter.setCenter(newPane);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		btnVideos.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File videoFile;
				try {
					videoFile = ActionClass.searchMainDirectory(sPath, "Videos");
					BorderPane newPane = Directory.setBorderPane(primaryStage, videoFile);
					iItems = Directory.getNumItems(videoFile);
					txtItems.setText(iItems + " items.");
					urlBar.setText(videoFile.getAbsolutePath());
					outerCenter.setCenter(newPane);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		btnMusic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File musicFile;
				try {
					musicFile = ActionClass.searchMainDirectory(sPath, "Music");
					BorderPane newPane = Directory.setBorderPane(primaryStage, musicFile);
					iItems = Directory.getNumItems(musicFile);
					txtItems.setText(iItems + " items.");
					urlBar.setText(musicFile.getAbsolutePath());
					outerCenter.setCenter(newPane);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		fileList.getChildren().addAll(desktopBox, documentsBox, downloadsBox, picturesBox, musicBox, videosBox);

		leftScrolly.setContent(fileList);
		leftScrolly.setPrefWidth(100);
		leftScrolly.setMinViewportWidth(130);

		accDesktop.setMaxWidth(0);
		accDesktop.setMinWidth(25);

		accDocuments.setMaxWidth(0);
		accDocuments.setMinWidth(25);

		accDownloads.setMaxWidth(0);
		accDownloads.setMinWidth(25);

		accPictures.setMaxWidth(0);
		accPictures.setMinWidth(25);

		accVideos.setMaxWidth(0);
		accVideos.setMinWidth(25);

		accMusic.setMaxWidth(0);
		accMusic.setMinWidth(25);

		btnDesktop.setMinWidth(122);
		btnDocuments.setMinWidth(122);
		btnDownloads.setMinWidth(122);
		btnPictures.setMinWidth(122);
		btnVideos.setMinWidth(122);
		btnMusic.setMinWidth(122);

		leftScrolly.getStyleClass().add("leftScrolly");
		leftScrolly.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		leftScrolly.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		return leftScrolly;
	}

}
