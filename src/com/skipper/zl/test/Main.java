package com.skipper.zl.test;

import com.skipper.zl.utils.DB;
import com.skipper.zl.utils.Test;

public class Main {

	public static void main(String[] args) {
		System.out.println(new Object() instanceof Test);
		System.out.println(new ChildA() instanceof ImpF);
		System.out.println(new ChildA() instanceof Object);
		System.out.println(new FatherA() instanceof ChildA);
		System.out.println(FatherA.class.isInstance(new ChildA()));
	}
}
