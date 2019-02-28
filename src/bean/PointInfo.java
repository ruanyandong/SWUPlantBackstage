package bean;

import java.io.Serializable;
import java.util.List;

public class PointInfo implements Serializable{
	
	private int pointNumber;//ֲ���λ
    private double latitude;// γ�ȣ�γ��
    private double longitude;//���ȣ�����
    private List<String> plantNameList;//ֲ�������б�
    
	public int getPointNumber() {
		return pointNumber;
	}
	public void setPointNumber(int pointNumber) {
		this.pointNumber = pointNumber;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public List<String> getPlantNameList() {
		return plantNameList;
	}
	public void setPlantNameList(List<String> plantNameList) {
		this.plantNameList = plantNameList;
	}

    
}
