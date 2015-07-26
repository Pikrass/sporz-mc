package net.pikrass.sporzmc;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;
import net.pikrass.sporz.actions.*;

import net.minecraft.entity.passive.EntityPig;

public class PigPlayer extends MCPlayer {
	private EntityPig entity;

	public PigPlayer(String username, EntityPig pig) {
		super(username);
		this.entity = pig;
	}
}
