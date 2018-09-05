
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;

public class Driver extends Application{

     private ArrayList<Item> list;

     private TextArea output;
     private TextField input;

     public void start(Stage stage){
          stage.setTitle("Item Randomnizer");

          Label guide = new Label(" To create a store, either hit enter "
          + " for a completely random list, \n or type in a seed to make a list"
          + " that you can come back to later.\n\n");
          output = new TextArea();
          output.setEditable(false);
          output.setPrefRowCount(15);
          input = new TextField();
          Label footnote = new Label("  HAPPY DUNGEONEERING");

          VBox root = new VBox();
          root.getChildren().add(guide);
          root.getChildren().add(input);
          root.getChildren().add(output);
          root.getChildren().add(footnote);

          readItemFile();

          input.setOnKeyPressed(e ->{
               if(e.getCode() == KeyCode.ENTER){
                    if(input.getText().matches(".+")){
                         String inputStr = input.getText();
                         setStore(generateStore(inputStr.substring(4, inputStr.length())));
                    } else if(input.getText().matches("")){
                         setStore(generateStore(null));
                    }
               }
          });

          stage.setScene(new Scene(root, 500, 400));
          stage.show();
     }

     private void readItemFile(){
          BufferedReader reader = null;
          try{
               reader = new BufferedReader(new FileReader("equiplist.txt"));
          } catch(IOException e){
               e.printStackTrace();
          }

          //parse all the items out:
          list = new ArrayList<>();

          String item_raw = "";
          do{
               try{
                    item_raw = reader.readLine();
                    //System.out.println(item_raw);
               } catch(IOException e){
                    e.printStackTrace();
               }

               if(item_raw != null && !item_raw.matches("[\n\r]")){
                    list.add(new Item(item_raw));
               }
          }while(item_raw != null);
     }

     public ArrayList<Item> generateStore(String seed){

          Random gen;

          if(seed == null){
               gen = new Random();
          } else {
               long longSeed = 0;
               for(int i = 0; i < seed.length(); i++){
                    longSeed += (long) seed.charAt(i);
               }
               gen = new Random(longSeed);
          }

          ArrayList<Item> store = new ArrayList<>();
          int numItems = gen.nextInt(10) + 5;
          int index = 0;

          for(int i = 0; i < numItems; i++){
               index = gen.nextInt(list.size());
               store.add(list.get(index));
          }

          return store;
     }

     public void setStore(ArrayList<Item> storeList){
          String store = "";
          for(int i = 0; i < storeList.size(); i++){
               store += (storeList.get(i) + "\n");
          }
          output.setText(store);
     }

     public static void main(String[] args){
          launch(args);
     }
}
