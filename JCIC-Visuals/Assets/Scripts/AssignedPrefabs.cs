using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AssignedPrefabs : MonoBehaviour {

	public static GameObject HEXAGON_PREFAB;
	public static GameObject BUILDING_PREFAB;
	public static GameObject WALL_PREFAB;
	public static GameObject TENTACLE_PREFAB;
	public static GameObject WATERDROP_PREFAB;

	public static GameObject POWERLINE_PREFAB;
	public static GameObject OVERCLOCK_PREFAB;
	public static GameObject GUARD_PREFAB;
	public static GameObject STORAGE_PREFAB;

	public static GameObject EMPOWER_PREFAB;
	public static GameObject DRAIN_PREFAB;

	public static GameObject SPREADPARTICLE_PREFAB;

	public GameObject HexagonPrefab;
	public GameObject BuildingPrefab;
	public GameObject WallPrefab;
	public GameObject TentaclePrefab;
	public GameObject WaterdropPrefab;

	public GameObject PowerlinePrefab;
	public GameObject OverclockPrefab;
	public GameObject GuardPrefab;
	public GameObject StoragePrefab;

	public GameObject EmpowerPrefab;
	public GameObject DrainPrefab;
	public GameObject SpreadParticlePrefab;


	// Use this for initialization
	void Start () {
		HEXAGON_PREFAB = HexagonPrefab;
		BUILDING_PREFAB = BuildingPrefab;
		WALL_PREFAB = WallPrefab;
		TENTACLE_PREFAB = TentaclePrefab;
		WATERDROP_PREFAB = WaterdropPrefab;

		POWERLINE_PREFAB = PowerlinePrefab;
		OVERCLOCK_PREFAB = OverclockPrefab;
		GUARD_PREFAB = GuardPrefab;
		STORAGE_PREFAB = StoragePrefab;

		EMPOWER_PREFAB = EmpowerPrefab;
		DRAIN_PREFAB = DrainPrefab;
		SPREADPARTICLE_PREFAB = SpreadParticlePrefab;
	}

}
