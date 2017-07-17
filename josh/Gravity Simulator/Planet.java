public class Planet {

public double xxPos;
public double yyPos;
public double xxVel;
public double yyVel;
public double mass;
public String imgFileName;

public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {

	xxPos = xP;
	yyPos = yP;
	xxVel = xV;
	yyVel = yV;
	mass = m;
	imgFileName = img;
}

public Planet(Planet p){
	this(p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
}
public double calcDistance(Planet p) {
	double xxDis = p.xxPos - this.xxPos;
	double yyDis = p.yyPos - this.yyPos;
	double disSquared = xxDis * xxDis + yyDis * yyDis;
	double distance = Math.sqrt(disSquared);
	return distance; 
}

public double calcForceExertedBy(Planet p){
	double g = 6.67e-11;
	double radius = this.calcDistance(p);
	double m1 = this.mass;
	double m2 = p.mass;
	double force = (g * m1 * m2)/(radius * radius);
	return force;

}

public double calcForceExertedByX(Planet p){
	double xxDis = p.xxPos - this.xxPos;
	double radius = this.calcDistance(p);
	double force = this.calcForceExertedBy(p);
	double xForce = (force * xxDis) / radius;
	return xForce;
}

public double calcForceExertedByY(Planet p){
	double yyDis = p.yyPos - this.yyPos;
	double radius = this.calcDistance(p);
	double force = this.calcForceExertedBy(p);
	double yForce = (force * yyDis) / radius;
	return yForce;
}

public double calcNetForceExertedByX(Planet[] p){
	double totalXForce = 0;
	int i = 0;
	while (i < p.length) {
		if (this.equals(p[i])) {
			i = i + 1;
		}
		else {
			totalXForce = totalXForce + this.calcForceExertedByX(p[i]);
			i = i + 1;
		}


	}
	return totalXForce;
	}


public double calcNetForceExertedByY(Planet[] p){
	double totalYForce = 0;
	int i = 0;
	while (i < p.length) {
		if (this.equals(p[i])) {
			i = i + 1;
		}
		else {
			totalYForce = totalYForce + this.calcForceExertedByY(p[i]);
			i = i + 1;
		}


	}
	return totalYForce;
	}

public void update(double dt, double fX, double fY) {

	double aNetX = fX / this.mass;
	double aNetY = fY / this.mass;
	this.xxVel = this.xxVel + dt * aNetX;
	this.yyVel = this.yyVel + dt * aNetY;
	this.xxPos = this.xxPos + dt * this.xxVel;
	this.yyPos = this.yyPos + dt * this.yyVel;

}

public void draw(){
	String imgLocation = "images/"+this.imgFileName;
	StdDraw.picture(this.xxPos,this.yyPos,imgLocation);


}



}





