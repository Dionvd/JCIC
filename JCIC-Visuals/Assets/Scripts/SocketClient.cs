using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net;
using System.Net.Sockets;
using System;

public class SocketClient : MonoBehaviour
{

	bool restart = false;
	float restartTime;

	IPEndPoint serverAddress;
	Socket clientSocket;

	void Start ()
	{
		try {
			Debug.Log ("Starting Socket client.");
			serverAddress = new IPEndPoint (IPAddress.Parse ("127.0.0.1"), 5242);
			clientSocket = new Socket (AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
			InvokeRepeating ("ReceiveInfo", .02f, 0.2f);
			clientSocket.Connect (serverAddress);
		} catch (Exception e) {
			Debug.Log (e.Message + "...Will restart in 5 seconds.");
			restartTime = Time.fixedTime + 5f;
			restart = true;
			clientSocket.Close ();
		}
	}

	void Update ()
	{
		
		if (restart && Time.fixedTime > restartTime) {
			restart = false;
			Start ();
		}
	}

	void ReceiveInfo ()
	{
		if (restart)
			return;
		
		try {
			// Receiving
			byte[] rcvLenBytes = new byte[4];

			clientSocket.Receive (rcvLenBytes);

			int rcvLen = System.BitConverter.ToInt32 (rcvLenBytes, 0);

			byte[] rcvBytes = new byte[rcvLen];
			clientSocket.Receive (rcvBytes);
			String rcv = System.Text.Encoding.ASCII.GetString (rcvBytes);

			JSONObject gameMap = new JSONObject(rcv);

			SpawnMap.gameMapUpdate = gameMap;
			//Debug.Log ("Client received: " + gameMap.ToString());

		} catch (Exception e) {
			Debug.Log ("No connection was found..."+ e.Message);
		}
	}
}
