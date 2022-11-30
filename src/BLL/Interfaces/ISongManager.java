package BLL.Interfaces;

import BE.Song;

import java.util.List;

public interface ISongManager {

    /**
     * Get all songs in the library
     * @return a list songs.
     * @throws Exception up the layers
     */
    List<Song> getAllSongs() throws Exception;

    /**
     * Filter the list of songs in library using a search query
     * @param query, the string input used to filter
     * @return a list of songs matching the query in either title, artist or category
     * @throws Exception up the layers
     */
    List<Song> search(String query) throws Exception;

}
