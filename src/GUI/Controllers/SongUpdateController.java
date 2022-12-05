package GUI.Controllers;

import BE.Song;
import GUI.Models.SongModel;
import GUI.Util.ErrorDisplayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SongUpdateController implements Initializable {

    private SongModel songModel;
    private Song song;
    @FXML
    private TextField textTitle, textArtist, textGenre;
    
    @FXML
    private Button btnOK, btnCancel;
    private boolean isTitleEmpty, isArtistEmpty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        song = SongModel.selectedSong;
        textTitle.setText(song.getTitle());
        textArtist.setText(song.getArtist());
        textGenre.setText(song.getGenre());

        //Adding a listener, and enabling/disabling the OK button if title is empty
        textTitle.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!textTitle.getText().trim().isEmpty()) {
                    btnOK.setDisable(false);
                } else {
                    btnOK.setDisable(true);
                }
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });

        //Adding a listener, and enabling/disabling the OK button if artist is empty
        textArtist.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                if(!textArtist.getText().trim().isEmpty()) {
                    btnOK.setDisable(false);
                } else {
                    btnOK.setDisable(true);
                }
            } catch (Exception e) {
                ErrorDisplayer.displayError(e);
            }
        });
    }

    /**
     * Set the model to the same model used in MainView.
     * @param songModel, a songModel object
     */
    public void setModel(SongModel songModel) {
        this.songModel = songModel;
    }

    /**
     * Changes the title, artist, and genre of the song to the new input once the user presses OK.
     * closes the window 
     */
    public void handleOK() {
        if (textTitle.getText().trim().isEmpty()) {
            ErrorDisplayer.displayError(new Exception("Title can not be empty"));
            return;
        }
        if (textArtist.getText().trim().isEmpty()) {
            ErrorDisplayer.displayError(new Exception("Artist can not be empty"));
            return;
        }

        //Set the title, artist, and genre to new input
        song.setTitle(textTitle.getText());
        song.setArtist(textArtist.getText());
        song.setGenre(textGenre.getText());


        try {
            songModel.updateSong(song); //Send the song down the layers to update it in the Database.
            songModel.search(""); //Refreshes the list shown to the user by simply searching an empty string.
        } catch (Exception e) {
            ErrorDisplayer.displayError(e);
        }

        handleClose();

    }

    /***
     * closes the window
     */
    public void handleClose() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
