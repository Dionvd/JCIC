using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Node {

	public GameObject hexagon;
	public GameObject building;

	public GameObject subBuilding1;
	public GameObject subBuilding2;
	public GameObject subBuilding3;
	public GameObject subBuilding4;


	public int OwnerId;
	public int Type;
	public int Power;

	public Node(int OwnerId, int Type, int Power, GameObject hexagon, GameObject building)
	{
		this.OwnerId = OwnerId;
		this.Type = Type;
		this.hexagon = hexagon;
		this.building = building;
		SetPowerStatue(Power);
		SetColorByOwner();
	}

	public Node(JSONObject JsonNode)
	{
		try {
			this.Power = int.Parse(JsonNode.GetField ("power").ToString());
			this.Type = int.Parse(JsonNode.GetField ("type").ToString());
			this.OwnerId = int.Parse(JsonNode.GetField ("ownerId").ToString());
		}
		catch (System.Exception e) {
			Debug.Log(e);	
		}
	}

	public void SetColorByOwner()
	{
		if (OwnerId == 0)
			return;

		
		Material color;

		switch(OwnerId%6)
		{
		case 0:
			color = GameObject.Find ("Plane").GetComponent<ColorAssets> ().Green;
			break;
		case 1:
			color = GameObject.Find ("Plane").GetComponent<ColorAssets> ().Red;
			break;
		case 2:
			color = GameObject.Find ("Plane").GetComponent<ColorAssets> ().Blue;
			break;
		case 3:
			color = GameObject.Find ("Plane").GetComponent<ColorAssets> ().Pink;
			break;
		case 4:
			color = GameObject.Find ("Plane").GetComponent<ColorAssets> ().Purple;
			break;
		case 5:
			color = GameObject.Find ("Plane").GetComponent<ColorAssets> ().Yellow;
			break;
		default:
			color = GameObject.Find ("Plane").GetComponent<ColorAssets> ().White;
			break;
		}
		building.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding1.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding2.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding3.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding4.transform.GetChild (0).GetComponent<Renderer> ().material = color;

	}

	public void CheckInstantiation(Vector3 location, GameObject hexagonPrefab, GameObject buildingPrefab)
	{
		if (OwnerId != 0) {
			hexagon = MonoBehaviour.Instantiate (hexagonPrefab, location, new Quaternion());
			building = MonoBehaviour.Instantiate (buildingPrefab, location, new Quaternion());

			subBuilding1 = MonoBehaviour.Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding2 = MonoBehaviour.Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding3 = MonoBehaviour.Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding4 = MonoBehaviour.Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());
		}
	}

	public void ClearGameObjects()
	{
		MonoBehaviour.Destroy (building);
		MonoBehaviour.Destroy (hexagon);
		MonoBehaviour.Destroy (subBuilding1);
		MonoBehaviour.Destroy (subBuilding2);
		MonoBehaviour.Destroy (subBuilding3);
		MonoBehaviour.Destroy (subBuilding4);
	}

	public void SetPowerStatue(int power)
	{

		if (power > 100)
			power = 100;

		this.Power = power;

		float Scale = Power * 0.09f - (12-hexagon.transform.localScale.y);
		
		if (Scale > 5f)
			Scale = 5f;
		if (Scale < 0f)
			Scale = 0f;

		float Scale2 = Scale/2 - (8-hexagon.transform.localScale.y);
		if (Scale2 > 2.5f)
			Scale2 = 2.5f;
		if (Scale2 < 0f)
			Scale2 = 0f;

			if (hexagon.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = hexagon.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (9f, 0.03f, false);
			}
		if (Scale != 0) {

			if (building.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = building.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale, 0.007f, true);
			}
		}
		if (Scale2 != 0) {
			if (subBuilding1.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding1.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.003f, true);
			}

			if (subBuilding2.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding2.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.0035f, true);
			}

			if (subBuilding3.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding3.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.004f, true);
			}

			if (subBuilding4.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding4.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.0045f, true);
			}
		}
	}

}
