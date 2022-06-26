import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Display extends Application implements EventHandler<MouseEvent> {
	
	private static Data data = new Data();
	private static int[] tempArray = data.temperatureData();
	private static String[] cityArray = data.getCities();
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 500;
	private static final int DATA_ELEMENTS_LIMIT = data.temperatureData().length;
	private static final int DATA_VALUE_LIMIT = WINDOW_HEIGHT/2;
	private static final int SPACE_BETWEEN_RECT = 2;
	private static final int RECT_WIDTH = (WINDOW_WIDTH - ((DATA_ELEMENTS_LIMIT-1)*SPACE_BETWEEN_RECT))/DATA_ELEMENTS_LIMIT;
	private static final int MENU_HEIGHT = 25;
	private static final int MENU_WIDTH = 100;
	private ArrayList<Integer> masterCopyArray = new ArrayList<Integer>(); //won't be changed
	private ArrayList<Rectangle> rectArray = new ArrayList<Rectangle>();
	private Timer timer;
	//private Group root;
	
	private Rectangle rect1;
	private Text info = new Text();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	private class Timer extends AnimationTimer {

		public void handle(long arg0) { //the timer's handle method
			
			int i = 0;
			for (Rectangle elem: rectArray) { //for loop didnt work so for-each loop with a counter
				// rect1 = rectArray.get(i);
				rect1 = elem;
				info = new Text();
				info.setText(cityArray[i] + "; " + tempArray[i] + "F");
				info.setStyle("-fx-fill: #000000");
				info.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
				
				//info.setVisible(false);
				rect1.setOnMouseEntered((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
					public void handle(MouseEvent mouseEvent) {
//						info.setX((int)mouseEvent.getX());
//						info.setY((int)mouseEvent.getY());
						info.setX(elem.getX());
						info.setY(elem.getY());
						elem.setStyle("-fx-fill: #bbbbbb");
						info.setVisible(true);
						System.out.println("mouse entered");
					}
				});
				
				rect1.setOnMouseExited((EventHandler<MouseEvent>) new EventHandler<MouseEvent>() {
					public void handle(MouseEvent mouseEvent) {
						info.setVisible(false);
						elem.setStyle("-fx-fill: #32b10a");
						System.out.println("mouse exited");
					}
				});
				i++;
			}
			
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("EnviroSort");
		stage.setResizable(false);
		
		Group root = new Group(); //-------CHANGE IF IT CRASHES HEHEHE--------//
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		stage.show();
		
		for (int i = 0; i < DATA_ELEMENTS_LIMIT; i++) {
			//masterCopyArray.add((int)(Math.random()*(DATA_VALUE_LIMIT)+1)); 
			masterCopyArray.add(tempArray[i]);
		}
		
		for (int elem: masterCopyArray) {
			Rectangle rect = new Rectangle(RECT_WIDTH, elem*3);
			rect.setStyle("-fx-fill: #32b10a");
			rectArray.add(rect);
		}
		
		int x = SPACE_BETWEEN_RECT/2;
		int y = WINDOW_HEIGHT - 10;
		for (Rectangle elem: rectArray) {
			elem.setX(x);
			x += SPACE_BETWEEN_RECT + RECT_WIDTH;
			elem.setY(y - elem.getHeight());
			root.getChildren().addAll(elem);
		}
		
		timer = new Timer();
		timer.start();
		//root.getChildren().addAll(info);
		
		Button sort = new Button("Sort!");
		sort.setPrefWidth(MENU_WIDTH); 
		sort.setPrefHeight(MENU_HEIGHT);
		sort.setTranslateX(WINDOW_WIDTH/2 - MENU_WIDTH/2);
		sort.setTranslateY(MENU_HEIGHT);
		root.getChildren().add(sort);
		
//		Button randomize = new Button("Randomize!");
//		randomize.setPrefWidth(MENU_WIDTH); 
//		randomize.setPrefHeight(MENU_HEIGHT);
//		randomize.setTranslateX(WINDOW_WIDTH - 3*MENU_WIDTH/2);
//		randomize.setTranslateY(MENU_HEIGHT*4);
//		root.getChildren().add(randomize);
//		
//		
//		randomize.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
//		    @Override 
//		    public void handle(ActionEvent e) {
//		    	randomizeButtonClicked();
//		    	for (Rectangle elem: rectArray) root.getChildren().add(elem);
//		    }
//		});

		
		//what happens if the sort button is clicked
		sort.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	sortButtonClicked();
		    	
		    	int x = SPACE_BETWEEN_RECT/2;
				int y = WINDOW_HEIGHT - 10;
				for (Rectangle elem: rectArray) {
					elem.setX(x);
					x += SPACE_BETWEEN_RECT + RECT_WIDTH;
					elem.setY(y - elem.getHeight());
					root.getChildren().addAll(elem);
				}
		    }
		});
		

		stage.setScene(scene);
		stage.show();
	
	}
	
	public void sortButtonClicked() {
    	ArrayList<Integer> ints = masterCopyArray; 
		int n = ints.size();
		for (int i = 0; i < n-1; i++) {
	            // Find the minimum element in unsorted array
			int min_idx = i;
	        for (int j = i+1; j < n; j++)
	        	if (ints.get(j) < ints.get(min_idx))
	        		min_idx = j;
	  
	            // Swap the found minimum element with the first
	            // element
	        int temp = ints.get(min_idx);
	        ints.set(min_idx, ints.get(i));
	        ints.set(i, temp);
	    }
    	//if (menuChoice != null) {
    		//System.out.println("1");
    		
//    		if (menuChoice == null) {
//    			System.out.println("choice is null");
//    		} else if (menuChoice.equals("Selection Sort")) {
//        		newArr = sorter.selectionSort(masterCopyArray);
//        	} else if (menuChoice.equals("Gnome Sort")) {
//        		newArr = sorter.gnomeSort(masterCopyArray);
//        	} else if (menuChoice.equals("Bubble Sort")) {
//        		newArr = sorter.bubbleSort(masterCopyArray);
//        	}
    		
        	for (Rectangle elem: rectArray) elem.setVisible(false);
        	for (int i = 0; i < ints.size(); i++) {
    			Rectangle rect = new Rectangle(RECT_WIDTH, ints.get(i)*3);
    			rect.setStyle("-fx-fill: #32b10a");
    			rectArray.set(i, rect);
    		}
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
		
		
	}
	
	
	/*
	public void randomizeButtonClicked() {
		
		for (int i = 0; i < DATA_ELEMENTS_LIMIT; i++) {
			rectArray.get(i).setVisible(false);
			masterCopyArray.set(i, (int)(Math.random()*(DATA_VALUE_LIMIT)+1)); 
			rectArray.set(i, new Rectangle(RECT_WIDTH, masterCopyArray.get(i)));
			rectArray.get(i).setVisible(true);
			int x = SPACE_BETWEEN_RECT/2;
			int y = WINDOW_HEIGHT - 10;
			for (Rectangle elem: rectArray) {
				elem.setX(x);
				elem.setStyle("-fx-fill: #32b10a");
				x += SPACE_BETWEEN_RECT + RECT_WIDTH;
				elem.setY(y - elem.getHeight());
				//root.getChildren().addAll(elem);
			}
			//reassigning random numbers in master copy array
		}
		
	}
	*/

}






