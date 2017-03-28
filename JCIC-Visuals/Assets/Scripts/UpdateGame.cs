using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UpdateGame : MonoBehaviour
{

	public GameObject hexagonPrefab;
	public GameObject buildingPrefab;
	Map map;

	public static List<JSONObject> UpdateJSONObjects = new List<JSONObject> ();


	private static bool mockData = false;

	// Use this for initialization
	void Start ()
	{
		map = new Map ();

		if (mockData)
			MockMap ();
	}



	void MockMap ()
	{
		for (int y = 0; y < 10; y++)
			for (int x = 0; x < 10; x++) {
				GameObject hexObj = Instantiate (hexagonPrefab, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);
				GameObject bldObj = Instantiate (buildingPrefab, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);

				map [x, y] = new Node (0, 0, 0, hexObj, bldObj);
			}
		map [0, 0].OwnerId = 1;
		map [9, 9].OwnerId = 2;
	
		map [0, 0].SetColorByOwner ();
		map [9, 9].SetColorByOwner ();
	}

	// Update is called once per frame
	void Update ()
	{

		if (UpdateJSONObjects.Count == 0)
			return;

		lock (UpdateJSONObjects) {

			while (UpdateJSONObjects.Count > 0) {

				JSONObject jsonObject = UpdateJSONObjects [0];
				UpdateJSONObjects.RemoveAt (0);

				//check if a new map was received
				if (jsonObject.HasField ("nodes")) {

					JSONObject mapJson = jsonObject.GetField ("nodes");

					if (mapJson != null) {
						Map map = new Map (mapJson);
						UpdateMap (map);
						Debug.Log ("Map updated!");
					}
				}
				//check if new moves were received
				else if (jsonObject.HasField ("moves")) { 

					JSONObject movesJson = jsonObject.GetField ("moves");

					if (movesJson != null) {
						Moves moves = new Moves (movesJson);
						UpdateMap (moves);
						Debug.Log ("Moves performed on map!");
					}
				} 
				//a new queue was received
				else {
					List<string> queue = new List<string> ();
					Debug.Log ("Queue updated! - " + jsonObject);
				}
				
			}
		}
		
	}



	//Map received: [{"x":0,"y":0,"id":0,"power":61,"type":0,"ownerId":1}]
	void UpdateMap (Map newMap)
	{
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (map [x, y] != null) {
					Destroy (map [x, y].hexagon);
					Destroy (map [x, y].building);
				}

				map [x, y] = newMap [x, y];
				map [x, y].checkInstantiation (new Vector3 (x + (y % 2 / 2f) - map.X, 0, (-y + map.Y) * 0.9f), hexagonPrefab, buildingPrefab);
				map [x, y].SetColorByOwner ();
			}
		}
	}

	//Moves received: [{"x":0,"action":1,"y":0,"direction":4}]
	void UpdateMap (Moves moves)
	{
		foreach (Move move in moves) {

			Vector2 newLoc = CalcNewLoc (move.X, move.Y, move.Direction);
			int newX = (int)newLoc.x;
			int newY = (int)newLoc.y;

			if (move.X == -1 && move.Y == -1) {
				Debug.Log ("NEW TURN");
				for (int y = 0; y < 10; y++) {
					for (int x = 0; x < 10; x++) {
						Node node = map [x, y];
						if (node.OwnerId != 0) {
							node.SetPowerStatue (node.Power + 1);
						}
					}
				}
			} else {
				switch (move.Action) {

				case 0: //sleep
					break;

				case 1: //spread
					map [newX, newY].OwnerId = map [move.X, move.Y].OwnerId;
					map [move.X, move.Y].SetPowerStatue (map [move.X, move.Y].Power - 20);
					map [newX, newY].ClearGameObjects ();
					map [newX, newY].checkInstantiation (new Vector3 (newX + (newY % 2 / 2f) - map.X, 0, (-newY + map.Y) * 0.9f), hexagonPrefab, buildingPrefab);
					map [newX, newY].SetColorByOwner ();
					Debug.Log ("SPREAD TO " + newX + "," + newY);
					break;
				}
			}
		}
	}


	void UpdateQueue ()
	{
		//TODO
	}


	//Calculates the location of a location moving in the given direction
	Vector2 CalcNewLoc (int x, int y, int direction)
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
