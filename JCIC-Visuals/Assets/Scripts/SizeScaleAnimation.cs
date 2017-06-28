using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
/// Size scale animation script. 
/// When attached to a GameObject, will grow or shrink these objects over time.
/// Destroys itself when it's goal is reached.
/// </summary>
public class SizeScaleAnimation : MonoBehaviour {


	public float Size = 0;
	public float SizePerFrame = 0;
	public bool HeightOnly = false;

	public SizeScaleAnimation()
	{
	}

	/// <summary>
	/// Sets the variables. To be called after SizeScaleAnimation was instantiated. 
	/// This is not done in constructor because MonoBehavior scripts cannot be instantiated attached to a GameObject with parameters.
	/// </summary>
	/// <param name="size">Size multiplier that is aimed to be reached.</param>
	/// <param name="sizePerFrame">Size increase or decrease per frame.</param>
	/// <param name="heightOnly">If set to <c>true</c> height only increases.</param>
	public void setVariables(float size, float sizePerFrame, bool heightOnly)
	{
		this.Size = size;
		this.SizePerFrame = sizePerFrame;
		this.HeightOnly = heightOnly;
	}


	/// <summary>
	/// Update this instance once per frame. Increases (or decreases) the size of the GameObject.
	/// Destroys the script if the goal was reached.
	/// </summary>
	void Update () {

		Vector3 xyzScale = this.gameObject.transform.localScale;
		bool end = false;

		if (xyzScale.y > Size && SizePerFrame > 0) {
			//too big, shrink instead
			SizePerFrame *= -5;
		}
		float newScale = xyzScale.y + SizePerFrame;

		if (newScale > Size && SizePerFrame > 0) { //expanding limit
			newScale = Size;
			end = true;
		}

		if (newScale < Size && SizePerFrame < 0) { //shrinking limit
			newScale = Size;
			end = true;
		}


		if (HeightOnly) {
			float widthScale = newScale/4;
			if (widthScale > 2)
				widthScale = 2;
			xyzScale = new Vector3 (widthScale, newScale, widthScale);
		}else
			xyzScale = new Vector3 (newScale, newScale, newScale);


		this.gameObject.transform.localScale = xyzScale;

		if (end)
			Destroy (this);
	}
}
