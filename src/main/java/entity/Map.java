package entity;

import java.util.ArrayList;

public class Map 
{
	
    int mapId = 0;
    ArrayList<Node> nodes;
       
    int width;
    int height;
    
    public Map() {
    	nodes = new ArrayList<Node>();
    }

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
    

    
}
