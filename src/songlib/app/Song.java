/**
 * @author Mazaya Rahman
 * @author Disha Bailoor
 *
 */

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
		if(this.songName.toLowerCase().compareTo(song.songName.toLowerCase()) == 0) {
			return this.artistName.toLowerCase().compareTo(song.artistName.toLowerCase());
		}
	    return this.songName.toLowerCase().compareTo(song.songName.toLowerCase());
	}
}
