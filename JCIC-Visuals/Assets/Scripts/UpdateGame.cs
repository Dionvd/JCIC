﻿//C# Naming convention : 
//PascalCase for classes, methods, public class variables.
//camelCase for method parameters, method variables and private class variables.

using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Linq;

/// <summary>
/// UpdateGame is the main script behind the visualisation.
/// It handles any information received from the socket client.
/// It stores important game information that was received.
/// </summary>
public class UpdateGame : MonoBehaviour
{
	Map Map;

	public static List<JSONObject> UpdateJSONObjects = new List<JSONObject> ();

	private static bool MockData = false;

	private Players players;


	// Initialization
	void Start ()
	{
		Map = new Map ();

		if (MockData)
			MockMap ();
	}


	/// <summary>
	/// Update is called once per frame. Checks if new messages were received by the socket client.
	/// If so, it calls for processing of the received messsage.
	/// </summary>
	void Update ()
	{

		if (UpdateJSONObjects.Count == 0)
			return;

		lock (UpdateJSONObjects) {
			while (UpdateJSONObjects.Count > 0) {
				//new JSON messages were received by the socket. Handle each message.	
				JSONObject jsonObject = UpdateJSONObjects [0];
				UpdateJSONObjects.RemoveAt (0);
				PerformJsonCommands (jsonObject);
			}
		}

		GameObject.Find ("Main Camera").GetComponent<UserInterface> ().SetScore (Map);
	}
		

