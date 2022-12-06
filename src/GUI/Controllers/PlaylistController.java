package GUI.Controllers;

import BE.PlayList;
import GUI.Models.PlayListModel;
import GUI.Util.ErrorDisplayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

    @FXML
    private TextField textName;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    public TableView<PlayList> tbvPlayLists;

    private PlayListModel playListModel;
    private PlayList playList;
    private MainController mainController;
    private String playlistName;
    private Boolean createNewPlayList = false;

    public void setModel(PlayListModel playListModel){
        this.playListModel = playListModel;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (PlayListModel.getSelectedPlaylist() != null) {
            playList = PlayListModel.getSelectedPlaylist();
            textName.setText(playList.getTitle());
        }

        //Disable the OK button until there is new input in the text field.
        btnOK.setDisable(true);
        //Adding a listener, and enabling/disabling the OK button if name is empty
        textName.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!textName.getText().trim().isEmpty()) {
                    btnOK.setDisable(false);
                    playlistName = newValue;
                } else {
                    btnOK.setDisable(true);
                }
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });
    }

    public void handleOK() {
        try {
            //we edit the selected playlist.
            if (!createNewPlayList){

                //set the new title
                playList.setTitle(playlistName);
               //updates title in the db
                playListModel.updatePlayList(playList);
                //refreshes the GUI.
                //tbvPlayLists.refresh();

            }
            //we create a new playlist.
            else if (createNewPlayList){
                //we create a new playlist object.
                PlayList p = new PlayList(playlistName);
                //we insert our new playlist into the db.
                playListModel.createPlayList(p);
                //we do this, so we can edit a playlist if needed.
                createNewPlayList = false;
                //TODO find out why I cant just use refresh().
                //tbvPlayLists.setItems(playListModel.getObservablePlayLists());

            }
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }


        tbvPlayLists.refresh();
        //Closes the window.
        handleClose();
    }

    public void handleClose() {
        Stage stage = (Stage) textName.getScene().getWindow();
        stage.close();
    }
    public void setTbvPlayLists(TableView tableView){
        tbvPlayLists = tableView;
    }
    public void setCreateNewPlayList(boolean createNewPlayList){
        this.createNewPlayList = createNewPlayList;
    }
}
