using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Node {

	private GameObject hexagon;
	private GameObject building;

	private GameObject subBuilding1;
	private GameObject subBuilding2;
	private GameObject subBuilding3;
	private GameObject subBuilding4;

	private GameObject typeBuilding;

	public long OwnerId;
	public int Type;
	public int Power;

	public Node(long OwnerId, int playerNr, int Type, int Power, GameObject hexagon, GameObject building)
	{
		this.OwnerId = OwnerId;
		this.Type = Type;
		this.hexagon = hexagon;
		this.building = building;
		SetPower(Power);
		SetColorByPlayerNr(playerNr);
	}

	public Node(JSONObject JsonNode)
	{
		try {
			this.Power = int.Parse(JsonNode.GetField ("power").ToString());
			this.Type = int.Parse(JsonNode.GetField ("type").ToString());
			this.OwnerId = long.Parse(JsonNode.GetField ("ownerId").ToString());
		}
		catch (System.Exception e) {
			Debug.Log(e);	
		}
	}

	public void SetColorByPlayerNr(int playerNr)
	{
		if (OwnerId == 0 || building == null)
			return;

		
		Material color;

		switch(playerNr%6)
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

	public void CheckInstantiation(Vector3 location, bool animation = true)
	{
		if (OwnerId != 0) {

			hexagon = MonoBehaviour.Instantiate (AssignedPrefabs.HEXAGON_PREFAB, location, new Quaternion());
			building = MonoBehaviour.Instantiate (AssignedPrefabs.BUILDING_PREFAB, location, new Quaternion());

			if (animation) {
				GameObject tentacle1 = MonoBehaviour.Instantiate (AssignedPrefabs.TENTACLE_PREFAB, new Vector3 (location.x + Random.Range (-50, 50) / 200f, location.y + Random.Range (-20, 10) / 300f - 2.2f, location.z + Random.Range (-50, 50) / 200f), new Quaternion ());
				GameObject tentacle2 = MonoBehaviour.Instantiate (AssignedPrefabs.TENTACLE_PREFAB, new Vector3 (location.x + Random.Range (-50, 50) / 200f, location.y + Random.Range (-20, 10) / 300f - 2.2f, location.z + Random.Range (-50, 50) / 200f), new Quaternion ());

				tentacle1.transform.Rotate (Random.Range (-45, 45), Random.Range (-180, 180), Random.Range (-45, 45));
				tentacle2.transform.Rotate (Random.Range (-45, 45), Random.Range (-180, 180), Random.Range (-45, 45));
			}

			subBuilding1 = MonoBehaviour.Instantiate (AssignedPrefabs.BUILDING_PREFAB, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding2 = MonoBehaviour.Instantiate (AssignedPrefabs.BUILDING_PREFAB, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding3 = MonoBehaviour.Instantiate (AssignedPrefabs.BUILDING_PREFAB, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());
			subBuilding4 = MonoBehaviour.Instantiate (AssignedPrefabs.BUILDING_PREFAB, new Vector3(location.x+Random.Range(-100,100)/230f, location.y+Random.Range(-50,30)/300f+0.2f, location.z+Random.Range(-100,100)/230f), new Quaternion());

			if (animation) {
				for (int i = 0; i < 20; i++) {
					GameObject waterdrop = MonoBehaviour.Instantiate (AssignedPrefabs.WATERDROP_PREFAB, new Vector3 (location.x + Random.Range (-30, 30) / 230f, location.y + Random.Range (-30, 30) / 300f - 0.2f, location.z + Random.Range (-30, 30) / 230f), new Quaternion ());
					waterdrop.transform.Rotate (Random.Range (-180, 180), Random.Range (-180, 180), Random.Range (-180, 180));
					waterdrop.gameObject.GetComponent<Rigidbody> ().AddForce (new Vector3 (Random.Range (-100, 100), Random.Range (200, 300), Random.Range (-100, 100)));
				}
			}
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
		if (typeBuilding != null)
			MonoBehaviour.Destroy (typeBuilding);

		building = null;
		hexagon = null;
		subBuilding1 = null;
		subBuilding2 = null;
		subBuilding3 = null;
		subBuilding4 = null;
		typeBuilding = null;
	}

	public void SetType(int type, Vector3 location)
	{
		if (this.Type == type)
			return;

		//destroy old objects
		MonoBehaviour.Destroy (typeBuilding);

		this.Type = type;

		//instantiate new objects
		switch (this.Type) {

		//powerline
		case 1:
			typeBuilding = MonoBehaviour.Instantiate (AssignedPrefabs.POWERLINE_PREFAB, new Vector3 (location.x, location.y, location.z), new Quaternion ());
			SizeScaleAnimation sizeScalerScript = typeBuilding.AddComponent<SizeScaleAnimation> ();
			sizeScalerScript.setVariables (2.5f, 0.01f, true);
			break;
		//overclocked
		case 2:
			typeBuilding = MonoBehaviour.Instantiate (AssignedPrefabs.OVERCLOCK_PREFAB, new Vector3 (location.x, location.y, location.z), new Quaternion ());
			SizeScaleAnimation sizeScalerScript2 = typeBuilding.AddComponent<SizeScaleAnimation> ();
			sizeScalerScript2.setVariables (2.5f, 0.01f, true);
			break;
		//guarded
		case 3:
			typeBuilding = MonoBehaviour.Instantiate (AssignedPrefabs.GUARD_PREFAB, new Vector3 (location.x, location.y, location.z), new Quaternion ());
			SizeScaleAnimation sizeScalerScript3 = typeBuilding.AddComponent<SizeScaleAnimation> ();
			sizeScalerScript3.setVariables (2.5f, 0.01f, true);
			break;
		//storage
		case 4:
			typeBuilding = MonoBehaviour.Instantiate (AssignedPrefabs.STORAGE_PREFAB, new Vector3 (location.x, location.y, location.z), new Quaternion ());
			SizeScaleAnimation sizeScalerScript4 = typeBuilding.AddComponent<SizeScaleAnimation> ();
			sizeScalerScript4.setVariables (2.5f, 0.01f, true);
			break;
		}
	}

	public void SetPower(int power)
	{

		if (power > 100 * (Type==4?3:1))
			power = 100 * (Type==4?3:1);

		if (power < 0)
			power = 0;

		this.Power = power;


		if (hexagon == null)
			return;
		
		float Scale = Power * 0.05f + 2f;
		
		if (Scale > 4f)
			Scale = 4f;
		if (Scale < 1f)
			Scale = 1f;

		float Scale2 = Scale/2 - (8-hexagon.transform.localScale.y);
		if (Scale2 > 2.5f)
			Scale2 = 2.5f;
		if (Scale2 < 0f)
			Scale2 = 0f;

			if (hexagon.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = hexagon.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (9f, 0.04f, false);
			}
		if (Scale != 0) {

			if (building.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = building.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale, 0.014f, true);
			}
		}
		if (Scale2 != 0) {
			if (subBuilding1.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding1.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.006f, true);
			}

			if (subBuilding2.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding2.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.007f, true);
			}

			if (subBuilding3.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding3.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.008f, true);
			}

			if (subBuilding4.GetComponent<SizeScaleAnimation> () == null) {
				SizeScaleAnimation sizeScalerScript = subBuilding4.AddComponent<SizeScaleAnimation> ();
				sizeScalerScript.setVariables (Scale2, 0.009f, true);
			}
		}
	}


	public void EmpowerAnimation(Vector3 location, int direction)
	{
		
		Vector3 rotation = Vector3.zero;

		switch (direction) {

		case 1: //NW
			rotation = new Vector3(0,60,0);
			break;
		case 2: //NE
			rotation = new Vector3(0,120,0);
			break;
		case 3: //W
			rotation = new Vector3(0,0,0);
			break;
		case 4: //E
			rotation = new Vector3(0,180,0);
			break;
		case 5: //SW
			rotation = new Vector3(0,300,0);
			break;
		case 6: //SE
			rotation = new Vector3(0,240,0);
			break;
		default:
			rotation = Vector3.zero;
			break;
		}

		GameObject obj = MonoBehaviour.Instantiate(AssignedPrefabs.EMPOWER_PREFAB, new Vector3(location.x,location.y+1.2f,location.z), new Quaternion());
		obj.transform.Rotate (rotation);

	}


	public void DrainAnimation(Vector3 location, int direction)
	{

		Vector3 rotation = Vector3.zero;

		switch (direction) {

		case 1: //NW
			rotation = new Vector3(0,60,0);
			break;
		case 2: //NE
			rotation = new Vector3(0,120,0);
			break;
		case 3: //W
			rotation = new Vector3(0,0,0);
			break;
		case 4: //E
			rotation = new Vector3(0,180,0);
			break;
		case 5: //SW
			rotation = new Vector3(0,300,0);
			break;
		case 6: //SE
			rotation = new Vector3(0,240,0);
			break;
		default:
			rotation = Vector3.zero;
			break;
		}

		GameObject obj = MonoBehaviour.Instantiate(AssignedPrefabs.DRAIN_PREFAB, new Vector3(location.x,location.y+1.2f,location.z), new Quaternion());
		obj.transform.Rotate (rotation);

	}


	public void AttackedBy(long attackerId, Vector3 location)
	{
		//if node is already owned, do nothing
		if (this.OwnerId == attackerId) 
			return;

		//if node is blocked, do nothing
		if (this.Type == -1) //node is blocked
			return;

		//if node is guarded, destroy guard
		if (this.Type == 3) { 
			SetType (0, location);
			return;
		}

		//if node is neutral, take over node
		if (this.OwnerId == 0) {
			this.OwnerId = attackerId;
			MonoBehaviour.Instantiate (AssignedPrefabs.SPREADPARTICLE_PREFAB, location, new Quaternion());
		}

		//else the node is an enemy node, destroy it.
		else {
			OwnerId = 0;
		}

		ClearGameObjects ();
		CheckInstantiation (location);
		SetType (0, location);
		SetPower (0);

	}
}
