package songlib.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import songlib.app.Song; 

public class SonglibController {
	
	@FXML ListView<Song> songListView;
	@FXML Button addSongBtn;
	@FXML Button editSongBtn;
	@FXML Button deleteSongBtn;
	@FXML TextField songDetailView;
	@FXML TextField songNameText;
	@FXML TextField artistNameText;
	@FXML TextField albumNameText;
	@FXML TextField yearText;
	
	private ObservableList<Song> songObjList; 
	private Song currentSong;
	private int currentIndex;
	
	/**
	 * Function called once the app is launched
	 * @param mainStage
	 */
	public void init(Stage mainStage) {
		// create an ObservableList
		// from an ArrayList 
		
		//Hardcoding some songs
		Song song1 = new Song("Dynamite", "BTS"); 
		Song song2 = new Song("Ice Cream", "Blackpink");
		Song song3 = new Song("Daechitwa", "Agust D"); 
		Song song4 = new Song("My Time", "JK"); 
		Song song5 = new Song("Oh my god", "G-Idle"); 
		song1.albumName = "Dynamite"; 
		song2.year = "2020"; 
		
		songObjList = FXCollections.observableArrayList(song1, song2, song3, song4, song5);
				
		Collections.sort(songObjList);    
		System.out.print(songObjList);
		
		songListView.setItems(songObjList);
		
		songListView.getSelectionModel().select(0);
		showItem(mainStage);
		
		//set listener 
		songListView.getSelectionModel().selectedItemProperty().addListener(new
		ChangeListener<Song>() { public void changed(ObservableValue<? extends Song>
			observable, Song oldValue, Song newValue) { showItem(mainStage);
			System.out.println("selection changed"); } });
		 
	}
	
	/**
	 * Function called upon selecting a song
	 * Populates song details view
	 * @param mainStage
	 */
	private void showItem(Stage mainStage) {
		int index = songListView.getSelectionModel().getSelectedIndex();
		if(index < 0 || index > songObjList.size()-1) {
			System.out.println("whyyy");
			return;
		}
		System.out.print("Showing item: " + index);
		
		//Populate song details view
		String content = "Song Name: " + songObjList.get(index).songName + " Artist " + songObjList.get(index).artistName;
		songDetailView.setText(content);
		String cont = ""; 
		if(songObjList.get(index).albumName != null) {
			cont = cont + " Album Name " + songObjList.get(index).albumName; 
		}
		if(songObjList.get(index).year != null) {
			cont = cont + " Year " + songObjList.get(index).year; 
		}
		songDetailView.appendText(cont); 
		
		//Populate current song
		currentSong = songObjList.get(index);
		currentIndex = index;
	}
	
	/**
	 * Adds a song to songObjList
	 * @param e
	 */
	public void addSong(ActionEvent e) {
		// Check for incomplete fields
		if(songNameText.getText().isEmpty() || artistNameText.getText().isEmpty()) {
			//error message - Alert
			Alert alert = new Alert(AlertType.INFORMATION);
			//alert.initOwner(mainStage);
			alert.setTitle("Incomplete Fields");
			alert.setHeaderText("Error!");
			String content = "Please fill in the Song Name and Artist Name Fields."; 
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		
		Song newSong = new Song(songNameText.getText(), artistNameText.getText()); 
		newSong.albumName = albumNameText.getText(); 
		newSong.year = yearText.getText();
		
		//Check for duplicates
		if(containsSong(songObjList,newSong)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			//alert.initOwner(mainStage);
			alert.setTitle("Song Already Exists!");
			alert.setHeaderText("Error!");
			String content = "This song already exists in the song list."; 
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		
		songObjList.add(newSong); 
		Collections.sort(songObjList); //throws an error = TO DO LATER 
		
		// Select newly added song
		songListView.getSelectionModel().select(songObjList.indexOf(newSong));
		
		//Clear form
		songNameText.clear();
		artistNameText.clear();
		albumNameText.clear();
		yearText.clear();			
	}
	
	/**
	 * Delete selected song form list
	 * @param e
	 */
	public void deleteSong(ActionEvent e) {
		songObjList.remove(currentSong);
		
		System.out.println("list length: " + songObjList.size());
		if(songObjList.isEmpty()) {
			songDetailView.clear();
			return;
		}
		
		if(currentIndex == songObjList.size()-1) {
			songListView.getSelectionModel().select(currentIndex);
		}else {
			songListView.getSelectionModel().select(currentIndex+1);
		}	
		System.out.println("Index after del: " + currentIndex);		
	}
	
	/**
	 * Helper function to check duplicate songs
	 * @param list
	 * @param song
	 * @return
	 */
	public boolean containsSong(final List<Song> list, final Song song){
	   for(int i = 0; i < list.size(); i++) {
		   if(list.get(i).songName.equals(song.songName) && list.get(i).artistName.equals(song.artistName)) {
			   return true; 
		   }
	   }
	   return false;
	}
}
