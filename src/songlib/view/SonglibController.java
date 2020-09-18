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
	
	@FXML ListView<Song> songListView;
	@FXML Button addSongBtn;
	@FXML Button editSongBtn;
	@FXML Button deleteSongBtn;
	@FXML TextArea songDetailView;
	
	private ObservableList<Song> songList; 
	
	public void init() {
		// create an ObservableList
		// from an ArrayList
		
		songList = FXCollections.observableArrayList(
		new Song("Dynamite", "BTS"),
		new Song("Ice Cream", "BlackPink"));

		//But this does not really set song name, artist name, it assigns object refs
		songListView.setItems(songList);
		
		}

}
