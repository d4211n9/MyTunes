package DAL.DB;

import BE.PlayList;
import DAL.Interfaces.IPlaylistDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayListDAO_DB implements IPlaylistDAO {
    private DatabaseConnector databaseConnector;

    public PlayListDAO_DB(){
        databaseConnector = new DatabaseConnector();
    }


    public void createPlayList() {

    }


    public List<PlayList> getAllPlayLists() {

        try(Connection connection = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM Playlists;";

            Statement statement = connection.createStatement();
            ArrayList<PlayList> allPlayList = new ArrayList<>();


            ResultSet rs = statement.executeQuery(sql);

            // Loop through rows from database result set
            while(rs.next()){
                //map dp row to object
                int playListId = rs.getInt("Id");
                String title = rs.getString("Title");
                PlayList playList = new PlayList(playListId, title);
                allPlayList.add(playList);
            }
            return allPlayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void updatePlayList() {

    }


    public void deletePlayList() {

    }
}
