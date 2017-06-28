using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MoveAnimation : MonoBehaviour {

    
	public float[] HeightMovementOver20Frames = new float[5];
	int counter = 0;

	public MoveAnimation()
	{
	}

	/// <summary>
	/// Update this instance once per frame. Increases (or decreases) the size of the GameObject.
	/// Destroys the script if the goal was reached.
	/// </summary>
	void Update () {

		int index = counter / 30;

		if (index >= HeightMovementOver20Frames.Length) {
			Destroy (this.gameObject);
			Debug.Log ("Done!");
			return;
		}

		this.transform.Translate (0, HeightMovementOver20Frames[index] / 20, 0);

		counter++;
	}
}
