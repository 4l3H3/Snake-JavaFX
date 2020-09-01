package com.alehe.snakegame;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	private int size;
	private String lastmove = null;
	private String penultimatemove = null;

	public Player(int size, int x, int y) {
		super(size, size, Color.GREEN);
		this.size = size;
		setLayoutX(x);
		setLayoutY(y);
		setStyle("opacity: 0.6;");
	}

	public void moveUp() {
		setLayoutY(getLayoutY() - size);
		penultimatemove = lastmove;
		lastmove = Move.UP.toString();
	}

	public void moveLeft() {
		setLayoutX(getLayoutX() - size);
		penultimatemove = lastmove;
		lastmove = Move.LEFT.toString();
	}

	public void moveRight() {
		setLayoutX(getLayoutX() + size);
		penultimatemove = lastmove;
		lastmove = Move.RIGHT.toString();
	}

	public void moveDown() {
		setLayoutY(getLayoutY() + size);
		penultimatemove = lastmove;
		lastmove = Move.DOWN.toString();
	}

	public String getPenultimateMove() {
		return penultimatemove;
	}

	public String getLastMove() {
		return lastmove;
	}
}