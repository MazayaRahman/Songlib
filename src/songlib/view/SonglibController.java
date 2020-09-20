package songlib.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import songlib.app.Song;

public class SonglibController {
	
	@FXML ListView<String> songListView;
	@FXML Button addSongBtn;
	@FXML Button editSongBtn;
	@FXML Button deleteSongBtn;
	@FXML TextArea songDetailView;
	
	private ObservableList<String> songList; 
	
	public void init() {
		// create an ObservableList
		// from an ArrayList
		
		/*
		 * songList = FXCollections.observableArrayList( new Song("Dynamite", "BTS"),
		 * new Song("Ice Cream", "BlackPink"));
		 */
		
		Song song1 = new Song("Dynamite", "BTS"); 
		Song song2 = new Song("Ice Cream", "Blackpink"); 
		
		songList = FXCollections.observableArrayList(song1.songName, song2.songName); 
		
		//But this does not really set song name, artist name, it assigns object refs
		//it does refer to song names now hehe
		songListView.setItems(songList);
		
		}

}
