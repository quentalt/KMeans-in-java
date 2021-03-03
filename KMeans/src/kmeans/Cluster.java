package kmeans;

import java.util.ArrayList;
import java.util.Locale;

public class Cluster {

	/*cluster associé à la donnée*/ 
	
	ArrayList<Data> dataSet;
	
	/** represente le centre de donnée
	 * 
	 */
	
	Data centroid;
	
	/** nombre de cluster */
	
	private static int nb;
	
	int num;
	
	double minDist;
	
	double maxDist;
	
	double moyDist;
	
	public Cluster() {
		
		dataSet = new ArrayList<>();
		centroid = new Data();
		num = nb++;
	}
	
	public Cluster(Data _centroid) {
		this();
		centroid = _centroid;
	}
	
		public void add(Data data)
		{
			
		dataSet.add(data);
		data.setCluster(this);
		}
		
		/* enlever la donnée du cluster*/
		public void remove(Data data) {
			dataSet.remove(data);
			data.setCluster(null);
		}

		/* calculer le centre du cluster*/
		
		public void centralize() {
			
			int nbElet = dataSet.size();
			if(nbElet>0) {
				
				int dim = dataSet.get(0).length;
				int[] tabI = {0};
				for (int i=0; i<dim;i++) {
					tabI[0] = i;
					// Somme normaliser
					double sum = dataSet.stream().parallel().mapToDouble(d->d.getNormValues(tabI[0])).sum();
					double average=sum/(double)nbElet;
					if(centroid.getNormValues(i)!=average)
				{
						centroid.setNormValues(i,sum/(double)nbElet);
				//sum de chaque valeur
						
						sum = dataSet.stream().parallel().mapToDouble(d->d.getValue(tabI[0])).sum();
				centroid.setValue(i,sum/(double)nbElet);
				}
			}
		}
	}
		/* calculer les stats*/
		
			public void computeStats() {
				
				double somDist=0;
				minDist = Double.POSITIVE_INFINITY;
				maxDist = Double.NEGATIVE_INFINITY;
				
				for(Data data:dataSet) {
					double dist = data.distNorm(centroid);
					if(dist<minDist) minDist=dist;
					if(dist<maxDist) maxDist=dist;
					
			}
		if(dataSet.size()>0) moyDist = somDist/dataSet.size();
/** @return le num des clusters, les stats et les données */ 			
		}
		@Override
		public String toString() {
			StringBuilder sb= new StringBuilder("cluster"+num+",nbElets = "+ dataSet.size()+ "\n");
		sb.append("--> centroid = ").append(centroid).append("\n data = \n");
		
		if(dataSet.size()<50)
			for(Data data:dataSet) sb.append(data.toString()).append("\n");
		sb.append("; dist min= ").append(String.format(Locale.ENGLISH,"%.2f,minDist")); 
		sb.append("; dist max= ").append(String.format(Locale.ENGLISH,"%.2f,maxDist"));
		sb.append("; dist average = ").append(String.format(Locale.ENGLISH,"%.2f,moyDist"));
		sb.append("   \n----");
		return sb.toString();
		
		}
		
		void setCentroid(Data _centroid) {
			centroid=_centroid;
		}
		
		Data getCentroid() {
		
		return centroid;
		
		}
		
		int getNum() {
			
			return num;
			
		}
		}
		
