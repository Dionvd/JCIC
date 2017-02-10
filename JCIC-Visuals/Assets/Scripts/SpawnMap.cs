using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnMap : MonoBehaviour {

	public GameObject hexagonPrefab;
	Node[,] map;


	// Use this for initialization
	void Start () {
		map = new Node[10,10];

		for (int y = 0; y < 10; y++)
			for (int x = 0; x < 10; x++) {
				GameObject obj = Instantiate (hexagonPrefab, new Vector3 (x + (y % 2 / 2f), 0, -y * 0.9f), this.transform.rotation);
				map[x, y] = new Node (0, 0, 0, obj);
			}
		map [0, 0].OwnerId = 1;
		map [9, 9].OwnerId = 2;

		map [0, 0].setColorByOwner ();
		map [9, 9].setColorByOwner ();

	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