	/// <summary>
	/// When given a JsonObject, will update the game visuals to represent the given JsonObject that was received.
	/// </summary>
	/// <param name="jsonObject">Json object.</param>
	void PerformJsonCommands(JSONObject jsonObject)
	{
		//check if a new map was received
		if (jsonObject.HasField ("map")) {

			Debug.Log ("Match info TEST " + jsonObject);

			//update match info
			long matchId = jsonObject.GetField("id").i;
			JSONObject playersJson = jsonObject.GetField("playerIds");

			if (playersJson.ToString () == "null") {
				Debug.Log ("Player information was empty!");
			} else {
				
				players = new Players (playersJson);
				UpdateMatchInfo (matchId, players);
				Debug.Log ("Match info updated! " + playersJson);

				//update map
				JSONObject mapJson = jsonObject.GetField ("map").GetField ("nodes");
				int mapWidth = 10;
				int mapHeight = 10;
				
				if (mapJson != null) {
					Map map = new Map (mapJson, mapWidth, mapHeight);
					UpdateMap (map);
					Debug.Log ("Map updated! " + mapJson);
				}
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
			JSONObject playersJson = jsonObject.GetField ("players");
			Players queue = new Players (playersJson);

			UpdateQueue (queue);
			Debug.Log ("Queue updated!");
		}
	}



	/// <summary>
	/// Updates the map to represent the newly received map
	/// </summary>
	/// <param name="newMap">New map.</param>
	void UpdateMap (Map newMap)
	{
		for (int y = 0; y < newMap.Height; y++) {
			for (int x = 0; x < newMap.Width; x++) {
				if (Map [x, y] != null) {
					Map [x, y].ClearGameObjects();
				}

				Map [x, y] = newMap [x, y];
				Map [x, y].CheckInstantiation (new Vector3 (x + (y % 2 / 2f) - Map.X, -1.5f, (-y + Map.Y) * 0.9f), false);
				Map [x, y].SetColorByPlayerNr (players.Ids.IndexOf(Map[x,y].OwnerId));
			}
		}

		//Initialize walls
		for (int x = 0; x < Map.Width; x++) {
			for (int y = 0; y < Map.Height; y++) {

				bool[] directionDoesntExists = new bool[6];
				Node nodeFrom = Map[x, y];

				for (int dir = 0; dir < 6; dir++) {
					Vector2 tempLoc = Map.CalcNewLoc (x, y, dir + 1);
					Node nodeTo = Map [(int)tempLoc.x, (int)tempLoc.y];
					if (nodeTo == null || nodeTo.Type == -1) {
						directionDoesntExists [dir] = true;
					}
					if (nodeFrom.Type == -1 && nodeTo.Type == -1) {
						directionDoesntExists [dir] = false;
					}
				}

				if (directionDoesntExists.Contains(true)) {
					//Walls are needed
					GameObject wall = Instantiate(AssignedPrefabs.WALL_PREFAB, new Vector3(x + (y % 2 / 2f) - Map.X, -1.7f, (-y + Map.Y) * 0.9f), new Quaternion());
					for (int dir = 5; dir >= 0; dir--) {
						if (!directionDoesntExists[dir]) {
							Destroy(wall.transform.GetChild (dir).gameObject);
						}
					}
					Map.Walls.Add (wall);
				}
			}
		}
	}

	/// <summary>
	/// Updates the match info as shown in the GUI.
	/// </summary>
	/// <param name="id">Identifier.</param>
	/// <param name="players">Players.</param>
	void UpdateMatchInfo (long id, Players players)
	{
		GameObject.Find ("Main Camera").GetComponent<UserInterface> ().SetMatchText (id, players);
	}

	/// <summary>
	/// Updates the map to show the moves that are being performed.
	/// </summary>
	/// <param name="moves">Moves.</param>
	void UpdateMap (Moves moves)
	{
		foreach (Move move in moves) {

			Vector2 newLoc = Map.CalcNewLoc (move.X, move.Y, move.Direction);
			int newX = (int)newLoc.x;
			int newY = (int)newLoc.y;

			if (move.X == -1 && move.Y == -1) {
				Debug.Log ("NEW TURN");
				for (int y = 0; y < Map.Height; y++) {
					for (int x = 0; x < Map.Width; x++) {
						Node node = Map [x, y];
						if (node != null && node.OwnerId != 0) {
							if (node.Type == 2) //overclocked
								node.SetPower(node.Power + 3);
							else
								node.SetPower(node.Power + 1);

						}
					}
				}
			} else {
				switch (move.Action) {

				case 0: //sleep
					break;

				case 1: //spread
					Map [move.X, move.Y].SetPower (Map [move.X, move.Y].Power - 10);
					Map [newX, newY].AttackedBy (Map [move.X, move.Y].OwnerId, new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f));
					Map [newX, newY].SetColorByPlayerNr (players.Ids.IndexOf(Map [newX, newY].OwnerId));
					Debug.Log ("SPREAD TO " + newX + "," + newY);
					break;
					
				case 2: //spreadAll
					Debug.Log ("SPREAD ALL " + newX + "," + newY);
					Map [move.X, move.Y].SetPower (Map [move.X, move.Y].Power - 40);

					for (int i = 1; i < 7; i++) {
						newLoc = Map.CalcNewLoc (move.X, move.Y, i);
						newX = (int)newLoc.x;
						newY = (int)newLoc.y;
						Map [newX, newY].AttackedBy (Map [move.X, move.Y].OwnerId, new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f));
						Map [newX, newY].SetColorByPlayerNr (players.Ids.IndexOf(Map [newX, newY].OwnerId));
					}
					break;

				case 3: //spreadLine
					Debug.Log ("SPREAD LINE " + newX + "," + newY);
					Map [move.X, move.Y].SetPower (Map [move.X, move.Y].Power - 60);
					for (int i = 0; i < 5; i++) {

						Map [newX, newY].AttackedBy (Map [move.X, move.Y].OwnerId, new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f));
						Map [newX, newY].SetColorByPlayerNr (players.Ids.IndexOf(Map [newX, newY].OwnerId));
						newLoc = Map.CalcNewLoc (newX, newY, move.Direction);
						newX = (int)newLoc.x;
						newY = (int)newLoc.y;
						if (newX < 0 || newX >= Map.Width || newY < 0 || newY >= Map.Height)
							break;
					}
					break;
				case 4: //empower
					Map [newX, newY].EmpowerAnimation (new Vector3 (move.X + (move.Y % 2 / 2f) - Map.X, -1.5f, (-move.Y + Map.Y) * 0.9f), move.Direction);
					Map [move.X, move.Y].SetPower(Map [move.X, move.Y].Power - 10);
					Map [newX, newY].SetPower(Map [newX, newY].Power + 5);

					Debug.Log ("EMPOWER " + newX + "," + newY);
					break;
				case 5: //discharge
					
					break;
				case 6: //powerline
					Map[newX,newY].SetType(1, new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f));
					Debug.Log ("POWERLINE " + newX + "," + newY);
					break;
				case 7: //overclock
					Map[newX,newY].SetType(2, new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f));
					Debug.Log ("OVERCLOCK " + newX + "," + newY);
					break;
				case 8: //guard
					Map[newX,newY].SetType(3, new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f));
					Debug.Log ("GUARD " + newX + "," + newY);
					break;
				case 9: //storage
					Map[newX,newY].SetType(4, new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f));
					Debug.Log ("STORAGE " + newX + "," + newY);
					break;
				case 10: //drain
					Map [newX, newY].DrainAnimation (new Vector3 (move.X + (move.Y % 2 / 2f) - Map.X, -1.5f, (-move.Y + Map.Y) * 0.9f), move.Direction);
					Map [move.X, move.Y].SetPower(Map [move.X, move.Y].Power - 10);
					Map [newX, newY].SetPower(Map [newX, newY].Power - 5);

					break;
				case 11: //explode
					for (int ix = -2; ix <= 2; ix++) {
						for (int iy = -2; iy <= 2; iy++) {

							newX = move.X + ix;
							newY = move.Y + iy;

							if (ix * iy == -4 || ix * iy == 4)
								continue; //skip corners

							if (move.Y % 2 == 0) {
								// . - 0 + .
								//  - - 0 + .
								// - - i + +
								//  - - 0 + .
								// . - 0 + .
								if ((iy == -1 || iy == 1) && ix == 2) {
									//not part of circle
									continue;
								}
							} else { 
								//  . - O + .
								// . - O + +
								//  - - i + +
								// . - O + +
								//  . - O + .
								if ((iy == -1 || iy == 1) && ix == -2) {
									//not part of circle
									continue;
								}
							
							}
						}

						Node explodingNode = Map [newX, newY];
						explodingNode.SetPower (0);
						explodingNode.OwnerId = 0;
						if (explodingNode.Type != -1)
							explodingNode.Type = 0;
					}
					break;
				}

			}
		}
	}

	/// <summary>
	/// Updates the waiting queue as shown on the GUI.
	/// </summary>
	/// <param name="queue">Queue.</param>
	void UpdateQueue (Players queue)
	{
		String text = queue.toString ();

		GameObject.Find ("Main Camera").GetComponent<UserInterface> ().SetQueueText (text);
	}

	/// <summary>
	/// Mocks the map with mock data, if enabled at Start().
	/// </summary>
	void MockMap ()
	{
		for (int y = 0; y < 10; y++)
			for (int x = 0; x < 10; x++) {
				GameObject hexObj = Instantiate (AssignedPrefabs.HEXAGON_PREFAB, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);
				GameObject bldObj = Instantiate (AssignedPrefabs.BUILDING_PREFAB, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);

				Map [x, y] = new Node (0, 0, 0, 0, hexObj, bldObj);
			}
		Map [0, 0].OwnerId = 1;
		Map [9, 9].OwnerId = 2;

		players = new Players (new JSONObject(""));
		players.Add ("John");
		players.Add ("Jake");

		Map [0, 0].SetColorByPlayerNr (0);
		Map [9, 9].SetColorByPlayerNr (1);
	}


}
