using System;
using System.Collections;
using System.Collections.Generic;

public class Players : List<String>
{
	public List<long> Ids = new List<long>();

	//playerNr = index, which defines which color the player receives.

	public List<int> Scores = new List<int>();

	public Players (JSONObject jsonPlayers)
	{
		for (int x = 0; x < jsonPlayers.Count; x++) {
			this.Add(jsonPlayers [x].str);
			Ids.Add (long.Parse(jsonPlayers [x].str));
			Scores.Add (0);

		}
	}

	public string toString() { 

		String str = "";
		for (int i = 0; i < this.Count; i++) {
			str += "\n" + this [i];
			if (Scores[i] > 0)
				str += " - " + Scores [i];
		}

		return str;
	}
}


