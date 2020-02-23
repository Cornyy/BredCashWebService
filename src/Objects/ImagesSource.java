package Objects;

import java.io.InputStream;
import java.util.ArrayList;


public class ImagesSource 
{
	
	private static ArrayList<InputStream> images = new ArrayList<InputStream>();
	
	public ImagesSource()
	{
		images = new ArrayList<InputStream>();
	}
	
	public void addImage(InputStream p,int index)
	{
		images.add(index,p);
	}
	public InputStream getImage(int index)
	{
		return images.get(index);
	}

}
