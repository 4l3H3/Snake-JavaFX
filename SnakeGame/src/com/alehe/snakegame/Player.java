package com.alehe.snakegame;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	private int size;
	private Move lastmove = Move.NONE;
	private Move penultimatemove = Move.NONE;

	public Player(int size, int x, int y, Color color) {
		super(size, size, color);
		this.size = size;
		setLayoutX(x);
		setLayoutY(y);
	}

	public void moveUp() {
		setLayoutY(getLayoutY() - size);
		penultimatemove = lastmove;
		lastmove = Move.UP;
	}

	public void moveLeft() {
		setLayoutX(getLayoutX() - size);
		penultimatemove = lastmove;
		lastmove = Move.LEFT;
	}

	public void moveRight() {
		setLayoutX(getLayoutX() + size);
		penultimatemove = lastmove;
		lastmove = Move.RIGHT;
	}

	public void moveDown() {
		setLayoutY(getLayoutY() + size);
		penultimatemove = lastmove;
		lastmove = Move.DOWN;
	}

	public Move getPenultimateMove() {
		return penultimatemove;
	}

	public Move getLastMove() {
		return lastmove;
	}
}