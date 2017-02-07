using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnMap : MonoBehaviour {

	public GameObject hexagonPrefab;
	GameObject[,] map;

	public Material orange;
	public Material blue;
	public Material red;

	// Use this for initialization
	void Start () {
		map = new GameObject[10,10];

		for(int y = 0; y < 10; y++)
			for(int x = 0; x < 10; x++)
				map[x,y] = Instantiate (hexagonPrefab, new Vector3(x+(y%2/2f),0,-y*0.9f), this.transform.rotation);
	

		map[0,0].transform.GetChild(1).GetComponent<Renderer>().material = blue;
		map[9,9].transform.GetChild(1).GetComponent<Renderer>().material = red;

	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
