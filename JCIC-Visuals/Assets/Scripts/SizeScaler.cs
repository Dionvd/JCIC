using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SizeScaler : MonoBehaviour {


	float size = 0;
	float sizePerFrame = 0;
	bool heightOnly = false;

	public SizeScaler()
	{
	}

	public void setVariables(float Size, float SizePerFrame, bool heightOnly)
	{
		this.size = Size;
		this.sizePerFrame = SizePerFrame;
		this.heightOnly = heightOnly;
	}

	// Use this for initialization
	void Start () {
		
	}

	// Update is called once per frame
	void Update () {

		Vector3 xyzScale = this.gameObject.transform.localScale;

		if (xyzScale.y > size && sizePerFrame > 0) {
			//too big, shrink instead
			sizePerFrame *= -5;
		}

		float newScale = xyzScale.y + sizePerFrame;



		bool end = false;
		if (newScale > size && sizePerFrame > 0) { //expanding limit
			newScale = size;
			end = true;
		}

		if (newScale < size && sizePerFrame < 0) { //shrinking limit
			newScale = size;
			end = true;
		}


		if (heightOnly) {
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
