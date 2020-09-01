package com.alehe.snakegame;

public enum Move {
	UP("up"), LEFT("left"), DOWN("down"), RIGHT("right");

	private String asString;

	private Move(final String asString) {
		this.asString = asString;
	}

	public String toString() {
		return asString;
	}
}