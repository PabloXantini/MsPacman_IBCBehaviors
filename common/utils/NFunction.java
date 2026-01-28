package MsGrasa2026.common.utils;

public class NFunction {
	public static double pPolyN(double input, double ease) {
		if(input>=1.0d || ease <= 0.0d) return 1.0d;
		return Math.exp(ease*Math.log(input));
	}
	public static double nPolyN(double input, double ease) {
		if(input<=0.0d || ease <= 0.0d) return 0.0d;
		return 1.0d - Math.exp(ease*Math.log(1.0d-input));
	}
}
