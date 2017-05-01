using System;
using System.Collections.Generic;
using UnityEngine;

public class Map 
{
	private Node[,] Nodes;

	public List<GameObject> Walls;

	public int X = 5;
	public int Y = 5;

	public Map()
	{
		Nodes = new Node[10,10];
		Walls = new List<GameObject> ();
	}

	public Map (JSONObject jsonMap)
	{
		
		Nodes = new Node[10,10];	
		Walls = new List<GameObject> ();

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				Nodes[x,y] = new Node (jsonMap [y*10+x]);
			}
		}

	}

	public int Width
	{
		get { return Nodes.GetLength (0); }
	}

	public int Height
	{
		get { return Nodes.GetLength (1); }
	}

	public Node this[int x,int y] {
		get { if (x < 0 || y < 0 || x >= Width || y >= Height)
				return null; 
			return Nodes [x, y]; }
		set { Nodes [x, y] = value; }
	}

	//Calculates the location of a location moving in the given direction
	public Vector2 CalcNewLoc (int x, int y, int direction)
	{
		switch (direction) {
		case 0: //CENTER
			break;
		case 1: //NORTHWEST
			if (y % 2 == 0)
				x--;
			y--;
			break;
		case 2: //NORTHEAST
			if (y % 2 == 1)
				x++;
			y--;
			break;
		case 3: //WEST
			x--;
			break;
		case 4: //EAST
			x++;
			break;
		case 5: //SOUTHWEST
			if (y % 2 == 0)
				x--;
			y++;
			break;
		case 6: //SOUTHEAST
			if (y % 2 == 1)
				x++;
			y++;
			break;
		}

		return new Vector2 (x, y);
	}
}


