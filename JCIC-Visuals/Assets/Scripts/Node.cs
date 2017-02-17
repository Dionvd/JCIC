using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Node {

	public GameObject gameObject;
	public int OwnerId;
	public int Type;
	public int Power;

	public Node(int OwnerId, int Type, int Power, GameObject gameObject)
	{
		this.OwnerId = OwnerId;
		this.Type = Type;
		this.Power = Power;
		this.gameObject = gameObject;

		SetColorByOwner();
	}

	public Node(JSONObject JsonNode)
	{
		this.Power = int.Parse(JsonNode.GetField ("power").ToString());
		this.Type = int.Parse(JsonNode.GetField ("type").ToString());
		this.OwnerId = int.Parse(JsonNode.GetField ("ownerId").ToString());
	}

	public void SetColorByOwner()
	{
		if (OwnerId == 0)
			return;
		
		Material color;

		switch(OwnerId%6)
		{
		case 0:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().green;
			break;
		case 1:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().red;
			break;
		case 2:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().blue;
			break;
		case 3:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().pink;
			break;
		case 4:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().purple;
			break;
		case 5:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().yellow;
			break;
		default:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().white;
			break;
		}
		gameObject.transform.GetChild (1).GetComponent<Renderer> ().material = color;

	}
}
