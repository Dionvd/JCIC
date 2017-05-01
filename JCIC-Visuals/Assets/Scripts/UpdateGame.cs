//C# Naming convention : 
//PascalCase for classes, methods, class variables.
//camelCase for method parameters and method variables.

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

	public GameObject HexagonPrefab;
	public GameObject BuildingPrefab;
	public GameObject WallPrefab;
	Map Map;

	public static List<JSONObject> UpdateJSONObjects = new List<JSONObject> ();

	private static bool MockData = false;

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

			//update match info
			long matchId = jsonObject.GetField("id").i;
			JSONObject playersJson = jsonObject.GetField("players");
			Players players = new Players (playersJson);
			UpdateMatchInfo (matchId, players);
			Debug.Log ("Match info updated! " + playersJson);

			//update map
			JSONObject mapJson = jsonObject.GetField ("map").GetField("nodes");

			if (mapJson != null) {
				Map map = new Map (mapJson);
				UpdateMap (map);
				Debug.Log ("Map updated! " + mapJson);
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
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (Map [x, y] != null) {
					Destroy (Map [x, y].hexagon);
					Destroy (Map [x, y].building);
				}

				Map [x, y] = newMap [x, y];
				Map [x, y].CheckInstantiation (new Vector3 (x + (y % 2 / 2f) - Map.X, -1.5f, (-y + Map.Y) * 0.9f), HexagonPrefab, BuildingPrefab);
				Map [x, y].SetColorByOwner ();
			}
		}

		//Initialize walls
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {

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
					GameObject wall = Instantiate(GameObject.Find ("Plane").GetComponent<UpdateGame> ().WallPrefab, new Vector3(x + (y % 2 / 2f) - Map.X, -1.7f, (-y + Map.Y) * 0.9f), new Quaternion());
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
				for (int y = 0; y < 10; y++) {
					for (int x = 0; x < 10; x++) {
						Node node = Map [x, y];
						if (node != null && node.OwnerId != 0) {
							node.SetPowerStatue (node.Power + 1);
						}
					}
				}
			} else {
				switch (move.Action) {

				case 0: //sleep
					break;

				case 1: //spread
					Map [newX, newY].OwnerId = Map [move.X, move.Y].OwnerId;
					Map [move.X, move.Y].SetPowerStatue (Map [move.X, move.Y].Power - 20);
					Map [newX, newY].ClearGameObjects ();
					Map [newX, newY].CheckInstantiation (new Vector3 (newX + (newY % 2 / 2f) - Map.X, -1.5f, (-newY + Map.Y) * 0.9f), HexagonPrefab, BuildingPrefab);
					Map [newX, newY].SetColorByOwner ();
					Debug.Log ("SPREAD TO " + newX + "," + newY);
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
				GameObject hexObj = Instantiate (HexagonPrefab, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);
				GameObject bldObj = Instantiate (BuildingPrefab, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);

				Map [x, y] = new Node (0, 0, 0, hexObj, bldObj);
			}
		Map [0, 0].OwnerId = 1;
		Map [9, 9].OwnerId = 2;

		Map [0, 0].SetColorByOwner ();
		Map [9, 9].SetColorByOwner ();
	}


}
