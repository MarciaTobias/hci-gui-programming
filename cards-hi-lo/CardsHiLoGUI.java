
// Standards javafx imports.
import javafx.application.Application;
import javafx.application.Platform;

// Import for alert, label, menu, insets
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.geometry.Insets;

// Imports componentes in the application
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

// Imports for images.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
// Imports for layout. 
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Imports to play sounds.
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class CardsHiLoGUI extends Application {

	// Menu to support opening file.
	MenuBar mBar;
	Menu mnuFile;
	MenuItem mnuItemNewGame;
	MenuItem mnuItemShuffle;
	MenuItem mnuItemExit;
	Menu mnuHelp;
	MenuItem mnuItemAbout;

	// Cards from class card.
	Card cardLeft;
	Card cardRight;

	// Images.
	Image dealLeft;
	Image dealRight;
	Image gif;

	// Where to show the images cards.
	ImageView imgViewLeft;
	ImageView imgViewRight;
	ImageView aboutGif;

	// Labels.
	Label lblFirstCard, lblSecondCard, lblNextCard, lblMessagesLeftSide, lblMessagesRightSide, lblProgressBar,
			lblCongratulations;

	// Radio buttons.
	RadioButton rbHigher, rbLower;

	// Toggle buttons.
	ToggleButton btnDealFirst, btnDealSecond;

	// Toggle group.
	ToggleGroup tgNextCard, tgDealCard;

	// Progress bar.
	ProgressBar progH;
	ProgressIndicator progInd;

	// Instantiate the deck of cards.
	DeckOfCards cards = new DeckOfCards();

	// ProgValue will receive the value of progress bar and progress indicator.
	double progValue;

	// Flag to the permit the user just to select the card at the left side first.
	boolean semaphore = true;

	// Sound shufling cards.
	String musicFileShuffle = "shuffling-cards-5.mp3";

	// Sound when we guess correctly.
	String win = "Oh-yeah-sound-effect.mp3";

	// Sound when we guess correctly.
	String congratulations = "applause-8.mp3";

	// Sound when is game over.
	String gameOver = "Sad-trombone-sound.mp3";

	public CardsHiLoGUI() {
	} // constructor

	@Override
	public void init() {

		// Components for the main menu.
		mBar = new MenuBar();
		mnuFile = new Menu("File");
		mnuItemNewGame = new MenuItem("New Game");
		mnuItemShuffle = new MenuItem("Shuffle");

		mnuItemExit = new MenuItem("Exit");
		mnuHelp = new Menu("Help");
		mnuItemAbout = new MenuItem("About");

		// Create the menu with items.
		mBar.getMenus().add(mnuFile);
		mnuFile.getItems().add(mnuItemNewGame);
		mnuFile.getItems().add(mnuItemShuffle);
		mnuFile.getItems().add(mnuItemExit);
		mBar.getMenus().add(mnuHelp);
		mnuHelp.getItems().add(mnuItemAbout);

		// Keys combinations for menu itens.
		mnuItemNewGame.setAccelerator(KeyCombination.keyCombination("ctrl + n"));
		mnuItemShuffle.setAccelerator(KeyCombination.keyCombination("ctrl + s"));
		mnuItemExit.setAccelerator(KeyCombination.keyCombination("ctrl + e"));
		mnuHelp.setAccelerator(KeyCombination.keyCombination("ctrl + h"));
		mnuItemAbout.setAccelerator(KeyCombination.keyCombination("ctrl + a"));

		// Instanciate card images with side back.
		dealLeft = new Image("back-side-left.jpg");
		dealRight = new Image("back-side-right.jpg");

		// Instanciate labels.
		lblFirstCard = new Label("First Card Dealt:");
		lblSecondCard = new Label("Second Card Dealt:");
		lblNextCard = new Label("Next card will be:");
		lblMessagesLeftSide = new Label();
		lblMessagesRightSide = new Label();
		lblCongratulations = new Label();

		// Radio Buttons.
		rbHigher = new RadioButton("Higher");
		rbLower = new RadioButton("Lower");

		// Radio buttons and deal second button will be disable by default.
		rbHigher.setDisable(true);
		rbLower.setDisable(true);

		// Toggle buttons.
		btnDealFirst = new ToggleButton("<- Deal First Card");
		btnDealSecond = new ToggleButton("Deal Second Card ->");

		// Toggle group
		tgNextCard = new ToggleGroup();
		tgDealCard = new ToggleGroup();

		// Set toggle group for radio buttons.
		rbHigher.setToggleGroup(tgNextCard);
		rbLower.setToggleGroup(tgNextCard);

		// Add toggle buttons in the group.
		btnDealFirst.setToggleGroup(tgDealCard);
		btnDealSecond.setToggleGroup(tgDealCard);

		// Buttons with the same size.
		btnDealFirst.setMinWidth(200);
		btnDealSecond.setMinWidth(200);

		// Prog value is the initial value of the progress bar and indicator.
		progValue = 0;

		// Progress bar.
		progH = new ProgressBar(progValue);
		progH.setMinWidth(160);
		progH.setMinHeight(30);

		// Progress indicator.
		progInd = new ProgressIndicator(progValue);

		// Handle events of buttons deal first.
		btnDealFirst.setOnAction(ae -> {

			if (semaphore == true) {
				semaphore = false;

				// It will show a message game over for the user, when the deck fineshed.
				if (cards.isEmpty()) {

					// Set the progress bar and progress indicator to 0, when we start a new game.
					progValue = 0;

					progH.setProgress(progValue);
					progInd.setProgress(progValue);

					// Disable radio buttons and deal buttons.
					btnDealFirst.setDisable(true);
					btnDealSecond.setDisable(true);
					rbHigher.setDisable(true);
					rbLower.setDisable(true);

					// Clean label at the right side
					lblMessagesRightSide.setText("");

					// Clean the label congratulations.
					lblCongratulations.setText("");

					// Plays the sound effect when all cards finishes.
					Media sound = new Media(new File(gameOver).toURI().toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.play();

					// Message for the user.
					lblMessagesLeftSide.setText("Game Over!!");

				} // if

				else {

					// Shows the card on the top of the stack.
					cardLeft = cards.dealTopCard();

					// Instanciate the object card and read the name of the file
					dealLeft = new Image(cardLeft.toString());

					// Instanciate the image.
					imgViewLeft.setImage(dealLeft);

					// Radio button are enable after the user select the card on the left side.
					rbHigher.setDisable(false);
					rbLower.setDisable(false);

					// Make the button second deal enable.
					btnDealSecond.setDisable(false);

					// Message for the user.
					lblMessagesLeftSide.setText("Higher or Lower?");

				} // else
			} // if semaphore
		}); // btnDealFirst

		// Handle events of buttons deal first.
		btnDealSecond.setOnAction(ae -> {

			// It will show a message game over for the user, when the deck fineshed.
			if (cards.isEmpty()) {

				// Set the progress bar and progress indicator to 0, when we start a new game.
				progValue = 0;

				progH.setProgress(progValue);
				progInd.setProgress(progValue);

				// Disable radio buttons and deal buttons.
				btnDealFirst.setDisable(true);
				btnDealSecond.setDisable(true);
				rbHigher.setDisable(true);
				rbLower.setDisable(true);

				// Clean label at the right side
				lblMessagesRightSide.setText("");

				// Clean the label congratulations.
				lblCongratulations.setText("");

				// Plays the sound effect when all cards finishes.
				Media sound = new Media(new File(gameOver).toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.play();

				// Message for the user.
				lblMessagesLeftSide.setText("Game Over!!");

			} // if

			else {

				if (semaphore == false) {
					semaphore = true;

					cardRight = cards.dealTopCard();

					// Instanciate the object card and read the name of the file
					dealRight = new Image(cardRight.toString());
					imgViewRight.setImage(dealRight);

					// If cards are equal it will be a draw, no poitns for the user.
					if (cardRight.rankIsEqualTo(cardLeft)) {

						// Value which will be for progress bar and indicator.
						progValue = 0;

						// Set the progress bar and indicator to 0.
						progH.setProgress(progValue);
						progInd.setProgress(progValue);

						// Shows message to the user.
						lblMessagesRightSide.setText("Draw, You lost");

						// Clean label at the left side
						lblMessagesLeftSide.setText("");

					} // if

					// If the card at the right side is lower than the card at the left side.
					else if (cardRight.rankIsLessThanCard(cardLeft)) {

						// and the lower radio button is selected.
						if (rbLower.isSelected()) {

							// Plays the sound effect when user win the game.
							Media sound = new Media(new File(win).toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(sound);
							mediaPlayer.play();

							// Increase it by 0.2.
							progValue = progValue + 0.2;

							// Set the value of the progress bar and progress indicator.
							progH.setProgress(progValue);
							progInd.setProgress(progValue);

							// Set a style. Show a red progress bar.
							progH.setStyle("-fx-accent: DARKSLATEBLUE;");
							progInd.setStyle("-fx-accent: DARKSLATEBLUE;");

							// Shows message to the user.
							lblMessagesRightSide.setText("Lower, You win!");

						} // radio button lower.

						else if (rbHigher.isSelected()) {

							// If the card at the left side is lower than the card at the right side, it

							// Make the progressBar and indicator with 0 value.
							progValue = 0;

							// Set the value of the progress bar and progress indicator.
							progH.setProgress(progValue);
							progInd.setProgress(progValue);

							// Shows message to the user.
							lblMessagesRightSide.setText("Lower, You lost");

						} // radio button higher.
					}

					// If the card at the right side is higher than the card at the left side.
					// will print a message for the user and disable the second button.
					else if (cardRight.rankIsGreatherThanCard(cardLeft)) {

						/// and the lower radio button is selected.
						if (rbLower.isSelected()) {

							// Make the progressBar and indicator with 0 value.
							progValue = 0;

							// Set the value of the progress bar and progress indicator.
							progH.setProgress(progValue);
							progInd.setProgress(progValue);

							// Shows message to the user.
							lblMessagesRightSide.setText("Higher, You lost!");

						} // radio button lower.

						// If the card at the left side is higher than the card at the right side, it
						// will print a message for the user and disable the second button.
						else if (rbHigher.isSelected()) {

							// Plays the sound effect when user win the game.
							Media sound = new Media(new File(win).toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(sound);
							mediaPlayer.play();

							// Increase it by 0.2.
							progValue = progValue + 0.2;

							// Set a style. Show a red progress bar.
							progH.setStyle("-fx-accent: DARKSLATEBLUE;");
							progInd.setStyle("-fx-accent: DARKSLATEBLUE;");

							// Set the value of the progress bar and progress indicator.
							progH.setProgress(progValue);
							progInd.setProgress(progValue);

							// Shows message to the user.
							lblMessagesRightSide.setText("Higher, You win!");

						} // radio button higher.

						btnDealSecond.setDisable(true);
					}

					// When user achieves 5 correct gesses.
					if (progValue == 1.0) {

						// Shows message to the user.
						lblCongratulations.setText("Congratulations, You win!");

						// Disable buttons to finish the game.
						rbHigher.setDisable(true);
						rbLower.setDisable(true);
						btnDealFirst.setDisable(true);
						btnDealSecond.setDisable(true);

						// Clean label at the left side
						lblMessagesLeftSide.setText("");

						// Clean label at the right side
						lblMessagesRightSide.setText("");

						// Plays the sound effect when user win the game.
						Media sound = new Media(new File(congratulations).toURI().toString());
						MediaPlayer mediaPlayer = new MediaPlayer(sound);
						mediaPlayer.play();
					}

					// Disable the second button
					btnDealSecond.setDisable(true);
				}
			}
		}); // BtnDealSecond.

		// Action for menu item shuffle.
		mnuItemShuffle.setOnAction(ae -> {

			// Plays the sound effect when shufle.
			Media sound = new Media(new File(musicFileShuffle).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();

			// Method to shuffle cards.
			cards.shuffle();

			// Message for the user, after the shuffled method.
			lblMessagesLeftSide.setText("Deck of Cards Shuffled!");

		});// shuffle item

		// Action for menu item exit.
		mnuItemExit.setOnAction(ae -> Platform.exit());

		// Action for the menu item about.
		mnuItemAbout.setOnAction(ae -> showAbout());

	}// init

	public void showAbout() {

		// Show information alert for the user about the system.
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("About Hi-Lo");
		alert.setHeaderText("Hi-lo V 1.0.0");
		alert.setContentText("\nHCI & GUI Programming" + "\nAssignment 2" + "\nCardsHiLoGUI' Card game application"
				+ "\nMarcia Tobias" + "\nstudent number: 2985044");

		// Show the message and wait for response from the user.
		alert.showAndWait();

	}// show about.

	@Override
	public void start(Stage pStage) throws Exception {

		// Get the dek of cards from the class deck of cards.
		cards.deckOfCards();

		// Shuffle the deck of cards.
		cards.shuffle();

		// Set the width and height.
		pStage.setWidth(585);
		pStage.setHeight(400);

		// Set the title.
		pStage.setTitle("Hi-Lo Card Game");

		// Create a layout.
		BorderPane bp = new BorderPane();

		GridPane gp = new GridPane();
		VBox vbButton = new VBox();
		HBox hbProgress = new HBox();

		// Instanciate images.
		imgViewLeft = new ImageView(dealLeft);
		imgViewRight = new ImageView(dealRight);

		// gp.setGridLinesVisible(true);

		// Add components to the layout.
		bp.setTop(mBar);

		// Add Grid Pane into Border Pane.
		bp.setCenter(gp);

		// Beautification.
		gp.setPadding(new Insets(10));
		gp.setHgap(15);
		gp.setVgap(10);

		// Group the radio buttons.
		vbButton.getChildren().addAll(lblNextCard, rbHigher, rbLower, btnDealFirst, btnDealSecond);
		vbButton.setSpacing(10);

		hbProgress.getChildren().addAll(progH, progInd);

		// Add componentes to the layout.
		gp.add(lblFirstCard, 1, 0);
		gp.add(imgViewLeft, 1, 1);
		gp.add(lblMessagesLeftSide, 1, 2);

		gp.add(vbButton, 2, 1);
		gp.add(hbProgress, 2, 3);
		gp.add(lblCongratulations, 2, 4);

		gp.add(lblSecondCard, 3, 0);
		gp.add(imgViewRight, 3, 1);
		gp.add(lblMessagesRightSide, 3, 2);

		// Make limits of the size of images.
		imgViewLeft.setFitHeight(193);
		imgViewLeft.setFitWidth(155);
		imgViewRight.setFitHeight(193);
		imgViewRight.setFitWidth(155);

		// Action for new ga item on menu.
		mnuItemNewGame.setOnAction(ae -> {

			// Close the stage.
			pStage.close();

			// Set the scene
			pStage.getScene();

			// Show new stage.
			pStage.show();

			// Get the dek of cards from the class deck of cards.
			cards.deckOfCards();

			// Shuffle the deck of cards.
			cards.shuffle();

			dealLeft = new Image("back-side-left.jpg");
			dealRight = new Image("back-side-right.jpg");

			// Instanciate images.
			imgViewLeft.setImage(dealLeft);
			imgViewRight.setImage(dealRight);

			// Set the progress bar and progress indicator to 0, when we start a new game.
			progValue = 0;

			progH.setProgress(progValue);
			progInd.setProgress(progValue);

			// Enable the first button to permits the user starts the game.
			btnDealFirst.setDisable(false);

			// Clean label at the left side
			lblMessagesLeftSide.setText("");

			// Clean label at the right side
			lblMessagesRightSide.setText("");

			// Clean the label congratulations.
			lblCongratulations.setText("");

		}); // newGame().

		// Create a scene.
		Scene s = new Scene(bp);

		// Add a stylesheet to the scene.
		s.getStylesheets().add("./progStyle.css");

		// Set the scene.
		pStage.setScene(s);

		// Show the stage.
		pStage.show();

	}// start

	public static void main(String[] args) {

		// Launch
		launch();

	}// main
}
