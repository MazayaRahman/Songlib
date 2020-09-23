package songlib.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.*; 

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
	@FXML Button saveBtn; 
	@FXML Button cancelBtn;
	@FXML TextArea songDetailView;
	@FXML TextField songNameText;
	@FXML TextField artistNameText;
	@FXML TextField albumNameText;
	@FXML TextField yearText;
	
	private ObservableList<Song> songObjList; 
	private Song currentSong;
	private int currentIndex;
	private boolean addClicked = false; 
	
	/**
	 * Function called once the app is launched
	 * @param mainStage
	 */
	public void init(Stage mainStage) {
		// create an ObservableList
		// from an ArrayList 
		
		songObjList = FXCollections.observableArrayList();
		
		//create a file or check for existing file
		File myPlaylist = new File("./src/songlib/files/playlist.txt");
	      try {
			if (myPlaylist.createNewFile()) {
			    System.out.println("File created: " + myPlaylist.getName());
			  } else {
			    System.out.println("File already exists.");
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    //read the file and update the list view
	      try {
	          Scanner myReader = new Scanner(myPlaylist);
	          while (myReader.hasNextLine()) {
	            String data = myReader.nextLine();
	            System.out.println(data);
	            String[] details = data.split(",",-1); 
	            System.out.println(details); 
	            Song newSong = new Song(details[0], details[1]); 
	            if(details[2] != null) {
	            	newSong.albumName = details[2]; 
	            }
	            if(details[3] != null) {
	            	newSong.year = details[3]; 
	            }
	            songObjList.add(newSong); 
	          }
	          myReader.close();
	        } catch (FileNotFoundException e) {
	          System.out.println("An error occurred.");
	          e.printStackTrace();
	     }
				
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
		
		//enable/disable fields when app is started
		songNameText.setDisable(true);
		albumNameText.setDisable(true);
		artistNameText.setDisable(true);
		yearText.setDisable(true); 
		
		saveBtn.setDisable(true);
		cancelBtn.setDisable(true);
		
		 
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
		String content = "Song Name: \t\t\t" + songObjList.get(index).songName + "\n" + "Artist: \t\t\t\t" + songObjList.get(index).artistName;
		songDetailView.setText(content);
		String cont = ""; 
		if(songObjList.get(index).albumName != null) {
			cont = cont + "\n" + "Album Name: \t\t\t" + songObjList.get(index).albumName; 
		}
		if(songObjList.get(index).year != null) {
			cont = cont + "\n" + "Year: \t\t\t\t" + songObjList.get(index).year; 
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
		//add button clicked
		addClicked = true; 
		
		//list disabled
		songListView.setDisable(true);
		
		//make the text fields editable
		songNameText.setDisable(false);
		albumNameText.setDisable(false);
		artistNameText.setDisable(false);
		yearText.setDisable(false); 
		
		//make buttons click able
		saveBtn.setDisable(false);
		cancelBtn.setDisable(false);
		deleteSongBtn.setDisable(true); 
		editSongBtn.setDisable(true); 
		
		//clear form
		songNameText.clear();
		albumNameText.clear();
		yearText.clear();
		artistNameText.clear(); 
	}
	
	/**
	 * Delete selected song form list
	 * @param e
	 */
	public void deleteSong(ActionEvent e) {
		if(songObjList.isEmpty()) {
			//error check
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No more songs left!");
			alert.setHeaderText("Error!");
			String content = "There are no songs to delete."; 
			alert.setContentText(content);
			alert.showAndWait();
			return;
		}
		songObjList.remove(currentSong);
		
		//index selection check
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
	 * Save form details into song form list
	 * @param e
	 */
	
	public void saveSong(ActionEvent e) {
		
		if(songNameText.getText().isEmpty() || artistNameText.getText().isEmpty()) {
			//error message - Alert
			Alert alert = new Alert(AlertType.INFORMATION);
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
		
		//add code
		if(addClicked) {			
			//Check for duplicates
			if(containsSong(songObjList,newSong)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Song Already Exists!");
				alert.setHeaderText("Error!");
				String content = "This song already exists in the song list."; 
				alert.setContentText(content);
				alert.showAndWait();
				return;
			}
			songObjList.add(newSong); 
			Collections.sort(songObjList);
			

			addClicked = false; 
		}
		else {			
			//edit song
			
			//Check for duplicates
			if(containsSong(songObjList,newSong) && currentSong.compareTo(newSong) != 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Song Already Exists!");
				alert.setHeaderText("Error!");
				String content = "This song already exists in the song list."; 
				alert.setContentText(content);
				alert.showAndWait();
				return;
			}
			
			songObjList.remove(currentSong);
			songObjList.add(newSong); 
			Collections.sort(songObjList);
		}
		// Select newly added song
		songListView.getSelectionModel().select(songObjList.indexOf(newSong));
		
		//Clear form
		songNameText.clear();
		artistNameText.clear();
		albumNameText.clear();
		yearText.clear();
		
		songNameText.setDisable(true);
		albumNameText.setDisable(true);
		artistNameText.setDisable(true);
		yearText.setDisable(true); 
		
		//list enabled
		songListView.setDisable(false);
		
		//enable/disable btns
		saveBtn.setDisable(true);
		cancelBtn.setDisable(true); 
		deleteSongBtn.setDisable(false);
		addSongBtn.setDisable(false);
		editSongBtn.setDisable(false);
	}
	
	/**
	 * Edit selected song form list
	 * @param e
	 */
	
	public void editSong(ActionEvent e) {
		//add is not clicked
		addClicked = false; 
		
		//list disabled
		songListView.setDisable(true);
		
		//make the text fields editable
		songNameText.setDisable(false);
		albumNameText.setDisable(false);
		artistNameText.setDisable(false);
		yearText.setDisable(false);
		
		//make the buttons editable
		saveBtn.setDisable(false);
		cancelBtn.setDisable(false);
		deleteSongBtn.setDisable(true);
		addSongBtn.setDisable(true);
		
		//fill the fields with existing song info
		songNameText.setText(currentSong.songName);
		albumNameText.setText(currentSong.albumName);
		artistNameText.setText(currentSong.artistName);
		yearText.setText(currentSong.year);		
	}
	
	/**
	 * Cancel ongoing action
	 * @param e
	 */
	
	public void cancelSong(ActionEvent e) {
		
		//Clear form
		songNameText.clear();
		artistNameText.clear();
		albumNameText.clear();
		yearText.clear();
		
		//Disable form
		songNameText.setDisable(true);
		albumNameText.setDisable(true);
		artistNameText.setDisable(true);
		yearText.setDisable(true);
		
		//list enabled
		songListView.setDisable(false);
		
		//disable/enable btns
		saveBtn.setDisable(true);
		cancelBtn.setDisable(true);
		deleteSongBtn.setDisable(false);
		addSongBtn.setDisable(false);
		editSongBtn.setDisable(false);
		
	}
	/**
	 * Helper function to check duplicate songs
	 * @param list
	 * @param song
	 * @return
	 */
	public boolean containsSong(final List<Song> list, final Song song){
	   for(int i = 0; i < list.size(); i++) {
		   if(list.get(i).songName.toLowerCase().equals(song.songName.toLowerCase()) && list.get(i).artistName.toLowerCase().equals(song.artistName.toLowerCase())) {
			   return true; 
		   }
	   }
	   return false;
	}
	/**
	 * Close session and write updated list onto file
	 */
	public void close() {
		//write the list onto the file
		try {
		      FileWriter myWriter = new FileWriter("./src/songlib/files/playlist.txt");
		      for(int i = 0; i < songObjList.size(); i++) {
		    	  Song thisSong = songObjList.get(i); 
		    	  myWriter.write("" + thisSong.songName + "," + thisSong.artistName + "," + thisSong.albumName + "," + thisSong.year + "\n"); 
		      }
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		   } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		   }
		System.out.println("I AM BEING CLOSED"); 
	}
}
