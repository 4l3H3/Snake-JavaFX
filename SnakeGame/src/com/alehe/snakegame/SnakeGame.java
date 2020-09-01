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
import javafx.stage.Stage;

public class SnakeGame extends Application {
	private int playersize = 30;
	private int width = playersize * 20;
	private int height = playersize * 20;
	private int score = 0;
	private int speed = 75;
	private boolean gameover = false;

	private Random random = new Random();

	private Player playerhead;
	private List<Player> playerbody = new ArrayList<Player>();
	private Direction playerheaddirection = Direction.NONE;

	private Rectangle fruit;

	private Pane root;
	private Label gameoverlabel = new Label("Game Over");
	private Label scorelabel = new Label(Integer.toString(score));

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		Stage window = new Stage();
		root = new Pane();

		gameoverlabel.setStyle("-fx-font-family: samic-sans; -fx-text-fill: grey; -fx-font-size: 20px;");
		gameoverlabel.setLayoutX(width / 3);
		gameoverlabel.setLayoutY(height / 2);

		scorelabel.setStyle("-fx-font-family: samic-sans; -fx-text-fill: grey; -fx-font-size: 20px;");
		scorelabel.setLayoutX(width / 2);
		scorelabel.setLayoutY(0);

		playerhead = new Player(playersize, playersize * 8, playersize * 8, Color.DARKGREEN);
		playerbody.add(playerhead);

		fruit = new Rectangle(playersize, playersize, Color.RED);
		fruit.setLayoutX(random.nextInt(19) * playersize);
		fruit.setLayoutY(random.nextInt(19) * playersize);

		root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				update();
			}
		};

		root.getChildren().addAll(playerbody);
		root.getChildren().addAll(fruit, scorelabel);

		window.setTitle("Snake");
		window.setMinWidth(width);
		window.setMinHeight(height);

		Scene scene = new Scene(root, height, width);

		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				playerheaddirection = Direction.LEFT;
				break;
			case S:
				playerheaddirection = Direction.DOWN;
				break;
			case D:
				playerheaddirection = Direction.RIGHT;
				break;
			case W:
				playerheaddirection = Direction.UP;
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

	private void update() {
		if (!gameover) {
			switch (playerheaddirection) {
			case UP:
				playerhead.moveUp();
				break;
			case RIGHT:
				playerhead.moveRight();
				break;
			case LEFT:
				playerhead.moveLeft();
				break;
			case DOWN:
				playerhead.moveDown();
				break;
			default:
				break;
			}

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

	/*
	 * Adds a piece to the playerbody based on the opposite direction the tail last
	 * went
	 */
	private void increasePlayerBody() {
		Player tail = playerbody.get(playerbody.size() - 1);
		switch (tail.getLastDirection()) {
		case UP:
			playerbody.add(
					new Player(playersize, (int) tail.getLayoutX(), (int) tail.getLayoutY() + playersize, Color.GREEN));
			break;
		case RIGHT:
			playerbody.add(
					new Player(playersize, (int) tail.getLayoutX() - playersize, (int) tail.getLayoutY(), Color.GREEN));
			break;
		case LEFT:
			playerbody.add(
					new Player(playersize, (int) tail.getLayoutX() + playersize, (int) tail.getLayoutY(), Color.GREEN));
			break;
		case DOWN:
			playerbody.add(
					new Player(playersize, (int) tail.getLayoutX(), (int) tail.getLayoutY() - playersize, Color.GREEN));
			break;
		default:
			break;
		}
		root.getChildren().add(playerbody.get(playerbody.size() - 1));

	}

	private void movePlayerBody() {
		if (playerbody.size() > 1) {
			for (int i = 1; i < playerbody.size(); i++) {
				switch (playerbody.get(i - 1).getPenultimateDirection()) {
				case UP:
					playerbody.get(i).moveUp();
					break;
				case LEFT:
					playerbody.get(i).moveLeft();
					break;
				case DOWN:
					playerbody.get(i).moveDown();
					break;
				case RIGHT:
					playerbody.get(i).moveRight();
					break;
				default:
					break;
				}
			}

		}
	}

	private void updateFruit() {
		root.getChildren().removeAll(fruit, scorelabel);
		fruit = new Rectangle(playersize, playersize, Color.RED);
		fruit.setLayoutX(random.nextInt(19) * playersize);
		fruit.setLayoutY(random.nextInt(19) * playersize);
		scorelabel.setText(Integer.toString(++score));
		root.getChildren().addAll(fruit, scorelabel);
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

}
