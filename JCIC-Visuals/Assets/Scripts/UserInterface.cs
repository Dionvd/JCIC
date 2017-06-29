using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Linq;

/// <summary>
/// User interface script. Adds and manipulates the GUI elements on the screen.
/// </summary>
public class UserInterface : MonoBehaviour {

	private string QueueText = "No Queue...";

	public GameObject Gui;
	public GameObject GuiWaitingQueue;
	public GameObject GuiGameInfoPrefab;
	public Dictionary<long, GameObject> GuiGameInfo = new Dictionary<long, GameObject> ();

	Players Players;


	// Use this for initialization
	void Start () {
		GameObject newGameInfo = Instantiate (GuiGameInfoPrefab, Gui.transform);
		newGameInfo.GetComponent<RectTransform> ().anchoredPosition = new Vector2 (0, -1);
		newGameInfo.GetComponent<RectTransform> ().Translate(0,-100,0);
		GuiGameInfo.Add (1, newGameInfo);
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void SetQueueText (string text) {
		this.QueueText = text;
		GuiWaitingQueue.transform.GetChild (0).GetComponent<UnityEngine.UI.Text> ().text = "Waiting Queue\n\n" + QueueText;;
	}

	public void SetMatchText (long id, Players players) {
		GameObject gameInfo = GuiGameInfo [1];
		this.Players = players;
		gameInfo.transform.GetChild (0).GetComponent<UnityEngine.UI.Text> ().text = "Game "+id+"\n"+players.toString();
	}

	public void SetScore(Dictionary<long, Map> maps)
	{
		if (this.Players == null)
			return;

		
		Dictionary<long, int> score = new Dictionary<long, int> ();

//		for (int x = 0; x < map.Width; x++) {
//
//			for (int y = 0; y < map.Height; y++) {
//				if (score.ContainsKey (map [x, y].OwnerId))
//					score [map [x, y].OwnerId]++;
//				else
//					score [map [x, y].OwnerId] = 1;
//			}
//		}

		for (int i = 0; i < Players.Count; i++) {
			if (score.ContainsKey (Players.Ids [i])) {
				Players.Scores [i] = score [Players.Ids [i]];
			}
		}

		SetMatchText (1, Players);;
		Debug.Log ("Scores updated!");
	}
}
