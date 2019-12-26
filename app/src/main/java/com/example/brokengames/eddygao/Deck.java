package com.example.brokengames.eddygao;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Deck {
	private ArrayList<Card> deck;
	private int x, y, width, height;
	public enum deckType {
		tableau, foundation, waste, deal;
	}
	private deckType type;
	private Paint border;
	private Rect rectangle;
	private Drawable image;

	public Deck(int x, int y, int width, int height, deckType type) {
		deck = new ArrayList<Card>();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		border = new Paint();
		border.setAntiAlias(false);
		border.setStyle(Paint.Style.STROKE);
		border.setStrokeWidth(2);
		border.setColor(Color.WHITE);
		rectangle = new Rect(x,y,x+width,y+height);
		if (type == deckType.deal);
		rectangle.inset(-3, -3);
	}
	
	public Deck(int x, int y, int width, int height, Drawable image) {
		deck = new ArrayList<Card>();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = deckType.deal;
		border = new Paint();
		border.setAntiAlias(false);
		border.setStyle(Paint.Style.STROKE);
		border.setStrokeWidth(2);
		border.setColor(Color.WHITE);
		rectangle = new Rect(x,y,x+width,y+height);
		this.image = image;
		rectangle.inset(-3, -3);
	}
	
	public void addCard(Card card) {
		deck.add(card);
		card.setParent(this);

		if (deckType.tableau == this.type) card.moveTo(x, y + height/3*(deck.size()-1));
		if (deckType.foundation == this.type || deckType.waste == this.type) card.moveTo(x, y);

	}
	
	public void removeCard(Card card) {
		deck.remove(card);
	}
	
	public Card getCard(int index) {
		return deck.get(index);
	}
	
	public void draw(Canvas canvas) {
		//draw deck, then draw cards
		
		canvas.drawRect(rectangle, border);
		if (deckType.foundation == type || deckType.waste == type) {
			if (deck.size() > 1) deck.get(deck.size()-2).draw(canvas);
			if (deck.size() > 0) deck.get(deck.size()-1).draw(canvas);
		}
		if (deckType.tableau == type) {
			for (Card card : deck) {
				card.draw();
			}
		}
		if (deckType.deal == type) {
			image.setBounds(x, y, x+width, y+height);
			image.draw(canvas);
		}

		
	}
	
	public deckType getType() {
		return type;
	}
	
	public int getSize() {
		return deck.size();
	}
	
	public boolean isEmpty() { 
		return deck.size() == 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void move(int x, int y) {
		for (Card card : deck) {
			card.moveByDelta(x-this.x, y-this.y);
		}
		
		this.x = x;
		this.y = y;
		
	}

	public Card topDeck() {
		if (deck.size() == 0) {
			Log.wtf("out", "no top deck!!!");
			return null;
		}
		return deck.get(deck.size()-1);
	}

	
	public void resize(int width, int height) {
		this.height = height;
		this.width = width;
		
		rectangle.set(x, y, x+width, y+height);
		rectangle.inset(-3, -3);
		if (deckType.tableau == this.type) {
			for (int i = 0; i < deck.size(); i++) {
				deck.get(i).moveTo(x, y + height/3*i);
			}
		}
		
	}
	
}
