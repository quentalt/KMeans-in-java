package kmeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/** initalisation de la data list*/

public class AppliKMeans {

		
		private static double Exemples[][];
		
		private static ArrayList<Data> dataSet =new ArrayList<>();
		/** liste de clusters */
		 
		 private static ArrayList<Cluster> clusters = new ArrayList<>();
		 
		
		
			private static void initialize(int _nbClusters, int _dim) {

		
		double[]maxs = new double [_dim];
		Arrays.fill(maxs, Double.NEGATIVE_INFINITY);
		for(double[] data:Exemples) {
			dataSet.add(new Data(data));
			Arrays.setAll(maxs, i->(maxs[i]>data[i]?maxs[i]:data[i]));
		
		}
		dataSet.forEach(d->d.normalize(maxs));
		
		createClusters(_nbClusters);
	}
	
	private static void createClusters (int _nbClusters) {
		
		Data centroid = dataSet.get((int)
				(dataSet.size()*Math.random()));
				Cluster firstCluster = new Cluster(centroid);
				clusters.add(firstCluster);
				int nbClusters = _nbClusters;
				
				for(int c = 1; c<nbClusters; c++) {
					
					Data farData = null;
					
					double maxDist  = Double.NEGATIVE_INFINITY;
					for(Data data:dataSet) {
						
						double minDist = Double.POSITIVE_INFINITY;
						for(Cluster cluster:clusters) {
						
						double dist = data.distNorm(cluster.getCentroid());
						
						if(minDist>dist) minDist = dist;
						
					}
						
						if(maxDist<minDist) {
							maxDist = minDist;
							farData = data;
							}
						}
					Cluster cluster = new Cluster (farData.clone());
					clusters.add(cluster);
				}
				System.out.println("Les centroides sont :  ");
				clusters.forEach(c->System.out.println(c.centroid.toString()));
	}
	
/** chercher le meilleur cluster pour une data
	@param  la donnée placer dans un cluster*/
	
	private static void kmeanCluster () {
		
		dataSet.forEach(data -> searchCluster(data).add(data));
		
		boolean mov = true;
		
		while(mov) {
			
			mov = false;
			
			clusters.forEach(c-> c.centralize());
			
			for(Data data:dataSet) {
				
				Cluster bestCluster = searchCluster(data);
				
				if(data.getCluster() !=bestCluster) {
					
					mov = true;
					data.getCluster().remove(data);
					bestCluster.add(data);
				}
			/* créer une donnée random*/

			
			}
		}
		
	}	
		private static Cluster searchCluster(Data data) {

			Cluster bestCluster = null;
			double minimum = Double.POSITIVE_INFINITY;
			double distance=0;
			for(Cluster cluster:clusters)
			{
				distance = data.distNorm(cluster.getCentroid());
				if(distance < minimum){ minimum = distance; bestCluster = cluster; }
			}
			return bestCluster;
		}
			
			


		private static void createExemples(int _nb, int _dim) {
			
			Exemples = new double[_nb] [_dim];
		
			for(int i=0; i<_nb;i++)
				Arrays.setAll(Exemples[i], j->Math.random()*20);
			
		}

		public static void main(String[] args) {
			
			int dim = 5;
			int nbExemples = 10000;
			createExemples(nbExemples,dim);
			int nbClusters = 15;
			System.out.println("classification de    "+nbExemples+"    exemples de dimension"+dim+"dans"+nbClusters+"clusters.");
			long start = System.currentTimeMillis();
			initialize(nbClusters,dim);
			kmeanCluster();
			long end = System.currentTimeMillis();
			System.out.println("après"+(end-start)+"ms");
			clusters.forEach(c->c.computeStats());
			
			//*imprime les résultats du cluster/clustering *//
			
			double error = 0;
			int nb=0;
			for(Cluster cluster:clusters) 
			{
				
				System.out.println(cluster);
				if(cluster.dataSet.size()>0) {
				nb++;
				error +=cluster.moyDist;
			}
			error = error/(nb*dim);
			System.out.println("erreurs globales="+String.format(Locale.ENGLISH,"%.2f",(error*100))+" % ");
					nb++;
			
		}
		
	}
			// TODO Auto-gene
		}

