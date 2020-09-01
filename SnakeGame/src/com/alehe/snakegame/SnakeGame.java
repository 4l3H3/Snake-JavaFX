package com.alehe.snakegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SnakeGame extends Application {
	private int playersize = 12;
	private int width = playersize * 20;
	private int height = playersize * 20;
	// private int score = 0;
	private int speed = 75;

	private Random random = new Random();

	private Player playerhead;
	private List<Player> playerbody = new ArrayList<Player>();
	private Rectangle fruit;

	private Pane root;
	private Label gameoverlabel = new Label("Game Over");

	private boolean[] moveWASD = { false, false, false, false };
	private boolean gameover = false;

	private void increasePlayerBody() {
		if (playerhead.getLastMove() != null) {
			Player tail = playerbody.get(playerbody.size() - 1);
			switch (tail.getLastMove()) {
			case "up":
				playerbody.add(new Player(playersize, (int) tail.getLayoutX(), (int) tail.getLayoutY() + playersize));
				break;
			case "right":
				playerbody.add(new Player(playersize, (int) tail.getLayoutX() - playersize, (int) tail.getLayoutY()));
				break;
			case "left":
				playerbody.add(new Player(playersize, (int) tail.getLayoutX() + playersize, (int) tail.getLayoutY()));
				break;
			case "down":
				playerbody.add(new Player(playersize, (int) tail.getLayoutX(), (int) tail.getLayoutY() - playersize));
				break;
			// ..
			default:
				break;
			}
			root.getChildren().add(playerbody.get(playerbody.size() - 1));
		}
	}

	private void movePlayerBody() {
		if (playerhead.getPenultimateMove() != null && playerbody.size() > 1) {
			for (int i = 1; i < playerbody.size(); i++) {
				switch (playerbody.get(i - 1).getPenultimateMove()) {
				case "up":
					playerbody.get(i).moveUp();
					break;
				case "left":
					playerbody.get(i).moveLeft();
					break;
				case "down":
					playerbody.get(i).moveDown();
					break;
				case "right":
					playerbody.get(i).moveRight();
					break;
				default:
					break;
				}
			}

		}
	}

	private void updateFruit() {
		root.getChildren().remove(fruit);
		fruit = new Rectangle(playersize, playersize, Color.RED);
		fruit.setLayoutX(random.nextInt(19) * 12);
		fruit.setLayoutY(random.nextInt(19) * 12);
		root.getChildren().add(fruit);
	}

	private void checkBodyCollision() {
		for (int i = 1; i < playerbody.size(); i++)
			if (playerhead.getLayoutX() == playerbody.get(i).getLayoutX()
					&& playerhead.getLayoutY() == playerbody.get(i).getLayoutY()) {
				gameover = true;
				root.getChildren().add(gameoverlabel);
			}
	}

	private void checkInBound() {
		if (!(-0.1 < playerhead.getLayoutX() && playerhead.getLayoutX() < width && -0.1 < playerhead.getLayoutY()
				&& playerhead.getLayoutY() < height)) {
			gameover = true;
			root.getChildren().add(gameoverlabel);
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	private void update() {
		if (!gameover) {
			if (moveWASD[0])
				playerhead.moveUp();
			else if (moveWASD[1])
				playerhead.moveLeft();
			else if (moveWASD[2])
				playerhead.moveDown();
			else if (moveWASD[3])
				playerhead.moveRight();

			checkBodyCollision();
			movePlayerBody();
			if (playerhead.getLayoutX() == fruit.getLayoutX() && playerhead.getLayoutY() == fruit.getLayoutY()) {
				updateFruit();
				increasePlayerBody();
			}
			checkInBound();

			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start(Stage primaryStage) throws Exception {
		Stage window = new Stage();
		root = new Pane();
		gameoverlabel.setStyle("-fx-font-family: samic-sans; -fx-text-fill: grey; -fx-font-size: 20px;");
		gameoverlabel.setLayoutX(width / 3);
		gameoverlabel.setLayoutY(height / 2);
		playerhead = new Player(playersize, playersize * 8, playersize * 8);
		fruit = new Rectangle(playersize, playersize, Color.RED);
		fruit.setLayoutX(random.nextInt(19) * 12);
		fruit.setLayoutY(random.nextInt(19) * 12);
		playerbody.add(playerhead);

		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				update();
			}
		};

		root.getChildren().addAll(playerbody);
		root.getChildren().add(fruit);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Snake");
		window.setMinWidth(width);
		window.setMinHeight(height);

		Scene scene = new Scene(root, height, width);

		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				moveWASD = new boolean[] { false, true, false, false };
				break;
			case S:
				moveWASD = new boolean[] { false, false, true, false };
				break;
			case D:
				moveWASD = new boolean[] { false, false, false, true };
				break;
			case W:
				moveWASD = new boolean[] { true, false, false, false };
				break;
			default:
				break;
			}
		});
		window.setScene(scene);
		window.setResizable(false);
		timer.start();
		window.show();

	}
}
