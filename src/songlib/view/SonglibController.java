package songlib.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	
	public void init(Stage mainStage) {
		// create an ObservableList
		// from an ArrayList  
		Song song1 = new Song("Dynamite", "BTS"); 
		Song song2 = new Song("Ice Cream", "Blackpink");
		Song song3 = new Song("Zinamite", "Apple"); 
		Song song4 = new Song("Alpha", "Zeta"); 
		Song song5 = new Song("Dynamite", "Adele"); 
		song1.albumName = "Dynamite"; 
		song2.year = "2020"; 
		
		songObjList = FXCollections.observableArrayList(song1, song2, song3, song4, song5);
		
		//But this does not really set song name, artist name, it assigns object refs
//		ObservableList<AdditionalInfo> additionalInfo = FXCollections.observableArrayList(bindings.getAdditionalInfo(node));
		
		Collections.sort(songObjList);    

		System.out.print(songObjList);
		
		songListView.setItems(songObjList);
		
		songListView.getSelectionModel().select(0);
		
		//set listener 
		songListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> showItem(mainStage));
		}
	
	private void showItem(Stage mainStage) {
		int index = songListView.getSelectionModel().getSelectedIndex(); 
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
	}
	
	public void addSong(ActionEvent e) {
		//Button addSongBtn = (Button)e.getSource();
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
		
		
	}
	
	public boolean containsSong(final List<Song> list, final Song song){
	   for(int i = 0; i < list.size(); i++) {
		   if(list.get(i).songName.equals(song.songName) && list.get(i).artistName.equals(song.artistName)) {
			   return true; 
		   }
	   }
	   return false;
	}
}
