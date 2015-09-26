package game;

import core.CoreEngine;

public class Main {
	public static void main(String[] args) 
	{
		CoreEngine engine = new CoreEngine(1000, 700, 60, new ShootingGame());
		engine.createWindow("Shooting Range  ");
		engine.start();
	}
}
