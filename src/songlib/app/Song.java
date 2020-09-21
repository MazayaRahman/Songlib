package songlib.app;

public class Song implements Comparable<Song>{
	
	public String songName;
	public String artistName;
	public String albumName;
	public String year;
	
	public Song(String name, String artist) {
		this.songName = name;
		this.artistName = artist;
		
	}
	
	public String toString() {
		return this.songName + " " + this.artistName; 
	}
	
	@Override
	public int compareTo(Song song) {
		if(this.songName.compareTo(song.songName) == 0) {
			return this.artistName.compareTo(song.artistName);
		}
	    return this.songName.compareTo(song.songName);
	}
}
