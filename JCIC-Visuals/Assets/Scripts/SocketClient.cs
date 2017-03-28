using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net;
using System.Net.Sockets;
using System;
using System.Threading;

public class SocketClient : MonoBehaviour
{

	bool restart = false;
	float restartTime;

	IPEndPoint serverAddress;
	Socket clientSocket;

	void Start ()
	{
		try {
			serverAddress = new IPEndPoint (IPAddress.Parse ("127.0.0.1"), 5242);
			clientSocket = new Socket (AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

			Thread thread = new Thread(ClientRun);
			thread.Start();
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

	void ClientRun()
	{
		while (true) {
			if (ReceiveInfo () == false)
				return;
			Thread.Sleep (100);
		}
	}

	Boolean ReceiveInfo ()
	{
		if (restart)
			return false;
		
		try {
			
			// Test connection
			if (clientSocket.Poll(1000, SelectMode.SelectRead) && clientSocket.Available == 0)
			{
				Debug.Log("Lost connection with the Socket server... Restarting...");
				restart = true;
				restartTime = 0f;
				return false;
			}

			// Receiving
			byte[] rcvLenBytes = new byte[4];

			clientSocket.Receive (rcvLenBytes);

			int rcvLen = System.BitConverter.ToInt32 (rcvLenBytes, 0);

			if (rcvLen == 0) 
			{
				//no message to be received...
				return true;
			}

			byte[] rcvBytes = new byte[rcvLen];
			clientSocket.Receive (rcvBytes);
			String rcv = System.Text.Encoding.ASCII.GetString (rcvBytes);

			JSONObject jsonObject = new JSONObject(rcv);
			Debug.Log ("Text received: " + jsonObject.ToString());

			lock(UpdateGame.UpdateJSONObjects)
			{
				UpdateGame.UpdateJSONObjects.Add(jsonObject);
			}

			return true;

		} catch (Exception e) {
			Debug.Log ("FATAL SOCKET ERROR : "+ e.Message);
		}
		return false;
	}
}
