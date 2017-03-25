package com.playground.rea.domain;

import java.io.Serializable;

/**
 * Contains the table top dimensions. It is initialised to 5 x 5 units.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class Table implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int dimensionX = 5;
	
	private int dimensionY = 5;

	public int getDimensionX() {
		return dimensionX;
	}

	public void setDimensionX(int dimensionX) {
		this.dimensionX = dimensionX;
	}

	public int getDimensionY() {
		return dimensionY;
	}

	public void setDimensionY(int dimensionY) {
		this.dimensionY = dimensionY;
	}

	
}
