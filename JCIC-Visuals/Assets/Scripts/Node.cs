using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Node : MonoBehaviour {

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
			color = GameObject.Find ("Plane").GetComponent<Colors> ().green;
			break;
		case 1:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().red;
			break;
		case 2:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().blue;
			break;
		case 3:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().pink;
			break;
		case 4:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().purple;
			break;
		case 5:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().yellow;
			break;
		default:
			color = GameObject.Find ("Plane").GetComponent<Colors> ().white;
			break;
		}
		building.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding1.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding2.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding3.transform.GetChild (0).GetComponent<Renderer> ().material = color;
		subBuilding4.transform.GetChild (0).GetComponent<Renderer> ().material = color;

	}

	public void checkInstantiation(Vector3 location, GameObject hexagonPrefab, GameObject buildingPrefab)
	{
		if (OwnerId != 0) {
			hexagon = Instantiate (hexagonPrefab, location, new Quaternion());
			building = Instantiate (buildingPrefab, location, new Quaternion());

			subBuilding1 = Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-100,30)/300f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding2 = Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-100,30)/300f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding3 = Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-100,30)/300f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding4 = Instantiate (buildingPrefab, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-100,30)/300f, location.z+Random.Range(-100,100)/230f), new Quaternion());
		}
	}

	public void ClearGameObjects()
	{
		Destroy (building);
		Destroy (hexagon);
		Destroy (subBuilding1);
		Destroy (subBuilding2);
		Destroy (subBuilding3);
		Destroy (subBuilding4);
	}

	public void SetPowerStatue(int Power)
	{

		if (Power > 100)
			Power = 100;

		this.Power = Power;

		float Scale = Power * 0.09f - (12-hexagon.transform.localScale.y);
		
		if (Scale > 10f)
			Scale = 10f;
		if (Scale < 0f)
			Scale = 0f;

		float Scale2 = Scale/2 - (8-hexagon.transform.localScale.y);
		if (Scale2 > 2.5f)
			Scale2 = 2.5f;
		if (Scale2 < 0f)
			Scale2 = 0f;

			if (hexagon.GetComponent<SizeScaler> () == null) {
				SizeScaler sizeScalerScript = hexagon.AddComponent<SizeScaler> ();
				sizeScalerScript.setVariables (10f, 0.03f, false);
			}
		if (Scale != 0) {

			if (building.GetComponent<SizeScaler> () == null) {
				SizeScaler sizeScalerScript = building.AddComponent<SizeScaler> ();
				sizeScalerScript.setVariables (Scale, 0.01f, true);
			}
		}
		if (Scale2 != 0) {
			if (subBuilding1.GetComponent<SizeScaler> () == null) {
				SizeScaler sizeScalerScript = subBuilding1.AddComponent<SizeScaler> ();
				sizeScalerScript.setVariables (Scale2, 0.003f, true);
			}

			if (subBuilding2.GetComponent<SizeScaler> () == null) {
				SizeScaler sizeScalerScript = subBuilding2.AddComponent<SizeScaler> ();
				sizeScalerScript.setVariables (Scale2, 0.003f, true);
			}

			if (subBuilding3.GetComponent<SizeScaler> () == null) {
				SizeScaler sizeScalerScript = subBuilding3.AddComponent<SizeScaler> ();
				sizeScalerScript.setVariables (Scale2, 0.003f, true);
			}

			if (subBuilding4.GetComponent<SizeScaler> () == null) {
				SizeScaler sizeScalerScript = subBuilding4.AddComponent<SizeScaler> ();
				sizeScalerScript.setVariables (Scale2, 0.003f, true);
			}
		}
	}

}
