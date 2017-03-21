package com.playground.rea.service.robot.model;

import java.io.Serializable;

import com.playground.rea.util.direction.DirectionEnum;

/**
 * Contains a robots current location. It is initialized at origin (0,0) which can be 
 * considered to be the SOUTH WEST most corner of a table top.
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 */
public class Robot implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int x = 0;
	
	private int y = 0;
	
	private DirectionEnum facing;
	
	private Table table;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public DirectionEnum getFacing() {
		return facing;
	}

	public void setFacing(DirectionEnum facing) {
		this.facing = facing;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	public String report() {
		return x + "," + y + "," + facing;
	}
	
	public boolean isPlaced() {
		if(table == null) {
			return false;
		} else {
			return true;
		}	
	}

	@Override
	public String toString() {
		return "Robot [x=" + x + ", y=" + y + ", facing=" + facing + ", table=" + table + "]";
	}
	
}
