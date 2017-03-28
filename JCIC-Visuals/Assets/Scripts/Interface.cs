using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Interface : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	void OnGUI () {

		string Text = "\n\n Paul \n Terrance \n Phil";

		// Make a background box
		GUI.Box(new Rect(10,10,100,90), "Waiting Queue" + Text);


	}
}
