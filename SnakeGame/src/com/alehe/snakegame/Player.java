package com.alehe.snakegame;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	private int size;
	private Direction lastDirection = Direction.NONE;
	private Direction penultimateDirection = Direction.NONE;

	public Player(int size, int x, int y, Color color) {
		super(size, size, color);
		this.size = size;
		setLayoutX(x);
		setLayoutY(y);
	}

	public void moveUp() {
		setLayoutY(getLayoutY() - size);
		penultimateDirection = lastDirection;
		lastDirection = Direction.UP;
	}

	public void moveLeft() {
		setLayoutX(getLayoutX() - size);
		penultimateDirection = lastDirection;
		lastDirection = Direction.LEFT;
	}

	public void moveRight() {
		setLayoutX(getLayoutX() + size);
		penultimateDirection = lastDirection;
		lastDirection = Direction.RIGHT;
	}

	public void moveDown() {
		setLayoutY(getLayoutY() + size);
		penultimateDirection = lastDirection;
		lastDirection = Direction.DOWN;
	}

	public Direction getPenultimateDirection() {
		return penultimateDirection;
	}

	public Direction getLastDirection() {
		return lastDirection;
	}
}