/*
This class is made as the back end of the whole user interface and where the logical decisions are made and applying the changes
of course in the game window
 */


package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Controller {
    object1 infoTransferred = new object1();//This is the object that we are going to use for Data transfer in the logical side
    public void levelSelection(GridPane gridPane,String level) throws FileNotFoundException {
        //Here we threw an exception if the  file wasn't found and i am using my one directory but the catch is that
        //we are using an arbitrary value that changes with the parameter level
        File file = new File(String.format("C:\\Users\\lenah\\Desktop\\levels\\level%s.txt",level));
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {//This loop goes through each line ad then checks with every id if its a match it changes the id and the CSS style
            String fullLine = scanner.nextLine();
            String[] lines = fullLine.split(",");
            for (Node n : gridPane.getChildren())
            {
                String nodeId = n.getId();
                String[] fullNodeId = nodeId.split(",");
                if (lines[1].equals(fullNodeId[0]) && (lines[2].equals(fullNodeId[1])))
                {
                    n.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + lines[0]);
                    if (lines[0].equals("Empty")) {
                        n.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                        n.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                        break;
                    }
                    else if (lines[0].equals("Mirror")) {
                        n.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                        n.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                        break;
                    }
                    else{
                            n.setStyle("-fx-border-color: deeppink ; -fx-background-color: hotpink  ; -fx-border-width: 2.5");
                            n.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Wood" + "," + fullNodeId[3] + "," + "3");
                            break;
                        }
                }
            }
        }
        scanner.close();
    }
    public int gameFunc(GridPane gridPane,Node node)
    {//This is mostly the biggest function because there are tons of exceptions if the box was on the right we have to make
        //three hits but if the position was 9 then we have to change the left and the one beneath
        infoTransferred.score = 0;
        int newNodeLocation = 0,newNodeLocation2 = 0,score = 0;
        String[] fullNodeId = node.getId().split(",");
        int boxLocation = Integer.parseInt(fullNodeId[3]);//This box location will be used through every exception for the position of the box in 1D
        if (fullNodeId[2].equals("Wall") || fullNodeId[2].equals("Empty"))//Now first we check if they are empty or a wall and so we return 0
            return 0;
        else if (fullNodeId[2].equals("Wood"))//Then we check if its wood
        {
            if (boxLocation == 0)//because a zero will hit right and beneath only we put it at first
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1;//here we give where the other potential gits will be
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 10;
                for (Node n : gridPane.getChildren())
                {
                    fullNodeId = n.getId().split(",");
                    if (fullNodeId[3].equals("1"))
                    {
                        score += getScore(fullNodeId, n);

                    }
                    else if (fullNodeId[3].equals("11"))
                    {
                        score += getScore(fullNodeId, n);//Here we use the get score method to get the overall score
                        // and check the other boxes and change their colours and Ids
                    }
                }
                infoTransferred.score = scoreCount(score);//here we get the overall score to be transferred


            }
            else if (boxLocation == 9)//same as zero
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score+=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);

            }
            else if (boxLocation == 90)
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score+=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                infoTransferred.score += scoreCount(score);
                return scoreCount(score);
            }
            else if (boxLocation == 99)
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score+=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 10;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if (((boxLocation/10) == 0) && (0<boxLocation) && (boxLocation>9))//This one covers the top boxes
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if ((boxLocation % 10) == 0)// This one covers the left side boxes
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 10;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if (boxLocation == 19 || boxLocation == 29 || boxLocation == 39 || boxLocation == 49 || boxLocation == 59
            || boxLocation == 69 || boxLocation  == 79 || boxLocation == 89)
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 10;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if ((boxLocation % 91) > 0 && (boxLocation % 91) < 8)// This one covers the bottom boxes
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) - 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else//This covers the boxes in the middle
            {
                node.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) - 10;
                int newNodeLocation4 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                score += getScore1(gridPane,newNodeLocation4);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }


        }
        else// if the box is a Mirror
        {
            if (boxLocation == 0)// Same as the above
            {

                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 10;
                for (Node n : gridPane.getChildren())
                {
                    fullNodeId = n.getId().split(",");
                    if (fullNodeId[3].equals("1"))
                    {
                        score += getScore(fullNodeId, n);

                    }
                    else if (fullNodeId[3].equals("11"))
                    {
                        score += getScore(fullNodeId, n);
                    }
                }
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if (boxLocation == 9)
            {

                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score+=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);

            }
            else if (boxLocation == 90)
            {

                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score+=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if (boxLocation == 99)
            {

                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score+=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 10;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if (((boxLocation/10) == 0) && (0<boxLocation) && (boxLocation>9))
            {

                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if ((boxLocation % 10) == 0)
            {
                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 10;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) + 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if (boxLocation == 19 || boxLocation == 29 || boxLocation == 39 || boxLocation == 49 || boxLocation == 59
                    || boxLocation == 69 || boxLocation  == 79 || boxLocation == 89)
            {
                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) - 10;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else if ((boxLocation % 91) >= 0 && (boxLocation % 91) < 8)
            {
                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) - 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }
            else
            {
                node.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
                node.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
                infoTransferred.locations.add(fullNodeId[3]);
                score +=1;
                newNodeLocation = Integer.parseInt(fullNodeId[3]) + 1 ;
                newNodeLocation2 = Integer.parseInt(fullNodeId[3]) - 1;
                int newNodeLocation3 = Integer.parseInt(fullNodeId[3]) - 10;
                int newNodeLocation4 = Integer.parseInt(fullNodeId[3]) + 10;
                score += getScore1(gridPane, newNodeLocation);
                score += getScore1(gridPane, newNodeLocation2);
                score += getScore1(gridPane,newNodeLocation3);
                score += getScore1(gridPane,newNodeLocation4);
                infoTransferred.score = scoreCount(score);
                return scoreCount(score);
            }


        }
        return score;


    }

    public int getScore1(GridPane gridPane, int newNodeLocation) {//This function makes sure if the box was hit or not
        String[] fullNodeId;
        for (Node n : gridPane.getChildren())
        {
            String newNodeLocationId = Integer.toString(newNodeLocation);
            fullNodeId = n.getId().split(",");
            if (fullNodeId[3].equals(newNodeLocationId))
            {
                int score= getScore(fullNodeId,n);
                if (score == 1){
                    return 1;}
                else {
                    return 0;

                }
            }

        }
        return 0;
    }

    public int getScore(String[] fullNodeId, Node n) {// This function covers each box if it was hit or not and send its position to the locations arraylist

        if (fullNodeId[4].equals("3"))
        {
            n.setStyle("-fx-border-color: deepskyblue ; -fx-background-color: cyan  ; -fx-border-width: 2.5");
            n.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Mirror" + "," + fullNodeId[3] + "," + "2");
            infoTransferred.locations.add(fullNodeId[3]);
            return 1;
        }
        else if (fullNodeId[4].equals("2"))
        {
            n.setStyle("-fx-border-color: silver ; -fx-background-color: White  ; -fx-border-width: 2.5");
            n.setId(fullNodeId[0] + "," + fullNodeId[1] + "," + "Empty" + "," + fullNodeId[3] + "," + "1");
            infoTransferred.locations.add(fullNodeId[3]);
            return 1;
        }

        else
        {

            return 0;
        }

    }
    public int scoreCount(int score)//This here counts how many hits where made from the user click
    {
        if (score == 1)
            return -3;
        else if (score == 2)
            return -1;
        else if (score == 3)
            return 1;
        else if (score == 4)
            return 2;
        else if (score == 5)
            return 4;
        return 0;
    }
    public void finalScore(Label label, int object)//This function updates the score label every click
    {
        int oldScore = Integer.parseInt(label.getText());
        int newScore = object + oldScore;
        label.setText(Integer.toString(newScore));
    }
    public void setNewHighScore(Label score,Label highScore,GridPane gridPane)//This function checks if the user have beaten the high score or not
    {
        int counter = 0;
        for (Node node : gridPane.getChildren())
        {
            String[] fullNodeId = node.getId().split(",");
            if (fullNodeId[2].equals("Mirror") || fullNodeId[2].equals("Wood"))
            {
                break;
            }
            else counter++;

        }
        if (counter == 100)
    {
        String[] highScoreSplit = highScore.getText().split(":");
        if (Integer.parseInt(score.getText()) > Integer.parseInt( highScoreSplit[1])) {
            highScore.setText("High Score:" + score.getText());
        }
    }

    }
    public void showHit(Label bottomLabel,GridPane gridPane)//This program updates the bottom label every click from the user
    {
        int firstHit = 0;
        String[] fullNodeId;
        String newString = "",score ="";

        for (String string : infoTransferred.locations)
        {
            for(Node node1:gridPane.getChildren())
            {
                fullNodeId = node1.getId().split(",");
                if (firstHit == 0)
                {
                    if (fullNodeId[3].equals(string))
                    {
                        newString = "Box:" + fullNodeId[0]+ "-" +fullNodeId[1];
                        firstHit++;
                    }
                }
                else
                    if (fullNodeId[3].equals(string))
                {
                    newString = newString.concat(" - " +"Hit:" + fullNodeId[0] + "," + fullNodeId[1]);
                    firstHit++;
                }

            }
        }
        if (firstHit == 1)
            score = "-3";
        else if (firstHit == 2)
            score = "-1";
        else if (firstHit == 3)
            score = "+1";
        else if (firstHit == 4)
            score = "+2";
        else if (firstHit == 5)
            score = "+4";
        newString = newString.concat("(" + score + ")");
        bottomLabel.setText(newString);

    }
    public void nextLevel(BorderPane bottomPane,Label bottomLabel,GridPane gridPane,Label level,Label score)
    {//This function checks every time if all the boxs are either Empty or a Wall type and if they are proceeds to the next level
        int counter = 0;
        Button button = new Button("Next Level >>");
        button.setStyle("-fx-background-color: transparent ; -fx-border-color: transparent");
        for (Node node : gridPane.getChildren())
        {
            String[] fullNodeId = node.getId().split(",");
            if (fullNodeId[2].equals("Wood") || fullNodeId[2].equals("Mirror"))
                break;
            else counter++;
        }
        if (counter == 100)
        {
            String[] levelSplit = level.getText().split("#");
            if (Integer.parseInt(levelSplit[1]) < 5) {//Here we check if the user finished all the levels or not
                int newLevel = Integer.parseInt(levelSplit[1]) + 1;
                EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {//here we check if the next level file is found or not
                        try {
                            score.setText("0");
                            level.setText(levelSplit[0] + "#" + Integer.toString(newLevel));
                            levelSelection(gridPane, Integer.toString(newLevel));
                            bottomLabel.setText("---Text---");
                            bottomPane.setRight(null);
                        } catch (FileNotFoundException e) {
                            bottomLabel.setText("Sorry Couldn't find the next level txt file");

                        }

                    }
                };button.setOnAction(eventHandler);
            }
            else {//if they did we inform that they finished all the levels
                bottomPane.setRight(null);
                bottomLabel.setText("There are no other levels Congrats for finishing our game Hope you like it :)");
            }
            bottomPane.setRight(button);
        }
    }
}