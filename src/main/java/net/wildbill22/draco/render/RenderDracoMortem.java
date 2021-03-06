package net.wildbill22.draco.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.wildbill22.draco.entities.dragons.EntityDracoMortem;
import net.wildbill22.draco.entities.dragons.DragonRegistry.IDragonRendererCreationHandler;
import net.wildbill22.draco.lib.REFERENCE;
import net.wildbill22.draco.models.ModelDracoMortem;

public class RenderDracoMortem extends RendererLivingEntity  implements IDragonRendererCreationHandler {
	protected ModelDracoMortem model;
	protected ResourceLocation dragonTexture;

	public RenderDracoMortem(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
		this.renderManager = RenderManager.instance;
		setEntityTexture();
	}
	
	// Add logic here for different dragons
	private void setEntityTexture() {
		dragonTexture = new ResourceLocation(REFERENCE.MODID + ":textures/models/dracoMortem.png");
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return dragonTexture;
	}

	@Override
	public String getKey() {
		return EntityDracoMortem.name;
	}
}