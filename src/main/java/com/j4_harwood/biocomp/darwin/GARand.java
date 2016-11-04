package com.j4_harwood.biocomp.darwin;

import java.util.concurrent.ThreadLocalRandom;

public class GARand {
	public static int nextInt(int bound){
		return ThreadLocalRandom.current().nextInt(bound);
	}
	public static double nextDouble(){
		return ThreadLocalRandom.current().nextDouble();
	}
}
