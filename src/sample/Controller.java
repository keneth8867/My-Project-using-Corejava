package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;

public class Controller {
    MediaPlayer P1;

    @FXML
    private Slider timeslider;

    @FXML
    private MediaView mediaview;

    @FXML
    private Button playbtn1;


    //    @FXML
//    void OpenSongMenu(ActionEvent event)
//    {
//        System.out.println("clicked open media");
//        try {
//
//        FileChooser fc= new FileChooser();
//        //here mentioning null so that it should show center
//        File file=fc.showOpenDialog(null);
//
//        Media m= new Media(file.toURI().toURL().toString());
//
//        P1=new MediaPlayer(m);
//        mediaview.setMediaPlayer(P1);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//}

    public void OpenSongMenu(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked open media");

        try {

            FileChooser fc= new FileChooser();
            //here mentioning null so that it should show center
            File file=fc.showOpenDialog(null);

            Media m= new Media(file.toURI().toURL().toString());
             if(P1 != null)
             {
                 P1.dispose();
             }
            P1=new MediaPlayer(m);
            mediaview.setMediaPlayer(P1);

            //timeslider
            P1.setOnReady(()->{
                 //when player gets ready..
                timeslider.setMin(0);
                timeslider.setMax(P1.getMedia().getDuration().toMinutes());
                timeslider.setValue(0);
                playbtn1.setText("Play");

            });

            //Listener on player..
            P1.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    // coding..
                    Duration d = P1.getCurrentTime();
                    timeslider.setValue(d.toMinutes());
                }
            });

            //time slider dragged gets video forward
            timeslider.valueProperty().addListener(new ChangeListener<Number>() {
                //whenever slider is dragged this Changed function is called
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                  //in this function whereever the slider is dragged i am going to fetch that time
                   if(timeslider.isPressed()) {
                       double val = timeslider.getValue();
                       P1.seek(new Duration(val * 60 * 1000));
                   }
                }
            });


        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
//    @FXML
//    void play(ActionEvent event) {
//        P1.play();
//
//    }
//
  public void play(ActionEvent actionEvent) {
    MediaPlayer.Status status=P1.getStatus();

    if(status==MediaPlayer.Status.PLAYING)
    {
        //if media is already playing video we will pause it.
        P1.pause();
        playbtn1.setText("Play");
    }
    else
    {


        P1.play();
        playbtn1.setText("Pause");


    }
  }


    public void Prevbtnclick(ActionEvent actionEvent) {
        double d =P1.getCurrentTime().toSeconds();
        d=d-10;
        P1.seek(new Duration(d*1000) );
    }

    public void nextbtnclick(ActionEvent actionEvent) {
        double d =P1.getCurrentTime().toSeconds();
        d=d+10;
        P1.seek(new Duration(d*1000) );
    }
}
