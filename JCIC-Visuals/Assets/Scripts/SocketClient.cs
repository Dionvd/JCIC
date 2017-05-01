using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net;
using System.Net.Sockets;
using System;
using System.Threading;

/// <summary>
/// Socket Client. Responsible for the communication with the web service.
/// Received messages are sent to UpdateGame class to be processed.
/// </summary>
public class SocketClient : MonoBehaviour
{

	bool Restart = false;
	float RestartTime;

	IPEndPoint ServerAddress = new IPEndPoint (IPAddress.Parse ("127.0.0.1"), 5242);
	Socket ClientSocket;


	/// <summary>
	/// On application start, this script will try to listen to ServerAddress and start a new thread for data receiving.
	/// If it cannot connect, then the application will try again in five seconds.
	/// </summary>
	void Start ()
	{
		try {
			ClientSocket = new Socket (AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

			Thread thread = new Thread(ClientRun);
			thread.Start();
			ClientSocket.Connect (ServerAddress);
		} catch (Exception e) {
			Debug.Log (e.Message + "...Will restart in 5 seconds.");
			RestartTime = Time.fixedTime + 5f;
			Restart = true;
			ClientSocket.Close ();
		}
	}

	/// <summary>
	/// Called once per frame. Checks if the connection needs to be restarted.
	/// </summary>
	void Update ()
	{
		if (Restart && Time.fixedTime > RestartTime) {
			Restart = false;
			Start ();
		}
	}


	/// <summary>
	/// Method that loops and is run on a seperate thread.
	/// Loop is broken if something goes wrong.
	/// </summary> 
	void ClientRun()
	{
		while (true) {
			if (ReceiveInfo () == false)
				return;
			Thread.Sleep (100);
		}
	}

	/// <summary>
	/// Receives the info from the socket server and stores it in a JSONObject for UpdateGame.
	/// </summary>
	/// <returns><c>true</c>, if info was received or no messages were ready to be received, <c>false</c> if connection was lost or an error occurred.</returns>
	Boolean ReceiveInfo ()
	{
		if (Restart)
			return false;
		
		try {
			
			// Test connection
			if (ClientSocket.Poll(1000, SelectMode.SelectRead) && ClientSocket.Available == 0)
			{
				Debug.Log("Lost connection with the Socket server... Restarting...");
				Restart = true;
				RestartTime = 0f;
				return false;
			}

			// Receiving
			byte[] rcvLenBytes = new byte[4];

			ClientSocket.Receive (rcvLenBytes);

			int rcvLen = System.BitConverter.ToInt32 (rcvLenBytes, 0);

			if (rcvLen == 0) 
			{
				//no message to be received...
				return true;
			}

			byte[] rcvBytes = new byte[rcvLen];
			ClientSocket.Receive (rcvBytes);
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
