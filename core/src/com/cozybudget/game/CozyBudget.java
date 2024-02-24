package com.cozybudget.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cozybudget.game.net.Administration;
import com.cozybudget.game.screen.MainMenuScreen;

public class CozyBudget extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public Preferences prefs;
	public Administration administration;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		prefs = Gdx.app.getPreferences("My Preferences");
		administration = new Administration();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {

		if (administration.getPublicToken() == null) {
			administration.getLinkToken();
		}
		if (administration.getPublicToken() != null && administration.getAccessToken() == null) {
			administration.getAccessToken();
		}
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
