public class NBody {


	public static double readRadius(String fileName){
		In in = new In(fileName);
		int planets = in.readInt();
		double radius = in.readDouble();
		return radius;


	}

	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);
		int planets = in.readInt();
		double radius = in.readDouble();
		Planet[] planetList = new Planet[planets];
		int i = 0;
		while (i < planets){
			planetList[i] = new Planet(in.readDouble(),in.readDouble(),
				in.readDouble(),in.readDouble(),in.readDouble(), in.readString());
			i = i + 1;

		}
		return planetList;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Planet[] planets = readPlanets(filename);
		StdDraw.setXscale(-radius, radius);
		StdDraw.setYscale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");

		for (Planet planet : planets){
			planet.draw();

		}
		double time = 0;
		
		while (time < T){
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			int i = 0;
			while (i < planets.length){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
				i = i + 1;

			}
			for (Planet p : planets){
				p.update(dt,p.calcNetForceExertedByX(planets),
					p.calcNetForceExertedByY(planets));
			}
			StdDraw.picture(0,0,"images/starfield.jpg");

			for (Planet planet : planets){
				planet.draw();

			}
			StdDraw.show(10);
			time = time + dt;
		}
	StdOut.printf("%d\n", planets.length);
	StdOut.printf("%.2e\n", radius);
	for (int i = 0; i < planets.length; i++) {
		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   		planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);	
}		



	}




}
