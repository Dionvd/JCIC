using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnMap : MonoBehaviour {

	public GameObject hexagonPrefab;
	Map map;
	public static JSONObject gameMapUpdate;

	// Use this for initialization
	void Start () {
		map = new Map ();

//		for (int y = 0; y < 10; y++)
//			for (int x = 0; x < 10; x++) {
//				GameObject obj = Instantiate (hexagonPrefab, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);
//				map[x, y] = new Node (0, 0, 0, obj);
//			}
//		map [0, 0].OwnerId = 1;
//		map [9, 9].OwnerId = 2;
//
//		map [0, 0].setColorByOwner ();
//		map [9, 9].setColorByOwner ();

	}
	
	// Update is called once per frame
	void Update () {
		if (gameMapUpdate != null) {
			Debug.Log ("Map updated!");

			JSONObject newMap = gameMapUpdate.GetField ("nodes");
			gameMapUpdate = null;

			Map map = new Map (newMap);
			UpdateMap (map);

		}
	}

	void UpdateMap(Map newMap)
	{
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (map [x, y] == null) {
					GameObject obj = Instantiate (hexagonPrefab, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);
					map [x, y] = newMap [x, y];
					map [x, y].gameObject = obj;
					map [x, y].SetColorByOwner ();
				}
			}
		}
	}
}
