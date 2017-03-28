using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Move {

	public int X;
	public int Y;
	public int Action;
	public int Direction;

	public Move()
	{
	}

	public Move(JSONObject JsonNode)
	{
		try {
			this.X = int.Parse(JsonNode.GetField ("x").ToString());
			this.Y = int.Parse(JsonNode.GetField ("y").ToString());
			this.Action = int.Parse(JsonNode.GetField ("action").ToString());
			this.Direction = int.Parse(JsonNode.GetField ("direction").ToString());
		}
		catch (System.Exception e) {
			Debug.Log(e);	
		}
	}

}
