package kmeans;

import java.util.Arrays;
import java.util.Locale;

public class Data implements Cloneable {

	int length;
	/** valeurs*/
	
	private double[]values;
	/** valeur normalisé*/
	
	double[]normValues;
	
	/** cluster associé data*/
	
	Cluster cluster;
	
	/** 
	 * 
	 * definir les valeurs, 
	 * les normValues et la lenght*/
	 
	public Data(int _length) {
		
		length = _length;
		
		values = new double[length];
		
		normValues = new double[length];
	}

	public Data(double ...tab) {
		
		this(tab.length);
		
		values= Arrays.copyOf(tab, tab.length);
	}
	
	void normalize(double[]maxs) {
		
		normValues = new double[length];
		
		Arrays.setAll(normValues, i->values[i]/maxs[i]);
		
	}
	
	/** calculer la distance euclidienne de notre valeur à une autre */
	
	public double distNorm(Data o) {
		
		double sum = 0;
		
		for(int i=0; i<length;i++)
			sum += Math.pow(normValues[i] - o.normValues[i],2);
return Math.sqrt(sum);
	
	}
/** retourne la liste des valeurs*/
	
	@Override
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder("(");

		String sep =",";
		
		for(double v:values)
			
		sb.append(String.format(Locale.ENGLISH,"%.2f", v)).append(sep);
	sb.append(")");
	return sb.toString();
	}
	
	protected Data clone() {
		
		Data copy = new Data(values);
		copy.normValues = Arrays.copyOf(normValues, length);

		return copy;
		
		
	}
	

	
	public void setCluster(Cluster cluster) {
		
		this.cluster = cluster;
	}
	
	public Cluster getCluster() {
		
		return cluster;
	}

	public double getNormValues(int i) {
		// TODO Auto-generated method stub
		return normValues[i];
	}

	public void setValue(int i, double d) {
		// TODO Auto-generated method stub
		 values[i]=d;
	}

	public double getValue(int i) {
		// TODO Auto-generated method stub
		return values[i];
	}

	public void setNormValues(int i, double d) {
		// TODO Auto-generated method stub
		
		values[i] =d;
	
	}



	
	}
	





