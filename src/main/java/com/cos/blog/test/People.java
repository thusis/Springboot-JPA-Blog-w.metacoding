package com.cos.blog.test;

public class People {
	private int hungryState = 50; //100이 만땅
	
	public void eat() {
		hungryState += 10;
	}
	
	public static void main(String[] args) {
		People p = new People();
		p.hungryState= 100;
	}
}
