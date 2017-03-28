using System;
using System.Collections.Generic;

public class Moves : List<Move>
{
	public Moves()
	{
	}

	public Moves (JSONObject jsonMap)
	{
		for (int x = 0; x < jsonMap.Count; x++) {
			this.Add(new Move (jsonMap [x]));
		}
	}

}


