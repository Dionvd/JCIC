using System;
using System.Collections.Generic;

public class Map
{
	private Node[,] Nodes;

	public int X = 5;
	public int Y = 5;

	public Map()
	{
		Nodes = new Node[10,10];	
	}

	public Map (JSONObject jsonMap)
	{
		Nodes = new Node[10,10];	
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				Nodes[x,y] = new Node (jsonMap [y][x]);
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
		get {return Nodes [x, y]; }
		set { Nodes [x, y] = value; }
	}


}


