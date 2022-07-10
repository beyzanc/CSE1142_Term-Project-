//student names : Abdulaziz Alftaieh and Beyzanur Çabuk
/*
This main.java is mainly about the UI setup for the user where we first created the top pane which was a BorderPane
and inserted 3 labels after creating them then we created a Gridpane so that we can create 10x10 Dimension array which
contained the buttons then we set up an id for each button and they are (row , column , type of box, the position of each
button in 1D array , and an id for each box type) respectively and the reason for the position in 1D its because it will
help us keep track of the boxes
 */

package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    Button button;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        int counter = 0;
        Controller controller = new Controller();// here we created an object just to transfer the information between the backend and UI
        primaryStage.setTitle("Game BOX");
        Label levelInfo = new Label("Level#1");
        Label score = new Label("0");
        Label highScore = new Label("High Score:3");//these labels were made based on how they were made in the project pdf file

        BorderPane topPane = new BorderPane();//this border pane will contain the previous labels
        topPane.setLeft(levelInfo);//Here we are setting each label to its desired location
        topPane.setCenter(score);
        topPane.setRight(highScore);
        GridPane centerPane = new GridPane();//This center pane will contain all 100 boxes (buttons)
        Label bottomLabel = new Label("---Text---");//This label will give info and at first it will be set to ---Text---
        BorderPane bottomPane = new BorderPane();
        bottomPane.setLeft(bottomLabel);
        for(int i = 0;i<10;i++)//This loop is created to save time and not writing the same lines and it will return a 10x10
        {
            for(int j  = 0;j<10;j++)
            {
                //Tese are the desired attributes for every button
                button = new Button();
                button.setStyle("-fx-border-color: dimgrey ; -fx-background-color: grey  ; -fx-border-width: 2.5");
                button.setPrefSize(200,200);
                button.setMinSize(40,40);
                button.setId(Integer.toString(j)+ "," + Integer.toString(i) + "," + "Wall" + "," + Integer.toString(counter) + "," + "0" );
                EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        controller.gameFunc(centerPane, (Node) actionEvent.getSource());
                        controller.finalScore(score,controller.infoTransferred.score);
                        controller.showHit(bottomLabel,centerPane);
                        controller.infoTransferred.locations.clear();//we clear the old strings so that it doesn't get mixed up with new ones
                        controller.setNewHighScore(score,highScore,centerPane);
                        controller.nextLevel(bottomPane,bottomLabel,centerPane,levelInfo,score);
                        //Here we have created an Event where all of these functions will be used for the game to run smoothly
                    }
                };
                counter+=1;
                button.setOnAction(event);
                centerPane.add(button,i,j);

            }
        }

        controller.levelSelection(centerPane,"1");//here of course we have to start with the first level by using the
                                                        //defined function levelSelection in the Controller class
        BorderPane mainWindow = new BorderPane();
        mainWindow.setTop(topPane);
        mainWindow.setCenter(centerPane);
        mainWindow.setBottom(bottomPane);
        Scene scene = new Scene(mainWindow,550,500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
